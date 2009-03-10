package com.mpower.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.interfaces.FieldDao;
import com.mpower.dao.interfaces.ConstituentDao;
import com.mpower.domain.model.Person;
import com.mpower.domain.model.customization.CustomField;
import com.mpower.domain.model.customization.FieldDefinition;
import com.mpower.domain.model.customization.FieldRelationship;
import com.mpower.service.RelationshipService;
import com.mpower.service.exception.ConstituentValidationException;
import com.mpower.service.relationship.PersonTreeNode;
import com.mpower.service.relationship.RelationshipUtil;
import com.mpower.service.relationship.TooManyLevelsException;
import com.mpower.type.FieldType;
import com.mpower.type.RelationshipDirection;
import com.mpower.type.RelationshipType;

@Service("relationshipService")
@Transactional(propagation = Propagation.REQUIRED)
public class RelationshipServiceImpl extends AbstractTangerineService implements RelationshipService {
	
	public static final int MAX_TREE_DEPTH = 200;
 
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "constituentDAO")
    private ConstituentDao constituentDao;

    @Resource(name = "fieldDAO")
    private FieldDao fieldDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ConstituentValidationException.class)
    public Person maintainRelationships(Person constituent) throws ConstituentValidationException {
    	if (logger.isDebugEnabled()) {
    	    logger.debug("maintainRelationships: constituent = " + constituent);
    	}
    	if (constituent.getSite() == null) {
            return constituent;
        }
    	ConstituentValidationException ex = new ConstituentValidationException();
    	String lastRecursiveParentCustomFieldName = null;
    	
    	Map<String, FieldDefinition> map = constituent.getFieldTypeMap();
    	if (map == null) {
    		logger.debug("FieldTypeMap not set, skipping relationship maintenance.");
    		return constituent;
    	}
    	
    	for (Map.Entry<String, FieldDefinition> e: map.entrySet()) {
    		String key = e.getKey();
    		FieldDefinition fd = e.getValue();
    		boolean isReferenceTypeField = fd.getFieldType() == FieldType.QUERY_LOOKUP || fd.getFieldType() == FieldType.MULTI_QUERY_LOOKUP;
    		if (isReferenceTypeField && fd.isCustom()) {
    			
    			// Determine if there is a relationship defined with another field.
    			List<FieldRelationship> masters = getSiteMasterFieldRelationships(fd.getId()); 
    			List<FieldRelationship> details = getSiteDetailFieldRelationships(fd.getId()); 
    			if (masters.size() == 0 && details.size() == 0) {
                    continue;
                }

    			String fieldlabel = fd.getDefaultLabel();
    			String customFieldName = fd.getCustomFieldName();

    			// Get old and new reference field value for comparison
    			String oldFieldValue = getOldFieldValue(constituent, key);
       			String newFieldValue = getNewFieldValue(constituent, key);  
       		    List<Long> oldids = RelationshipUtil.getIds(oldFieldValue);
    			List<Long> newids = RelationshipUtil.getIds(newFieldValue);
    			
    			if (!oldFieldValue.equals(newFieldValue)) {
 
	       			logger.debug("Maintaining relationships for fieldname = " + key + ", oldids = " + oldFieldValue + ", newids = " + newFieldValue);
	
	    			// Setup for recursion checks
	    			boolean needToCheckForRecursion = false;
		   			for (FieldRelationship fr : masters) {
		   				if (fr.isRecursive()) {
                            needToCheckForRecursion = true;
                        }
		   			}
		   			for (FieldRelationship fr : details) {
		   				if (fr.isRecursive()) {
		   					needToCheckForRecursion = true;
		   					lastRecursiveParentCustomFieldName = fr.getDetailRecordField().getCustomFieldName();
		   				}
		   			}
		   			List<Long> descendants = new ArrayList<Long>();
	    			
	    			// Maintain the other related fields
		   			for (FieldRelationship fr : details) {
			   			if (needToCheckForRecursion) {
			   				descendants = new ArrayList<Long>();
			   				getDescendantIds(descendants, constituent, fr.getMasterRecordField().getCustomFieldName(), 0);
			   			}
	   			    	maintainRelationShip(fieldlabel, customFieldName, constituent, fr.getMasterRecordField(), RelationshipDirection.MASTER, fr, oldids, newids, descendants, ex);
	 	   			}
		   			for (FieldRelationship fr : masters) {
	   			    	maintainRelationShip(fieldlabel, customFieldName, constituent, fr.getDetailRecordField(), RelationshipDirection.DETAIL, fr, oldids, newids, descendants, ex);
	 	   			}
		   			
    			}
	   			
    		}
    	}
    	if (!ex.getValidationResults().isEmpty()) {
            throw ex;
        }
    	
	    if (logger.isDebugEnabled() && lastRecursiveParentCustomFieldName != null) {
	        // TODO Turn off in production since getting the whole tree could be expensive.
	    	debugPrintTree(constituent, lastRecursiveParentCustomFieldName);
	    }
			
        return constituent;
    }
    
    // Return the tree reachable from Person, using the "parent" field name (e.g. "organization.parent")
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, rollbackFor = ConstituentValidationException.class)
	public PersonTreeNode getTree(Person person, String parentCustomFieldName, boolean oneLevelOnly, boolean fromHeadOfTree) throws ConstituentValidationException {
    	
       	Map<String, FieldDefinition> map = person.getFieldTypeMap();
    	for (Map.Entry<String, FieldDefinition> e: map.entrySet()) {
    		FieldDefinition fd = e.getValue();
    		if (fd.getCustomFieldName().equals(parentCustomFieldName)) {
    			List<FieldRelationship> details = getSiteDetailFieldRelationships(fd.getId());  
    			for (FieldRelationship fr : details) {
	    			if (fr.isRecursive()) {
	        			String childrenCustomFieldName = fr.getMasterRecordField().getCustomFieldName();
	            		PersonTreeNode tree = getTree(person, parentCustomFieldName, childrenCustomFieldName, oneLevelOnly, fromHeadOfTree);
	    				return tree;
	    			}
    			}
    		}
    	}
    	return null;
	}

    private void debugPrintTree(Person person, String parentCustomFieldName) throws ConstituentValidationException {
    	try {
    		PersonTreeNode tree = getTree(person, parentCustomFieldName, false, true);
    		String result = debugPrintTree(tree);
    		logger.debug("Tree for "+parentCustomFieldName+":\r\n"+result);
    		PersonTreeNode thisnode = findPersonNodeInTree(person, tree);
    		result = debugPrintTree(thisnode);
    		logger.debug("Subtree for "+person.getDisplayValue()+":\r\n"+result);
    		Person head = getHeadOfTree(person, parentCustomFieldName);
    		logger.debug("Head of tree: "+head.getDisplayValue());
    		Person common = getFirstCommonAncestor(person, head, parentCustomFieldName);
    		logger.debug("Common ancestor: "+common.getDisplayValue());
    		logger.debug(tree.toJSONString());
    	} catch (Exception e) {
    		logger.debug("debugPrintTree error:" ,e);
    	}
    }
    
    private String getOldFieldValue(Person person, String fieldname) {
		String oldFieldValue = (String)person.getFieldValueMap().get(fieldname);  
		return (oldFieldValue == null) ? "" : oldFieldValue;
    }
    
    private String getNewFieldValue(Person person, String fieldname) {
    	BeanWrapperImpl bean = new BeanWrapperImpl(person);
    	String result = (String) bean.getPropertyValue(fieldname + ".value");
    	return (result == null) ? "" : result;
    }
    
	private void maintainRelationShip(String thisFieldLabel,
			String customFieldName, 
			Person person, 
			FieldDefinition otherField,
			RelationshipDirection direction,
			FieldRelationship fieldRelationship, 
			List<Long> oldIds,
			List<Long> newIds, 
			List<Long> checkDescendents,
			ConstituentValidationException ex) throws ConstituentValidationException { 

        logger.debug("maintainRelationShip() called for " + otherField.getFieldName() + ", " + direction + ", " + fieldRelationship.getRelationshipType() + ", recursive=" + fieldRelationship.isRecursive());	
        
        if (!otherField.isCustom()) { 
        	logger.error("Field Id specified in relationship is not a custom field: " + otherField.getId());
        	return;
        }

        // Check if this field can be multi-valued per this relationship.
		boolean thisCanBeMultiValued = thisCanBeMultiValued(direction, fieldRelationship.getRelationshipType());
		boolean thisIsMultiValued = newIds.size() > 1;
		if (!thisCanBeMultiValued && thisIsMultiValued) {
			String thisFieldTooManyValues = "Value for " + thisFieldLabel + " can only have one selected value.";
			ex.addValidationResult(thisFieldTooManyValues);
			return;
		}

		// Get merged list of all ids, both adds and deletes.
        List<Long> allids = new ArrayList<Long>();
        allids.addAll(oldIds);
        for (Long id : newIds) {
            if (!allids.contains(id)) {
                allids.add(id);
            }
        }
        
		// Check other person's related field for deletion or addition of a reference to this id.
		boolean otherCanBeMultiValued = otherCanBeMultiValued(direction, fieldRelationship.getRelationshipType());
        Long thisId = person.getId();
		String otherfieldname = otherField.getCustomFieldName();  
		
		List<Person> otherPersons = constituentDao.readConstituentsByIds(allids);
		for (Person otherPerson : otherPersons) {
			
			Long otherId = otherPerson.getId();

			if (newIds.contains(otherId)) {
			
				// Self reference
				if (thisId.equals(otherId)) {
					ex.addValidationResult("fieldSelfReference", new Object[]{thisFieldLabel});
					continue;
				}
	
				// Check for recursion loops
				if (fieldRelationship.isRecursive()) {
					if (thisCanBeMultiValued) {
						// Attempt to add an ancestor as a child
						List<Long> descendants = new ArrayList<Long>();
						getDescendantIds(descendants, otherPerson, customFieldName, 0);
						if (descendants.contains(thisId)) {
							ex.addValidationResult("childReferenceError", new Object[]{thisFieldLabel});
							continue;
						}
					} else {
						// Attempt to set parent to a descendant
						if (checkDescendents.contains(otherId)) {
							ex.addValidationResult("parentReferenceError", new Object[]{thisFieldLabel});
							continue;
						}
					}
				}
			
			}
			
			// Check for additions or deletions
			CustomField otherCustomField = otherPerson.getCustomFieldMap().get(otherfieldname);
			String otherCustomFieldValue = otherCustomField.getValue();
			List<Long> otherFieldIds = RelationshipUtil.getIds(otherCustomFieldValue);
			boolean found = otherFieldIds.contains(thisId);
			boolean shouldBeFound = newIds.contains(otherId);
			
			// Maintain reverse references
			boolean needToPersist = false;
			if (found && !shouldBeFound) {
				otherFieldIds.remove(thisId);
				needToPersist = true;
			} else if (!found && shouldBeFound) {
				if (!otherCanBeMultiValued) {
                    otherFieldIds.clear();
                }
				otherFieldIds.add(thisId);
				needToPersist = true;
				ensureOtherPersonAttributeIsSet(otherField, otherPerson);
			}
			
			if (needToPersist) {
				String newOtherFieldValue = RelationshipUtil.getIdString(otherFieldIds);
				logger.debug("Updating related field "+otherCustomField.getName()+" value on "+otherPerson.getDisplayValue() + " to " + newOtherFieldValue);
				otherCustomField.setValue(newOtherFieldValue);
				constituentDao.maintainConstituent(otherPerson); 
			}
			
		}

	}
	
	private void ensureOtherPersonAttributeIsSet(FieldDefinition otherFieldDefinition, Person otherPerson) {
		String fieldAttributes = otherFieldDefinition.getEntityAttributes();
		if (fieldAttributes == null) {
            return;
        }
		// If it's a field that applies to only a single attribute, make sure the attribute is set on the other person. 
		// For multiple attributes, take the first as the default.
		if (fieldAttributes.contains(",")) {
			fieldAttributes = fieldAttributes.substring(0,fieldAttributes.indexOf(","));
		}
	    otherPerson.addConstituentRole(fieldAttributes);
	}

	// Field must be a detail (child list) custom field.
	private void getDescendantIds(List<Long> list, Person person, String customFieldName, int level) throws ConstituentValidationException {
		
		if (level > MAX_TREE_DEPTH) {
			throw new TooManyLevelsException(customFieldName);
		}

		List<Person> referencedPersons = getPersons(person, customFieldName);
		for (Person referencedPerson : referencedPersons) {
			Long referencedId = referencedPerson.getId();
			if (list.contains(referencedId)) {
				// This will normally happen if they place a child over a parent or vice-versa.  It is caught in the recursion check logic when the item causing the loop is edited.
				String recursionLoop = "Relationship tree for "+customFieldName+" forms a loop due to reference from " + person.getDisplayValue() + " to " + referencedPerson.getDisplayValue();
				logger.debug(recursionLoop);
				return;
			}
			list.add(referencedId);
			getDescendantIds(list, referencedPerson, customFieldName, level + 1);
		}
		
	}
	
	// Returns list of person Ids in the reference type custom field.
	private List<Long> getIdList(Person person, String customFieldName) {
		
		CustomField customField = person.getCustomFieldMap().get(customFieldName);
		String customFieldValue = customField.getValue();
		List<Long> ids = RelationshipUtil.getIds(customFieldValue);
        return ids;		
        
	}
	
	// Returns list of Person objects referenced by the custom field
	private List<Person> getPersons(Person person, String customFieldName) {
		
		List<Person> persons = constituentDao.readConstituentsByIds(getIdList(person, customFieldName));
		return persons;
		
	}
	
	// Person is any member of the tree.  Returns the tree based on the recursive relationship defined by the custom fields.
	public PersonTreeNode getTree(Person person, String parentCustomFieldName, String childrenCustomFieldName, boolean oneLevelOnly, boolean fromHeadOfTree) throws ConstituentValidationException {
		if (fromHeadOfTree) {
            person = getHeadOfTree(person, parentCustomFieldName);
        }
		PersonTreeNode personNode = new PersonTreeNode(person, 0);
		getSubTree(personNode, childrenCustomFieldName, oneLevelOnly);
		return personNode;
	}
	
	// Field must be a master (parent id) custom field.
	public Person getHeadOfTree(Person person, String parentCustomFieldName) throws ConstituentValidationException {
		int level = 0;
		while (true) {
			level++;
			if (level > MAX_TREE_DEPTH) {
				throw new TooManyLevelsException(parentCustomFieldName);
			}
			List<Person> referencedPersons = getPersons(person, parentCustomFieldName);
			if (referencedPersons.size() == 0) {
                return person;
            }
			person = referencedPersons.get(0);
		}
	}
	
	public void getSubTree(PersonTreeNode personNode, String childrenCustomFieldName, boolean oneLevelOnly) throws ConstituentValidationException {
		if (personNode.getLevel() > MAX_TREE_DEPTH) {
			throw new TooManyLevelsException(childrenCustomFieldName);
		}

		List<Person> referencedPersons = getPersons(personNode.getPerson(), childrenCustomFieldName);
		for (Person referencedPerson : referencedPersons) {
			PersonTreeNode child = new PersonTreeNode(referencedPerson, personNode.getLevel() + 1);
			personNode.getChildren().add(child);
			if (!oneLevelOnly) {
                getSubTree(child, childrenCustomFieldName, oneLevelOnly);
            }
		}
	}
	
	// If person is in tree, return the node, otherwise return null.
	public PersonTreeNode findPersonNodeInTree(Person person, PersonTreeNode tree) {
		if (equals(person, tree.getPerson())) {
            return tree;
        }
	    for (PersonTreeNode personNode: tree.getChildren()) {
	    	PersonTreeNode node = findPersonNodeInTree(person, personNode);
	    	if (node != null) {
                return node;
            }
	    }
	    return null;
	}

	public Person getFirstCommonAncestor(Person p1, Person p2, String parentCustomFieldName) throws ConstituentValidationException {
		List<Person> p1Parents = new ArrayList<Person>();
		p1Parents.add(p1);
		List<Person> p2Parents = new ArrayList<Person>();
		p2Parents.add(p2);
		int level = 0;
		while (true) {
			level++;
			if (level > MAX_TREE_DEPTH) {
				throw new TooManyLevelsException(parentCustomFieldName);
			}
			List<Person> referencedPersons1 = getPersons(p1, parentCustomFieldName);
			List<Person> referencedPersons2 = getPersons(p2, parentCustomFieldName);
			if (referencedPersons1.size() == 0 && referencedPersons2.size() == 0) {
                return null;
            }
			if (referencedPersons1.size() > 0) {
                p1 = referencedPersons1.get(0);
            }
			if (referencedPersons2.size() > 0) {
                p2 = referencedPersons2.get(0);
            }
			if (contains(p1Parents, p2)) {
                return p2;
            }
			if (contains(p2Parents, p1)) {
                return p1;
            }
		}
	}
	
	public String debugPrintTree(PersonTreeNode tree) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tree.getLevel(); i++) {
            sb.append("\t");
        }
		sb.append(tree.getPerson().getDisplayValue()).append("\r\n");
		for (PersonTreeNode child: tree.getChildren()) {
            sb.append(debugPrintTree(child));
        }
		return sb.toString();
	}
	
	private boolean contains(List<Person> persons, Person person) {
		for (Person aperson: persons) {
            if (equals(person, aperson)) {
                return true;
            }
        }
		return false;
	}

	// Don't want to add an equals method to the JPA object
	private boolean equals(Person p1, Person p2) {
		if (p1 == null && p2 == null) {
            return true;
        }
		if (p1 == null || p2 == null) {
            return false;
        }
		return p1.getId().equals(p2.getId());
	}
	
	private boolean thisCanBeMultiValued(RelationshipDirection direction, RelationshipType fieldRelationshipType) {
		return fieldRelationshipType.equals(RelationshipType.MANY_TO_MANY) ||
		( fieldRelationshipType.equals(RelationshipType.ONE_TO_MANY) && direction.equals(RelationshipDirection.DETAIL) );
	}
	
	private boolean otherCanBeMultiValued(RelationshipDirection direction, RelationshipType fieldRelationshipType) {
		return fieldRelationshipType.equals(RelationshipType.MANY_TO_MANY) ||
		( fieldRelationshipType.equals(RelationshipType.ONE_TO_MANY) && direction.equals(RelationshipDirection.MASTER) );
	}
	

	
	  // These are the relationships for which the current field is a detailField.  The relationship holds the masterField name and relationship type.
	  // There should only be one master field relationship active per FieldDefinition (for a particular site).

	   private List<FieldRelationship> getSiteDetailFieldRelationships(String fieldDefinitionId) {
		   return getSiteFieldRelationships(fieldDao.readDetailFieldRelationships(fieldDefinitionId));
	   }
	   
	   private List<FieldRelationship> getSiteMasterFieldRelationships(String fieldDefinitionId) {
		   return getSiteFieldRelationships(fieldDao.readMasterFieldRelationships(fieldDefinitionId));
	   }
		
		// Filter for this site.
	    private List<FieldRelationship> getSiteFieldRelationships(List<FieldRelationship> list) {
	    	List<FieldRelationship> result = new ArrayList<FieldRelationship>();
			for (FieldRelationship fr : list) {
				if (fr.getSite() == null) {
	                continue;
	            }
				if (fr.getSite().getName().equals(getSiteName())) {
	                result.add(fr);
	            }
			}
			// If no site specific relationships exist for this field, the default relationships apply.
			if (result.size() == 0) {
	            for (FieldRelationship fr : list) {
	            	if (fr.getSite() == null) {
	                    result.add(fr);
	                }
	            }
	        }
			return result;
	    }
	    
	    public boolean isTree(FieldDefinition fd) {
	    	// This must be the parent reference field on the detail record.
	    	List<FieldRelationship> list = getSiteDetailFieldRelationships(fd.getId());
	    	for (FieldRelationship fr : list) {
	    		if (fr.isRecursive()) {
	    			return true;
	    		}
	    	}
	    	return false;
	    }
	    
	    public boolean isRelationship(FieldDefinition fd) {
	    	return (getSiteMasterFieldRelationships(fd.getId()).size() > 0 || getSiteDetailFieldRelationships(fd.getId()).size() > 0) ;
	    }

	
}
