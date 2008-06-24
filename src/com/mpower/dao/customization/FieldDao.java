package com.mpower.dao.customization;

import com.mpower.entity.customization.FieldDefinition;
import com.mpower.entity.customization.Picklist;

public interface FieldDao {
    public FieldDefinition readFieldById(String fieldId);

    public Picklist readPicklistBySiteAndFieldName(Long siteId, String fieldName);

    public boolean readFieldRequired(Long siteId, String sectionName, String fieldDefinitionId, String secondaryFieldDefinitionId);
}
