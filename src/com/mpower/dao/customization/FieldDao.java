package com.mpower.dao.customization;

import java.util.List;

import com.mpower.domain.customization.FieldDefinition;
import com.mpower.domain.customization.FieldValidation;
import com.mpower.domain.customization.Picklist;
import com.mpower.domain.customization.RequiredField;
import com.mpower.type.EntityType;

public interface FieldDao {
    public FieldDefinition readFieldById(String fieldId);

    public Picklist readPicklistBySiteAndFieldName(String siteName, String fieldName);

    public boolean readFieldRequired(String siteName, String sectionName, String fieldDefinitionId, String secondaryFieldDefinitionId);

    public List<FieldValidation> readFieldValidations(String siteName, EntityType entityType);

    public List<RequiredField> readRequiredFields(String siteName, EntityType entityType);
}
