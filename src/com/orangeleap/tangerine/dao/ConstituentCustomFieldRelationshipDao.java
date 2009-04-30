package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.ConstituentCustomFieldRelationship;
import com.orangeleap.tangerine.domain.customization.CustomField;

public interface ConstituentCustomFieldRelationshipDao {

    public ConstituentCustomFieldRelationship readById(Long id);
    
    public ConstituentCustomFieldRelationship maintainConstituentCustomFieldRelationship(ConstituentCustomFieldRelationship constituentCustomFieldRelationship);

	public List<ConstituentCustomFieldRelationship> readAllByConstituentAndField(Long personId, String fieldDefinitionId);
	

}
