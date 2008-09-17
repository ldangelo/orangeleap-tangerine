package com.mpower.service.customization;

import java.util.Locale;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.mpower.dao.customization.FieldDao;
import com.mpower.domain.customization.FieldDefinition;
import com.mpower.domain.customization.FieldRequired;
import com.mpower.domain.customization.FieldValidation;
import com.mpower.domain.customization.Picklist;
import com.mpower.domain.customization.SectionField;
import com.mpower.type.EntityType;

// TODO: Need a service to clear the cache and this class needs to observe that class
@Service("fieldService")
public class FieldServiceImpl implements FieldService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "fieldDao")
    private FieldDao fieldDao;

    public Object lookupFieldDefaultValue(String siteName, Locale locale, String fieldId) {
        // TODO Auto-generated method stub
        return null;
    }

    public FieldRequired lookupFieldRequired(String siteName, SectionField currentField) {
        return fieldDao.readFieldRequired(siteName, currentField.getSectionDefinition().getSectionName(), currentField.getFieldDefinition().getId(), currentField.getSecondaryFieldDefinition() == null ? null : currentField.getSecondaryFieldDefinition().getId());
    }

    public FieldValidation lookupFieldValidation(String siteName, SectionField currentField) {
        return fieldDao.readFieldValidation(siteName, currentField.getSectionDefinition().getSectionName(), currentField.getFieldDefinition().getId(), currentField.getSecondaryFieldDefinition() == null ? null : currentField.getSecondaryFieldDefinition().getId());
    }

    public FieldDefinition readFieldById(String fieldId) {
        return fieldDao.readFieldById(fieldId);
    }

    @Override
    public Picklist readPicklistBySiteAndFieldName(String siteName, String fieldName, EntityType entityType) {
        return fieldDao.readPicklistBySiteAndFieldName(siteName, fieldName, entityType);
    }
}