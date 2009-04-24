package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.FieldRelationship;
import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.FieldValidation;

public interface FieldDao {
    
    public FieldRequired readFieldRequired(String sectionName, String fieldDefinitionId, String secondaryFieldDefinitionId);
    public FieldValidation readFieldValidation(String sectionName, String fieldDefinitionId, String secondaryFieldDefinitionId);
    public FieldRelationship readFieldRelationship(Long id);
    public List<FieldRelationship> readMasterFieldRelationships(String masterFieldDefId);
    public List<FieldRelationship> readDetailFieldRelationships(String detailFieldDefId);
}
