package com.mpower.service.customization;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mpower.dao.customization.FieldDao;
import com.mpower.domain.customization.FieldDefinition;
import com.mpower.domain.customization.Picklist;
import com.mpower.domain.customization.SectionField;

// TODO: Need a service to clear the cache and this class needs to observe that class
@Service("fieldService")
public class FieldServiceImpl implements FieldService {

    @Resource(name = "fieldDao")
    private FieldDao fieldDao;

    public Object lookupFieldDefaultValue(String siteName, Locale locale, String fieldId) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean lookupFieldRequired(String siteName, SectionField currentField) {
        return fieldDao.readFieldRequired(siteName, currentField.getSectionName(), currentField.getFieldDefinition().getId(), currentField.getSecondaryFieldDefinition() == null ? null : currentField.getSecondaryFieldDefinition().getId());
    }

    public FieldDefinition readFieldById(String fieldId) {
        return fieldDao.readFieldById(fieldId);
    }

    public Picklist readPicklistBySiteAndFieldName(String siteName, String fieldName) {
        return fieldDao.readPicklistBySiteAndFieldName(siteName, fieldName);
    }
}