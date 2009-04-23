package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.CustomFieldRelationship;

public interface CustomFieldRelationshipDao {

    public CustomFieldRelationship readById(Long id);
    
    public CustomFieldRelationship maintainCustomFieldRelationship(CustomFieldRelationship customFieldRelationship);

	public List<CustomFieldRelationship> readAllBySite();

}
