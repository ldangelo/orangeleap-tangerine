package com.orangeleap.tangerine.service.customization;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.dao.PicklistDao;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.communication.AbstractCommunicationEntity;
import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.FieldValidation;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineUserHelper;

// TODO: Need a service to clear the cache and this class needs to observe that class
@Service("fieldService")
public class FieldServiceImpl implements FieldService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "constituentService")
    private ConstituentService constituentService;

    @Resource(name = "fieldDAO")
    private FieldDao fieldDao;
    
    @Resource(name = "picklistDAO")
    private PicklistDao picklistDao;

    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;

    @Resource(name = "picklistCache")
    private Cache picklistCache;

    @Override
    public FieldRequired lookupFieldRequired(SectionField currentField) {
        if (logger.isTraceEnabled()) {
            logger.trace("lookupFieldRequired: currentField = " + currentField);
        }

        String key = buildRequiredFieldCacheKey(currentField);

        Element ele = picklistCache.get(key);
        FieldRequired fieldRequired = null;

        if(ele == null) {
            fieldRequired = fieldDao.readFieldRequired(currentField.getSectionDefinition().getSectionName(), currentField.getFieldDefinition().getId(), currentField.getSecondaryFieldDefinition() == null ? null : currentField.getSecondaryFieldDefinition().getId());
            picklistCache.put(new Element(key, fieldRequired));
        } else {
            fieldRequired = (FieldRequired) ele.getValue();
        }
        
        return fieldRequired;
    }

    @Override
    public FieldValidation lookupFieldValidation(SectionField currentField) {
        if (logger.isTraceEnabled()) {
            logger.trace("lookupFieldValidation: currentField = " + currentField);
        }
        return fieldDao.readFieldValidation(currentField.getSectionDefinition().getSectionName(), currentField.getFieldDefinition().getId(), currentField.getSecondaryFieldDefinition() == null ? null : currentField.getSecondaryFieldDefinition().getId());
    }

    @Override
    public Picklist readPicklistByFieldNameEntityType(String fieldName, EntityType entityType) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPicklistByFieldNameEntityType: fieldName = " + fieldName + " entityType = " + entityType);
        }

        String siteName = tangerineUserHelper.lookupUserSiteName();
        siteName = (siteName == null ? "DEFAULT" : siteName);
        String key = siteName + "." + fieldName + "." + entityType.name();

        Element ele = picklistCache.get(key);
        Picklist picklist = null;

        if(ele == null) {
            picklist = picklistDao.readPicklistByFieldName(fieldName, entityType);
            picklistCache.put(new Element(key,picklist));
        } else {
            picklist = (Picklist)ele.getValue();
        }

        return picklist;
    }

    private String buildRequiredFieldCacheKey(SectionField sectionField) {
        String siteName = tangerineUserHelper.lookupUserSiteName();
        siteName = (siteName == null ? "DEFAULT" : siteName);

        StringBuilder builder = new StringBuilder(siteName);
        builder.append(".").append(sectionField.getSectionDefinition().getSectionName());
        builder.append(".").append(sectionField.getFieldDefinition().getId());
        if(sectionField.getSecondaryFieldDefinition() == null) {
            builder.append(".").append("NULL");
        } else {
            builder.append(".").append(sectionField.getSecondaryFieldDefinition().getId());
        }
        return builder.toString();
    }
    
    @Override
    public boolean isFieldDisabled(SectionField sectionField, Object model) {
        boolean isDisabled = false;
        
        if ((EntityType.address.equals(sectionField.getFieldDefinition().getEntityType()) || 
                EntityType.phone.equals(sectionField.getFieldDefinition().getEntityType()) || 
                EntityType.email.equals(sectionField.getFieldDefinition().getEntityType())) && 
                ("receiveCorrespondence".equals(sectionField.getFieldDefinition().getFieldName()) || "receiveCorrespondenceText".equals(sectionField.getFieldDefinition().getFieldName())) && 
                model instanceof AbstractCommunicationEntity) {
            
            AbstractCommunicationEntity entity = (AbstractCommunicationEntity) model;
            Person constituent = constituentService.readConstituentById(entity.getPersonId());
            if (constituent != null) {
                String communicationPref = constituent.getCustomFieldValue(StringConstants.COMMUNICATION_PREFERENCES);
                if (StringConstants.OPT_OUT_ALL.equals(communicationPref)) {
                    isDisabled = true;
                    //Opt Out-All, Unknown, Any, Email, Mail, Text, Phone
                }
                else if (StringConstants.OPT_IN.equals(communicationPref)) {
                    if (EntityType.address.equals(sectionField.getFieldDefinition().getEntityType())) {
                        if (constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.MAIL_CAMEL_CASE) ||
                                constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.ANY_CAMEL_CASE) || 
                                constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.UNKNOWN_CAMEL_CASE)) {
                            // do nothing
                        }
                        else {
                            isDisabled = true;
                        }
                    }
                    else if (EntityType.phone.equals(sectionField.getFieldDefinition().getEntityType())) {
                    	if ("receiveCorrespondence".equals(sectionField.getFieldDefinition().getFieldName())) {
	                        if (constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.PHONE_CAMEL_CASE) ||
	                                constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.ANY_CAMEL_CASE) || 
	                                constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.UNKNOWN_CAMEL_CASE)) {
	                            // do nothing
	                        }
	                        else {
	                            isDisabled = true;
	                        }
                    	}
                    	if ("receiveCorrespondenceText".equals(sectionField.getFieldDefinition().getFieldName())) {
	                        if (constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.TEXT_CAMEL_CASE) ||
	                                constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.ANY_CAMEL_CASE) || 
	                                constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.UNKNOWN_CAMEL_CASE)) {
	                            // do nothing
	                        }
	                        else {
	                            isDisabled = true;
	                        }
                    	}
                    }
                    else if (EntityType.email.equals(sectionField.getFieldDefinition().getEntityType())) {
                        if (constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.EMAIL_CAMEL_CASE) ||
                                constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.ANY_CAMEL_CASE) || 
                                constituent.hasCustomFieldValue(StringConstants.COMMUNICATION_OPT_IN_PREFERENCES, StringConstants.UNKNOWN_CAMEL_CASE)) {
                            // do nothing
                        }
                        else {
                            isDisabled = true;
                        }
                    }
                }
            }
        }
        return isDisabled;
    }

}