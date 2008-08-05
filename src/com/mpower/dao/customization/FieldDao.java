package com.mpower.dao.customization;

import com.mpower.domain.customization.FieldDefinition;
import com.mpower.domain.customization.Picklist;

public interface FieldDao {
    public FieldDefinition readFieldById(String fieldId);

    public Picklist readPicklistBySiteAndFieldName(String siteName, String fieldName);

    public boolean readFieldRequired(String siteName, String sectionName, String fieldDefinitionId, String secondaryFieldDefinitionId);

    public String readFieldValidation(String siteName, Long sectionFieldId);
}
