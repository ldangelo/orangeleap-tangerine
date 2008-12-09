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

    			logger.debug("fieldname="+key);
    			logger.debug("oldids="+oldFieldValue);
    			logger.debug("newids="+newFieldValue);
    			

    			
    			// Maintain the other related fields
	   			for (FieldRelationship fr : masters) {
    			    maintainRelationShip(person, fr.getMasterField(), RelationshipDirection.MASTER, fr.getRelationshipType(), oldids, newids);
	   			}
	   			for (FieldRelationship fr : details) {
	    			maintainRelationShip(person, fr.getDetailField(), RelationshipDirection.DETAIL, fr.getRelationshipType(), oldids, newids);
	    		}
	   			
    		}
    	}
    	
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
		String[] sids = fieldValue.split(",");
		for (String sid: sids) {
			if (sid.length() > 0) {
		   	   Long id = new Long(sid); 
			   if (!person.getId().equals(id)) ids.add(id);
			}
		}
        return ids;
    }
    
     
	private void maintainRelationShip(Person person, FieldDefinition otherField, RelationshipDirection direction, RelationshipType fieldRelationshipType, List<Long> oldids, List<Long> newids) throws PersonValidationException { 

        logger.debug("*** maintain called for "+direction+","+fieldRelationshipType);	
		
        // TODO
        
	}

    
}
