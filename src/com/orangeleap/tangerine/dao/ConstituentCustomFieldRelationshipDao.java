package com.orangeleap.tangerine.dao;

import java.util.Date;

import com.orangeleap.tangerine.domain.customization.ConstituentCustomFieldRelationship;

public interface ConstituentCustomFieldRelationshipDao {

    public ConstituentCustomFieldRelationship readById(Long id);
    
	public ConstituentCustomFieldRelationship readByConstituentFieldDefinitionCustomFieldIds(Long constituentId, String fieldDefinitionId, String customFieldValue, Date customFieldStartDate);
    
    public ConstituentCustomFieldRelationship maintainConstituentCustomFieldRelationship(ConstituentCustomFieldRelationship constituentCustomFieldRelationship);


}
