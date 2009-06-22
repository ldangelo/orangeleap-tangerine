package com.orangeleap.tangerine.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.orangeleap.tangerine.dao.ConstituentCustomFieldRelationshipDao;
import com.orangeleap.tangerine.dao.ConstituentDao;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.customization.ConstituentCustomFieldRelationship;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.service.AuditService;
import com.orangeleap.tangerine.service.ConstituentCustomFieldRelationshipService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineUserHelper;

@Service("constituentCustomFieldRelationshipService")
public class ConstituentCustomFieldRelationshipServiceImpl extends AbstractTangerineService implements ConstituentCustomFieldRelationshipService {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name="tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;

    @Resource(name = "constituentDAO")
    private ConstituentDao constituentDao;

    @Resource(name = "constituentCustomFieldRelationshipDAO")
    private ConstituentCustomFieldRelationshipDao constituentCustomFieldRelationshipDao;

    @Resource(name = "auditService")
    private AuditService auditService;

    // Used for custom field maintenance 
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ConstituentCustomFieldRelationship maintainConstituentCustomFieldRelationship(ConstituentCustomFieldRelationship constituentCustomFieldRelationship) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainConstituentCustomFieldRelationship: id = " + constituentCustomFieldRelationship.getId());
        }
	    // If the site can't read the constituent, don't udpate the ccr.
	    if (null == constituentDao.readConstituentById(constituentCustomFieldRelationship.getConstituentId())) {
			return null;
		}
        constituentCustomFieldRelationship = constituentCustomFieldRelationshipDao.maintainConstituentCustomFieldRelationship(constituentCustomFieldRelationship);
        
        auditService.auditObject(constituentCustomFieldRelationship); 
        return constituentCustomFieldRelationship;
    }
    
    @Override
    public void deleteConstituentCustomFieldRelationship(Long entityId, String masterFieldDefinitionId, String value, Date startDate) {
	    if (null == constituentDao.readConstituentById(entityId)) {
			return;
		}
        constituentCustomFieldRelationshipDao.deleteConstituentCustomFieldRelationship(entityId, masterFieldDefinitionId, value, startDate);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteConstituentCustomFieldRelationship(CustomField oldCustomFld, String masterFieldDefinitionId) {
		ConstituentCustomFieldRelationship ccr = readByConstituentFieldDefinitionCustomFieldIds(oldCustomFld.getEntityId(), masterFieldDefinitionId, oldCustomFld.getValue(), 
				oldCustomFld.getStartDate());
		if (ccr != null) {
		    if (constituentDao.readConstituentById(oldCustomFld.getEntityId()) != null) {
		    	constituentCustomFieldRelationshipDao.deleteConstituentCustomFieldRelationship(ccr);
			}
		}
		
		if (NumberUtils.isDigits(oldCustomFld.getValue())) {
			ConstituentCustomFieldRelationship reverseCcr = readByConstituentFieldDefinitionCustomFieldIds(Long.parseLong(oldCustomFld.getValue()), masterFieldDefinitionId, 
					oldCustomFld.getEntityId().toString(), oldCustomFld.getStartDate());
			if (reverseCcr != null) {
			    if (constituentDao.readConstituentById(Long.parseLong(oldCustomFld.getValue())) != null) {
			    	constituentCustomFieldRelationshipDao.deleteConstituentCustomFieldRelationship(reverseCcr);
				}
			}
		}		
    }

    @Override
	public ConstituentCustomFieldRelationship readById(Long id) {
	    if (logger.isTraceEnabled()) {
	        logger.trace("ConstituentCustomFieldRelationshipService.readById: id = " + id);
	    }
	    ConstituentCustomFieldRelationship constituentCustomFieldRelationship = constituentCustomFieldRelationshipDao.readById(id);
	    // If the site can't read the constituent, don't return the ccr.
	    if (null == constituentDao.readConstituentById(constituentCustomFieldRelationship.getConstituentId())) {
			return null;
		}
	    return constituentCustomFieldRelationship;
	}
    
    @Override
    public ConstituentCustomFieldRelationship readByConstituentFieldDefinitionCustomFieldIds(Long constituentId, String fieldDefinitionId, String customFieldValue, Date customFieldStartDate) {
	    Constituent constituent = constituentDao.readConstituentById(constituentId);
	    // If the site can't read the constituent, don't return the ccr.
	    if (constituent == null) {
			return null;
		}
	    ConstituentCustomFieldRelationship constituentCustomFieldRelationship = constituentCustomFieldRelationshipDao.readByConstituentFieldDefinitionCustomFieldIds(constituentId, fieldDefinitionId, customFieldValue, customFieldStartDate);
	    return constituentCustomFieldRelationship;
	}
    
    @Override
    public void updateConstituentCustomFieldRelationshipValue(CustomField newCustomFld, CustomField oldCustomFld, String masterFieldDefinitionId, Map<String, Object> relationshipCustomizations) {
		ConstituentCustomFieldRelationship ccr = readByConstituentFieldDefinitionCustomFieldIds(oldCustomFld.getEntityId(), masterFieldDefinitionId, oldCustomFld.getValue(), 
				oldCustomFld.getStartDate());
		if (ccr == null) {
			// Just create a new one if old one is not found
			ccr = new ConstituentCustomFieldRelationship();
		}
		ccr.setConstituentId(oldCustomFld.getEntityId());
		ccr.setCustomFieldValue(newCustomFld.getValue());
		ccr.setCustomFieldStartDate(newCustomFld.getStartDate());
		ccr.setMasterFieldDefinitionId(masterFieldDefinitionId);
		setConstituentCustomFieldRelationshipCustomFields(relationshipCustomizations, ccr);
		maintainConstituentCustomFieldRelationship(ccr);
		
		if (NumberUtils.isDigits(newCustomFld.getValue())) {
			ConstituentCustomFieldRelationship reverseCcr = readByConstituentFieldDefinitionCustomFieldIds(Long.parseLong(oldCustomFld.getValue()), masterFieldDefinitionId, 
					oldCustomFld.getEntityId().toString(), oldCustomFld.getStartDate());
			if (reverseCcr == null) {
				// Just create a new one if old one is not found
				reverseCcr = new ConstituentCustomFieldRelationship();
			}
			reverseCcr.setConstituentId(Long.parseLong(newCustomFld.getValue()));
			reverseCcr.setCustomFieldValue(oldCustomFld.getEntityId().toString());
			reverseCcr.setCustomFieldStartDate(newCustomFld.getStartDate());
			reverseCcr.setMasterFieldDefinitionId(masterFieldDefinitionId);
			setConstituentCustomFieldRelationshipCustomFields(relationshipCustomizations, reverseCcr);
			maintainConstituentCustomFieldRelationship(reverseCcr);
		}
    }
    
    @Override
    public void saveNewConstituentCustomFieldRelationship(CustomField newCustomFld, String masterFieldDefinitionId, Map<String, Object> relationshipCustomizations) {
    	ConstituentCustomFieldRelationship existingCcr = readByConstituentFieldDefinitionCustomFieldIds(newCustomFld.getEntityId(), masterFieldDefinitionId, newCustomFld.getValue(), newCustomFld.getStartDate());
    	
    	if (existingCcr != null) {
    		updateConstituentCustomFieldRelationshipValue(newCustomFld, newCustomFld, masterFieldDefinitionId, relationshipCustomizations);
    	}
    	else {
	    	ConstituentCustomFieldRelationship ccr = new ConstituentCustomFieldRelationship();
	    	ccr.setConstituentId(newCustomFld.getEntityId());
	    	ccr.setCustomFieldStartDate(newCustomFld.getStartDate());
	    	ccr.setCustomFieldValue(newCustomFld.getValue());
	    	ccr.setMasterFieldDefinitionId(masterFieldDefinitionId);
			setConstituentCustomFieldRelationshipCustomFields(relationshipCustomizations, ccr);
	    	ccr = maintainConstituentCustomFieldRelationship(ccr);
	
	    	if (NumberUtils.isDigits(newCustomFld.getValue())) {
		    	ConstituentCustomFieldRelationship reverseCcr = new ConstituentCustomFieldRelationship();
		    	reverseCcr.setConstituentId(Long.parseLong(newCustomFld.getValue()));
		    	reverseCcr.setCustomFieldStartDate(newCustomFld.getStartDate());
		    	reverseCcr.setCustomFieldValue(newCustomFld.getEntityId().toString());
		    	reverseCcr.setMasterFieldDefinitionId(masterFieldDefinitionId);
				setConstituentCustomFieldRelationshipCustomFields(relationshipCustomizations, reverseCcr);
		    	reverseCcr = maintainConstituentCustomFieldRelationship(reverseCcr);
	    	}
    	}
    }
    
    private void setConstituentCustomFieldRelationshipCustomFields(Map<String, Object> relationshipCustomizations, ConstituentCustomFieldRelationship constituentCustomFieldRelationship) {
    	constituentCustomFieldRelationship.clearCustomFieldMap();
    	
    	if (relationshipCustomizations != null) {
	    	for (Map.Entry<String, Object> entry : relationshipCustomizations.entrySet()) {
	    		String key = entry.getKey();
	    		Object valueObj = entry.getValue();
	    		if (valueObj == null || StringConstants.EMPTY.equals(valueObj.toString().trim())) {
	    			valueObj = StringConstants.BLANK_CUSTOM_FIELD_VALUE;
	    		}
				constituentCustomFieldRelationship.addCustomFieldValue(key, valueObj.toString());
			}
    	}
    }

    @Override
	public ConstituentCustomFieldRelationship findConstituentCustomFieldRelationships(Long constituentId, String masterFieldDefinitionId, String customFieldValue, Date customFieldStartDate, 
			Map<String, Object> defaultRelationshipCustomizations) {
        ConstituentCustomFieldRelationship constituentCustomFieldRelationship = 
        	readByConstituentFieldDefinitionCustomFieldIds(constituentId, masterFieldDefinitionId, customFieldValue, customFieldStartDate);
        ensureDefaultFieldsAndValuesExist(constituentCustomFieldRelationship, defaultRelationshipCustomizations);
        
        ConstituentCustomFieldRelationship reverseConstituentCustomFieldRelationship = null;
        if (NumberUtils.isDigits(customFieldValue)) {
        	reverseConstituentCustomFieldRelationship = readByConstituentFieldDefinitionCustomFieldIds(new Long(customFieldValue), masterFieldDefinitionId, 
        			constituentId.toString(), customFieldStartDate);
        	ensureDefaultFieldsAndValuesExist(reverseConstituentCustomFieldRelationship, defaultRelationshipCustomizations);
        }
        
        mergeFieldsFromReverseRelationship(constituentCustomFieldRelationship, reverseConstituentCustomFieldRelationship); 
		
        return constituentCustomFieldRelationship;
	}
	
	/**
	 * 
	 * @param constituentCustomFieldRelationship
	 * @param reverseConstituentCustomFieldRelationship
	 */
	private void mergeFieldsFromReverseRelationship(ConstituentCustomFieldRelationship constituentCustomFieldRelationship, ConstituentCustomFieldRelationship reverseConstituentCustomFieldRelationship) {
		if (reverseConstituentCustomFieldRelationship != null) {
			Iterator<Map.Entry<String, CustomField>> it = reverseConstituentCustomFieldRelationship.getCustomFieldMap().entrySet().iterator();
			while (it.hasNext()) {
				CustomField cf = it.next().getValue();
				constituentCustomFieldRelationship.setCustomFieldValue(cf.getName(), cf.getValue());
			}
		}
	}	
	
    /**
     * Use the default custom field value on constituentCustomFieldRelationship if it does not exist
     * @param constituentCustomFieldRelationship
     * @param defaultRelationshipCustomizations
     */
	private void ensureDefaultFieldsAndValuesExist(ConstituentCustomFieldRelationship constituentCustomFieldRelationship, Map<String, Object> defaultRelationshipCustomizations) {
		if (defaultRelationshipCustomizations != null && constituentCustomFieldRelationship != null) {
			for (Map.Entry<String, Object> mapEntry : defaultRelationshipCustomizations.entrySet()) {
				String name = mapEntry.getKey();
				String value = constituentCustomFieldRelationship.getCustomFieldValue(name);
				if (StringConstants.BLANK_CUSTOM_FIELD_VALUE.equals(value)) {
					constituentCustomFieldRelationship.setCustomFieldValue(name, StringConstants.EMPTY);
				}
				else if (StringUtils.hasText(value) == false) {
					constituentCustomFieldRelationship.setCustomFieldValue(name, mapEntry.getValue().toString());
				}
			}
		}
	}

}
