package com.mpower.service.customization;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mpower.dao.customization.FieldDao;
import com.mpower.domain.Site;
import com.mpower.domain.customization.FieldDefinition;
import com.mpower.domain.customization.Picklist;
import com.mpower.domain.customization.SectionField;

// TODO: Need a service to clear the cache and this class needs to observe that class
@Service("fieldService")
public class FieldServiceImpl implements FieldService {

    @Resource(name = "fieldDao")
    private FieldDao fieldDao;

    public Object lookupFieldDefaultValue(Site site, Locale locale, String fieldId) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean lookupFieldRequired(Site site, SectionField currentField) {
        return fieldDao.readFieldRequired(site.getId(), currentField.getSectionName(), currentField.getFieldDefinition().getId(), currentField.getSecondaryFieldDefinition() == null ? null : currentField.getSecondaryFieldDefinition().getId());
    }

    public FieldDefinition readFieldById(String fieldId) {
        return fieldDao.readFieldById(fieldId);
    }

    public Picklist readPicklistBySiteAndFieldName(Site site, String fieldName) {
        return fieldDao.readPicklistBySiteAndFieldName(site.getId(), fieldName);
    }
}