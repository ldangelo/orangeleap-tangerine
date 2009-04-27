package com.orangeleap.tangerine.service;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.ConstituentCustomFieldRelationship;

public interface ConstituentCustomFieldRelationshipService {

	  public ConstituentCustomFieldRelationship readById(Long id);
	    
	  public List<ConstituentCustomFieldRelationship> maintainConstituentCustomFieldRelationships(List<ConstituentCustomFieldRelationship> list);

	  public ConstituentCustomFieldRelationship maintainConstituentCustomFieldRelationshipCustomFields(ConstituentCustomFieldRelationship constituentCustomFieldRelationship);
	  
}
