package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.ConstituentCustomFieldRelationship;

public interface ConstituentCustomFieldRelationshipDao {

    public ConstituentCustomFieldRelationship readById(Long id);
    
    public ConstituentCustomFieldRelationship maintainConstituentCustomFieldRelationship(ConstituentCustomFieldRelationship constituentCustomFieldRelationship);

	public List<ConstituentCustomFieldRelationship> readAllByConstituentAndFieldRelationship(Long personId, Long fieldRelationshipId);

}
