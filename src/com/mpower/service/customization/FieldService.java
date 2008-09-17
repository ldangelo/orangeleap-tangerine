package com.mpower.service.customization;

import java.util.Locale;

import com.mpower.domain.customization.FieldDefinition;
import com.mpower.domain.customization.FieldRequired;
import com.mpower.domain.customization.FieldValidation;
import com.mpower.domain.customization.Picklist;
import com.mpower.domain.customization.SectionField;
import com.mpower.type.EntityType;

public interface FieldService {
    public FieldDefinition readFieldById(String fieldId);

    public Picklist readPicklistBySiteAndFieldName(String siteName, String fieldName, EntityType entityType);

    public FieldRequired lookupFieldRequired(String siteName, SectionField currentField);

    public FieldValidation lookupFieldValidation(String siteName, SectionField currentField);

    public Object lookupFieldDefaultValue(String siteName, Locale locale, String fieldId);
}
