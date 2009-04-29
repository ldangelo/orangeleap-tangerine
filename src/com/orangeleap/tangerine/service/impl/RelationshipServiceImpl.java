package com.orangeleap.tangerine.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.orangeleap.tangerine.dao.ConstituentDao;
import com.orangeleap.tangerine.dao.CustomFieldDao;
import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.FieldRelationship;
import com.orangeleap.tangerine.service.RelationshipService;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.service.relationship.PersonTreeNode;
import com.orangeleap.tangerine.service.relationship.RelationshipUtil;
import com.orangeleap.tangerine.service.relationship.TooManyLevelsException;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.RelationshipDirection;
import com.orangeleap.tangerine.type.RelationshipType;

@Service("relationshipService")
@Transactional(propagation = Propagation.REQUIRED)
public class RelationshipServiceImpl extends AbstractTangerineService implements RelationshipService {
	
	public static final int MAX_TREE_DEPTH = 200;
	private static final String PERSON = "person";
 
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "constituentDAO")
    private ConstituentDao constituentDao;

    @Resource(name = "fieldDAO")
    private FieldDao fieldDao;
    
    @Resource(name = "customFieldDAO")
    private CustomFieldDao customFieldDao;
    

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ConstituentValidationException.class)
    public Person maintainRelationships(Person constituent) throws ConstituentValidationException {
    	if (logger.isTraceEnabled()) {
    	    logger.trace("maintainRelationships: constituent = " + constituent);
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
    		boolean isReferenceTypeField = fd.getFieldType() == FieldType.QUERY_LOOKUP || fd.getFieldType() == FieldType.MULTI_QUERY_LOOKUP || fd.getFieldType() == FieldType.QUERY_LOOKUP_OTHER;
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
    			validateIds(customFieldName, newids);
    			
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
	    	// debugPrintTree(constituent, lastRecursiveParentCustomFieldName);
	    }
			
        return constituent;
    }
    
    // Validate id list for import.  They must exist and be on same site.
    private void validateIds(String customFieldName, List<Long> ids) {
    	for (Long id : ids) {
    		Person person = constituentDao.readConstituentById(id);
    		if (person == null || !person.getSite().getName().equals(getSiteName())) {
                throw new RuntimeException("Invalid id "+id+" for "+customFieldName);
            }
    	}
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
		
		if (allids.isEmpty() == false) {
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
	    List<Person> persons = new ArrayList<Person>();
		if (StringUtils.hasText(customFieldName)) {
    	    List<Long> ids = getIdList(person, customFieldName);
    	    if (ids != null && ids.isEmpty() == false) {
    	        persons = constituentDao.readConstituentsByIds(ids);
    	    }
		}
		return persons;
	}
	
	// Person is any member of the tree.  Returns the tree based on the recursive relationship defined by the custom fields.
	@Override
	public PersonTreeNode getTree(Person person, String parentCustomFieldName, String childrenCustomFieldName, boolean oneLevelOnly, boolean fromHeadOfTree) throws ConstituentValidationException {
		if (fromHeadOfTree) {
            person = getHeadOfTree(person, parentCustomFieldName);
        }
		PersonTreeNode personNode = new PersonTreeNode(person, 0);
		getSubTree(personNode, childrenCustomFieldName, oneLevelOnly);
		return personNode;
	}
	
	// Field must be a master (parent id) custom field.
	@Override
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
	
	private static boolean thisCanBeMultiValued(RelationshipDirection direction, RelationshipType fieldRelationshipType) {
		return fieldRelationshipType.equals(RelationshipType.MANY_TO_MANY) ||
		( fieldRelationshipType.equals(RelationshipType.ONE_TO_MANY) && direction.equals(RelationshipDirection.DETAIL) );
	}
	
	private static boolean otherCanBeMultiValued(RelationshipDirection direction, RelationshipType fieldRelationshipType) {
		return fieldRelationshipType.equals(RelationshipType.MANY_TO_MANY) ||
		( fieldRelationshipType.equals(RelationshipType.ONE_TO_MANY) && direction.equals(RelationshipDirection.MASTER) );
	}
	
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
    
    @Override
    public boolean isHierarchy(FieldDefinition fd) {
    	// This must be the parent reference field on the detail record.
    	List<FieldRelationship> list = getSiteDetailFieldRelationships(fd.getId());
    	for (FieldRelationship fr : list) {
    		if (fr.isRecursive()) {
    			return true;
    		}
    	}
    	return false;
    }
    
    @Override
    public boolean isRelationship(FieldDefinition fd) {
    	return (getSiteMasterFieldRelationships(fd.getId()).size() > 0 || getSiteDetailFieldRelationships(fd.getId()).size() > 0) ;
    }
    
    @Override
	public List<CustomField> readCustomFieldsByConstituentAndFieldName(Long personId, String fieldName) {
	    if (logger.isTraceEnabled()) {
	        logger.trace("ConstituentCustomFieldRelationshipService.readAllCustomFieldsByConstituentAndFieldName: personId = " + personId);
	    }
	    if (null == constituentDao.readConstituentById(personId)) return null;
	    return customFieldDao.readCustomFieldsByEntityAndFieldName(personId, PERSON, fieldName);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void maintainCustomFieldsByConstituentAndFieldDefinition(Long personId, String fieldDefinitionId, List<CustomField> list) {
    	
	    if (logger.isTraceEnabled()) {
	        logger.trace("ConstituentCustomFieldRelationshipService.maintainCustomFieldsByConstituentAndFieldDefinition: personId = " + personId);
	    }
	    if (null == constituentDao.readConstituentById(personId)) throw new RuntimeException("Invalid constituent id");
	    FieldDefinition fieldDefinition = fieldDao.readFieldDefinition(fieldDefinitionId);
	    if (null == fieldDefinition) throw new RuntimeException("Invalid Field Definition id");

	    // TODO need additional validation for self reference / hierarchy for changed date ranges
	    
		validateDateRangesAndSave(personId, fieldDefinition, list);
	   
    }

    private void validateDateRangesAndSave(Long personId, FieldDefinition fieldDefinition, List<CustomField> newlist) {

    	FieldDefinition refField = getCorrespondingField(fieldDefinition);
    	
    	// Check date ranges on this entity
		boolean datesvalid = validateDateRanges(fieldDefinition.getId(), newlist);
		if (!datesvalid) {
			throw new RuntimeException("Date ranges cannot overlap for a single-valued field.");
		} 
		
    	// Find any orphaned back references 
		if (refField != null) {
			List<CustomField> deletes = getDeletes(personId, fieldDefinition, newlist);
			for (CustomField cf : deletes) {
				Long refid = new Long(cf.getValue());
				List<CustomField> reflist = customFieldDao.readCustomFieldsByEntityAndFieldName(new Long(refid), PERSON, refField.getCustomFieldName());
		        for (CustomField refcf: reflist) {
		        	Long backref = new Long(refcf.getValue());
		        	if (backref.equals(cf.getEntityId())) {
		        		customFieldDao.deleteCustomField(refcf);
		        	}
		        }
			}
		}
		
	    // Save custom fields on main entity
		customFieldDao.maintainCustomFieldsByEntityAndFieldName(personId, PERSON, fieldDefinition.getCustomFieldName(), newlist);

		
    	// Check date ranges on referenced entities
		if (refField != null) {
			
			for (CustomField cf : newlist) {
			
	    		boolean existing = false;
				Long refid = new Long(cf.getValue());
				List<CustomField> reflist = customFieldDao.readCustomFieldsByEntityAndFieldName(new Long(refid), PERSON, refField.getCustomFieldName());
		        for (CustomField refcf: reflist) {
		        	Long backref = new Long(refcf.getValue());
		        	if (backref.equals(cf.getEntityId())) {
		        		refcf.setStartDate(cf.getStartDate());
		        		refcf.setEndDate(cf.getEndDate());
		        		existing = true;
		        	}
		        }
		        if (!existing) {
		        	CustomField newcf = new CustomField();
		        	newcf.setEntityId(refid);
		        	newcf.setEntityType("person");
		        	newcf.setName(refField.getCustomFieldName());
		        	newcf.setStartDate(cf.getStartDate());
		        	newcf.setEndDate(cf.getEndDate());
		        	newcf.setValue(""+cf.getEntityId());
		        	reflist.add(newcf);
		        }
			
				boolean refdatesvalid = validateDateRanges(refField.getId(), reflist);
				if (!refdatesvalid) {
					Person refPerson = constituentDao.readConstituentById(new Long(cf.getValue()));
					throw new RuntimeException("Date ranges conflict on corresponding custom field for referenced value " + refPerson.getFullName());  
				} 
			    customFieldDao.maintainCustomFieldsByEntityAndFieldName(refid, PERSON, refField.getCustomFieldName(), reflist);
			    
			    // TODO need to set new roles on refId.
			    
			}
			
		}
		
    }
    
    private List<CustomField> getDeletes(Long personId, FieldDefinition fieldDefinition, List<CustomField> newlist) {
		List<CustomField> deletes = new ArrayList<CustomField>();
	    List<CustomField> oldlist = customFieldDao.readCustomFieldsByEntityAndFieldName(personId, PERSON, fieldDefinition.getCustomFieldName());
	    for (CustomField oldcf : oldlist) {
	    	boolean found = false;
		    for (CustomField newcf : newlist) {
		    	if (oldcf.getValue().equals(newcf.getValue())) {
		    		found = true;
		    		break;
		    	}
		    }
		    if (!found) {
		    	deletes.add(oldcf);
		    }
	    }
	    return deletes;
    }
    
    
    // Supports one relationship defined per field definition.
    private FieldDefinition getCorrespondingField(FieldDefinition fd) {
		List<FieldRelationship> masters = getSiteMasterFieldRelationships(fd.getId()); 
		FieldDefinition result = searchRelationships(fd, masters);
		if (result != null) return result;
		List<FieldRelationship> details = getSiteDetailFieldRelationships(fd.getId()); 
		result = searchRelationships(fd, details);
		return result;
    }
    
    private FieldDefinition searchRelationships(FieldDefinition fd, List<FieldRelationship> list) {
		for (FieldRelationship fr : list) {
			if (fr.getMasterRecordField().getId().equals(fd.getId())) return fr.getDetailRecordField();
			if (fr.getDetailRecordField().getId().equals(fd.getId())) return fr.getMasterRecordField();
		}
		return null;
    }
    
    private boolean validateDateRanges(String fieldDefinitionId, List<CustomField> list) {
    	
    	boolean isMultiValued = false;

    	List<FieldRelationship> masters = fieldDao.readMasterFieldRelationships(fieldDefinitionId);
    	List<FieldRelationship> details = fieldDao.readDetailFieldRelationships(fieldDefinitionId);
    	if (masters.size() > 0) {
    		isMultiValued = thisCanBeMultiValued(RelationshipDirection.DETAIL, masters.get(0).getRelationshipType());
    	}
    	if (details.size() > 0) {
    		isMultiValued = thisCanBeMultiValued(RelationshipDirection.MASTER, details.get(0).getRelationshipType());
    	}
    	
    	FieldDefinition fd = fieldDao.readFieldDefinition(fieldDefinitionId);
    	FieldType ft = fd.getFieldType();
    	if (ft.equals(FieldType.QUERY_LOOKUP) || ft.equals(FieldType.PICKLIST)) isMultiValued = false;
    	if (ft.equals(FieldType.MULTI_PICKLIST) || ft.equals(FieldType.MULTI_PICKLIST)) isMultiValued = true;
    
    	if (!isMultiValued) {
    	    return validateDateRangesDoNotOverlap(list);
    	}
    	return true;
    }    
    
    private List<Date> getDays(CustomField cf) {
    	List<Date> days = new ArrayList<Date>();
    	Date start = new Date(cf.getStartDate().getTime());
    	Date end = cf.getEndDate();
    	while (!start.after(end)) {
    		days.add(start);
    		start = nextDay(start);
    	}
    	return days;
    }
    
    private Date nextDay(Date date) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.DATE, 1);
    	return cal.getTime();
    }
    
    private boolean validateDateRangesDoNotOverlap(List<CustomField> list) {
    	List<Date> alldays = new ArrayList<Date>();
    	for (CustomField cf : list) {
    		List<Date> cfdays = getDays(cf);
    		for (Date d1 : alldays) {
        		for (Date d2 : cfdays) {
        			if (d1.equals(d2)) return false;
        		}
    		}
        	alldays.addAll(cfdays);
    	}
    	return true;
    }
    
}
