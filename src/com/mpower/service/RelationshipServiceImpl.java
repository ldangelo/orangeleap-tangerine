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
    @Transactional(propagation = Propagation.REQUIRED)
    public Person maintainRelationships(Person person) throws PersonValidationException {
    	
    	PersonValidationException ex = new PersonValidationException();
    	
    	Map<String, FieldDefinition> map = person.getFieldTypeMap();
    	for (Map.Entry<String, FieldDefinition> e: map.entrySet()) {
    		String key = e.getKey();
    		FieldDefinition fd = e.getValue();
    		if (fd.isCustom() && fd.getFieldType() == FieldType.QUERY_LOOKUP || fd.getFieldType() == FieldType.MULTI_QUERY_LOOKUP) {
    			
    			// Determine if there is a relationship defined with another field.
    			List<FieldRelationship> masters = fd.getSiteMasterFieldRelationships(person.getSite());
    			List<FieldRelationship> details = fd.getSiteDetailFieldRelationships(person.getSite());
    			if (masters.size() == 0 && details.size() == 0) continue;
    			
    			// Get old and new reference field value for comparison
    			String oldFieldValue = getOldFieldValue(person, key);
       			String newFieldValue = getNewFieldValue(person, key);  
       		    List<Long> oldids = getIds(person, oldFieldValue);
    			List<Long> newids = getIds(person, newFieldValue);

    			
    			if (!oldFieldValue.equals(newFieldValue)) {
    			
	    			logger.debug("fieldname="+key);
	    			logger.debug("oldids="+oldFieldValue);
	    			logger.debug("newids="+newFieldValue);
	    			String fieldlabel = fd.getDefaultLabel();
	    			
	    			// Maintain the other related fields
		   			for (FieldRelationship fr : masters) {
	    			    maintainRelationShip(fieldlabel, person, fr.getMasterField(), RelationshipDirection.MASTER, fr.getRelationshipType(), oldids, newids, ex);
		   			}
		   			for (FieldRelationship fr : details) {
		    			maintainRelationShip(fieldlabel, person, fr.getDetailField(), RelationshipDirection.DETAIL, fr.getRelationshipType(), oldids, newids, ex);
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
    	return (String) bean.getPropertyValue(fieldname + ".value");
    }
    
    private List<Long> getIds(Person person, String fieldValue) {
		List<Long> ids = new ArrayList<Long>();
		if (fieldValue == null) return ids;
		String[] sids = fieldValue.split(",");
		for (String sid: sids) {
			if (sid.length() > 0) {
		   	   Long id = new Long(sid); 
			   if (person == null || !person.getId().equals(id)) ids.add(id);
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
    
     
	private void maintainRelationShip(String thisFieldLabel, Person person, FieldDefinition otherField, 
			RelationshipDirection direction, RelationshipType fieldRelationshipType, 
			List<Long> oldIds, List<Long> newIds, PersonValidationException ex) 
	throws PersonValidationException { 

        logger.debug("maintainRelationShip() called for "+direction+","+fieldRelationshipType);	
        
        if (!otherField.isCustom()) { 
        	logger.error("Field Id specified in relationship is not a custom field: " + otherField.getId());
        	return;
        }

        // Check if this field can be multi-valued per this relationship.
		boolean thisCanBeMultiValued = thisCanBeMultiValued(direction, fieldRelationshipType);
		boolean thisIsMultiValued = newIds.size() > 1;
		if (!thisCanBeMultiValued && thisIsMultiValued) {
			String thisFieldTooManyValues = "Value for " + thisFieldLabel + " can only have one selected value.";
			ex.addValidationResult(thisFieldTooManyValues);
			throw ex;
		}

		// Get list of all ids.
        List<Long> allids = new ArrayList<Long>();
        allids.addAll(oldIds);
        for (Long id : newIds) if (!allids.contains(id)) allids.add(id);
        
		// Check other person's related field for deletion or addition of a reference to this id.
		boolean otherCanBeMultiValued = otherCanBeMultiValued(direction, fieldRelationshipType);
        Long thisId = person.getId();
		String otherfieldname = otherField.getCustomFieldName();  
		
		List<Person> otherPersons = personDao.readPersons(person.getSite().getName(), allids);
		for (Person otherPerson : otherPersons) {
			
			Long otherId = otherPerson.getId();
			CustomField otherCustomField = otherPerson.getCustomFieldMap().get(otherfieldname);
			String otherCustomFieldValue = otherCustomField.getValue();
			List<Long> otherFieldIds = getIds(otherPerson, otherCustomFieldValue);
			boolean found = otherFieldIds.contains(thisId);
			boolean shouldBeFound = newIds.contains(otherId);
			
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
	
	private boolean thisCanBeMultiValued(RelationshipDirection direction, RelationshipType fieldRelationshipType) {
		return fieldRelationshipType.equals(RelationshipType.MANY_TO_MANY) ||
		( fieldRelationshipType.equals(RelationshipType.ONE_TO_MANY) && direction.equals(RelationshipDirection.DETAIL) );
	}
	
	private boolean otherCanBeMultiValued(RelationshipDirection direction, RelationshipType fieldRelationshipType) {
		return fieldRelationshipType.equals(RelationshipType.MANY_TO_MANY) ||
		( fieldRelationshipType.equals(RelationshipType.ONE_TO_MANY) && direction.equals(RelationshipDirection.MASTER) );
	}
    
}
