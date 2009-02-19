package com.mpower.dao.interfaces;

import java.util.List;

import com.mpower.domain.model.customization.FieldRelationship;

public interface FieldDao {
    
    public List<FieldRelationship> readMasterFieldRelationships(String masterFieldDefId);
    public List<FieldRelationship> readDetailFieldRelationships(String masterFieldDefId);
//    public FieldDefinition readFieldDefinition(String fieldDefinitionId);

//    public FieldRequired readFieldRequired(String siteName, String sectionName, String fieldDefinitionId, String secondaryFieldDefinitionId);

//    public FieldValidation readFieldValidation(String siteName, String sectionName, String fieldDefinitionId, String secondaryFieldDefinitionId);
}
