package com.mpower.service.customization;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.mpower.dao.interfaces.FieldDao;
import com.mpower.dao.interfaces.PicklistDao;
import com.mpower.domain.model.customization.FieldRequired;
import com.mpower.domain.model.customization.FieldValidation;
import com.mpower.domain.model.customization.Picklist;
import com.mpower.domain.model.customization.SectionField;
import com.mpower.type.EntityType;

// TODO: Need a service to clear the cache and this class needs to observe that class
@Service("fieldService")
public class FieldServiceImpl implements FieldService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "fieldDAO")
    private FieldDao fieldDao;
    
    @Resource(name = "picklistDAO")
    private PicklistDao picklistDao;

    @Override
    public FieldRequired lookupFieldRequired(SectionField currentField) {
        if (logger.isDebugEnabled()) {
            logger.debug("lookupFieldRequired: currentField = " + currentField);
        }
        return fieldDao.readFieldRequired(currentField.getSectionDefinition().getSectionName(), currentField.getFieldDefinition().getId(), currentField.getSecondaryFieldDefinition() == null ? null : currentField.getSecondaryFieldDefinition().getId());
    }

    @Override
    public FieldValidation lookupFieldValidation(SectionField currentField) {
        if (logger.isDebugEnabled()) {
            logger.debug("lookupFieldValidation: currentField = " + currentField);
        }
        return fieldDao.readFieldValidation(currentField.getSectionDefinition().getSectionName(), currentField.getFieldDefinition().getId(), currentField.getSecondaryFieldDefinition() == null ? null : currentField.getSecondaryFieldDefinition().getId());
    }

    @Override
    public Picklist readPicklistByFieldNameEntityType(String fieldName, EntityType entityType) {
        if (logger.isDebugEnabled()) {
            logger.debug("readPicklistByFieldNameEntityType: fieldName = " + fieldName + " entityType = " + entityType);
        }
        return picklistDao.readPicklistByFieldName(fieldName, entityType);
    }
}