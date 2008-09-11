package com.mpower.dao.customization;

import com.mpower.domain.customization.FieldDefinition;
import com.mpower.domain.customization.Picklist;
import com.mpower.type.EntityType;

public interface FieldDao {
    public FieldDefinition readFieldById(String fieldId);

    public Picklist readPicklistBySiteAndFieldName(String siteName, String fieldName, EntityType entityType);

    public boolean readFieldRequired(String siteName, String sectionName, String fieldDefinitionId, String secondaryFieldDefinitionId);

    public String readFieldValidation(String siteName, String sectionName, String fieldDefinitionId, String secondaryFieldDefinitionId);
}
