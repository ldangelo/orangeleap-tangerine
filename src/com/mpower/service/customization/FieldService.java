package com.mpower.service.customization;

import java.util.Locale;

import com.mpower.domain.customization.FieldDefinition;
import com.mpower.domain.customization.Picklist;
import com.mpower.domain.customization.SectionField;


public interface FieldService {
    public FieldDefinition readFieldById(String fieldId);

    public Picklist readPicklistBySiteAndFieldName(String siteName, String fieldName);

    public boolean lookupFieldRequired(String siteName, SectionField currentField);

    public String lookupFieldValidation(String siteName, SectionField currentField);

    public Object lookupFieldDefaultValue(String siteName, Locale locale, String fieldId);
}
