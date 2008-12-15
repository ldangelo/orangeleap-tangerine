package com.mpower.service;

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

import com.mpower.dao.PersonDao;
import com.mpower.dao.SiteDao;
import com.mpower.domain.CustomField;
import com.mpower.domain.Person;
import com.mpower.domain.customization.FieldDefinition;
import com.mpower.domain.customization.FieldRelationship;
import com.mpower.service.exception.PersonValidationException;
import com.mpower.type.FieldType;
import com.mpower.type.RelationshipDirection;
import com.mpower.type.RelationshipType;

@Service("relationshipService")
public class RelationshipServiceImpl implements RelationshipService {
 
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "personDao")
    private PersonDao personDao;

    @Resource(name = "siteDao")
    private SiteDao siteDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = PersonValidationException.class)
    public Person maintainRelationships(Person person) throws PersonValidationException {
    	
    	PersonValidationException ex = new PersonValidationException();
    	
    	Map<String, FieldDefinition> map = person.getFieldTypeMap();
    	for (Map.Entry<String, FieldDefinition> e: map.entrySet()) {
    		String key = e.getKey();
    		FieldDefinition fd = e.getValue();
    		boolean isReferenceTypeField = fd.getFieldType() == FieldType.QUERY_LOOKUP || fd.getFieldType() == FieldType.MULTI_QUERY_LOOKUP;
    		if (isReferenceTypeField && fd.isCustom()) {
    			
    			// Determine if there is a relationship defined with another field.
    			List<FieldRelationship> masters = fd.getSiteMasterFieldRelationships(person.getSite());
    			List<FieldRelationship> details = fd.getSiteDetailFieldRelationships(person.getSite());
    			if (masters.size() == 0 && details.size() == 0) continue;

    			String fieldlabel = fd.getDefaultLabel();
    			String customFieldName = fd.getCustomFieldName();

    			// Get old and new reference field value for comparison
    			String oldFieldValue = getOldFieldValue(person, key);
       			String newFieldValue = getNewFieldValue(person, key);  
       		    List<Long> oldids = getIds(oldFieldValue);
    			List<Long> newids = getIds(newFieldValue);
    			
    			if (!oldFieldValue.equals(newFieldValue)) {
 
	       			logger.debug("Maintaining relationships for fieldname = " + key + ", oldids = " + oldFieldValue + ", newids = " + newFieldValue);
	
	    			// Setup for recursion checks
	    			boolean needToCheckForRecursion = false;
		   			for (FieldRelationship fr : masters) {
		   				if (fr.isRecursive()) needToCheckForRecursion = true;
		   			}
		   			for (FieldRelationship fr : details) {
		   				if (fr.isRecursive()) needToCheckForRecursion = true;
		   			}
		   			List<Long> descendants = new ArrayList<Long>();
	    			
	    			// Maintain the other related fields
		   			for (FieldRelationship fr : masters) {
			   			if (needToCheckForRecursion) {
			   				descendants = new ArrayList<Long>();
			   				getDescendants(descendants, person, fr.getMasterField().getCustomFieldName(), 0);
			   			}
	   			    	maintainRelationShip(fieldlabel, customFieldName, person, fr.getMasterField(), RelationshipDirection.MASTER, fr, oldids, newids, descendants, ex);
	 	   			}
		   			for (FieldRelationship fr : details) {
	   			    	maintainRelationShip(fieldlabel, customFieldName, person, fr.getDetailField(), RelationshipDirection.DETAIL, fr, oldids, newids, descendants, ex);
	 	   			}
		   			
    			}
	   			
    		}
    	}
    	if (!ex.getValidationResults().isEmpty()) throw ex;
    	
        return person;
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
    
    private List<Long> getIds(String fieldValue) {
		List<Long> ids = new ArrayList<Long>();
		if (fieldValue == null) return ids;
		String[] sids = fieldValue.split(",");
		for (String sid: sids) {
			if (sid.length() > 0) {
		   	   Long id = new Long(sid); 
			   ids.add(id);
			}
		}
        return ids;
    }
    
    private String getIdString(List<Long> ids) {
    	StringBuilder sb = new StringBuilder();
    	for (Long id: ids) {
    		if (sb.length() > 0) sb.append(",");
    		sb.append(id);
    	}
    	return sb.toString();
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
			PersonValidationException ex) throws PersonValidationException { 

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
        for (Long id : newIds) if (!allids.contains(id)) allids.add(id);
        
		// Check other person's related field for deletion or addition of a reference to this id.
		boolean otherCanBeMultiValued = otherCanBeMultiValued(direction, fieldRelationship.getRelationshipType());
        Long thisId = person.getId();
		String otherfieldname = otherField.getCustomFieldName();  
		
		List<Person> otherPersons = personDao.readPersons(person.getSite().getName(), allids);
		for (Person otherPerson : otherPersons) {
			
			Long otherId = otherPerson.getId();
			
			// Check for recursion loops
			if (fieldRelationship.isRecursive() && newIds.contains(otherId)) {
				// Self reference
				if (thisId.equals(otherId)) {
					//String attemptToAddSelf = "Value for " + thisFieldLabel + " cannot reference itself.";
					ex.addValidationResult("fieldSelfReference", new Object[]{thisFieldLabel});
					continue;
				}
				if (thisCanBeMultiValued) {
					// Attempt to add an ancestor as a child
					List<Long> descendants = new ArrayList<Long>();
					getDescendants(descendants, otherPerson, customFieldName, 0);
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
			
			// Check for additions or deletions
			CustomField otherCustomField = otherPerson.getCustomFieldMap().get(otherfieldname);
			String otherCustomFieldValue = otherCustomField.getValue();
			List<Long> otherFieldIds = getIds(otherCustomFieldValue);
			boolean found = otherFieldIds.contains(thisId);
			boolean shouldBeFound = newIds.contains(otherId);
			
			// Maintain reverse references
			boolean needToPersist = false;
			if (found && !shouldBeFound) {
				otherFieldIds.remove(thisId);
				needToPersist = true;
			} else if (!found && shouldBeFound) {
				if (!otherCanBeMultiValued) otherFieldIds.clear();
				otherFieldIds.add(thisId);
				needToPersist = true;
			}
			
			if (needToPersist) {
				String newOtherFieldValue = getIdString(otherFieldIds);
				logger.debug("Updating related field "+otherCustomField.getName()+" value on "+otherPerson.getDisplayValue() + " to " + newOtherFieldValue);
				otherCustomField.setValue(newOtherFieldValue);
				personDao.savePerson(otherPerson); 
			}
			
		}

	}

	private List<Long> getIdList(Person person, String customFieldName) {
		
		CustomField customField = person.getCustomFieldMap().get(customFieldName);
		String customFieldValue = customField.getValue();
		List<Long> ids = getIds(customFieldValue);
        return ids;		
        
	}
	
	private List<Person> getPersons(Person person, String customFieldName) {
		
		List<Person> persons = personDao.readPersons(person.getSite().getName(), getIdList(person, customFieldName));
		return persons;
		
	}

	private static final int MAX_TREE_DEPTH = 200;
	
	// Field must be a detail (child list) custom field.
	// This might be optimized by doing it closer to the database since we don't need the whole object unless there is an error.
	private void getDescendants(List<Long> list, Person person, String customFieldName, int level) throws PersonValidationException {
		
		if (level > MAX_TREE_DEPTH) {
			// This should not happen normally.
			String recursionError = "Relationship tree for "+customFieldName+" exceeds maximum number of levels.";
			logger.error(recursionError);
			PersonValidationException ex = new PersonValidationException();
			ex.addValidationResult("relationshipTooManyLevels", new Object[]{customFieldName}); 
			throw ex;
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
			getDescendants(list, referencedPerson, customFieldName, level + 1);
		}
		
	}
	
	private boolean thisCanBeMultiValued(RelationshipDirection direction, RelationshipType fieldRelationshipType) {
		return fieldRelationshipType.equals(RelationshipType.MANY_TO_MANY) ||
		( fieldRelationshipType.equals(RelationshipType.ONE_TO_MANY) && direction.equals(RelationshipDirection.DETAIL) );
	}
	
	private boolean otherCanBeMultiValued(RelationshipDirection direction, RelationshipType fieldRelationshipType) {
		return fieldRelationshipType.equals(RelationshipType.MANY_TO_MANY) ||
		( fieldRelationshipType.equals(RelationshipType.ONE_TO_MANY) && direction.equals(RelationshipDirection.MASTER) );
	}
    
}
