package com.orangeleap.tangerine.service;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.ConstituentCustomFieldRelationship;
import com.orangeleap.tangerine.domain.customization.CustomField;

public interface ConstituentCustomFieldRelationshipService {

	  public ConstituentCustomFieldRelationship readById(Long id);
	    
	  public List<ConstituentCustomFieldRelationship> maintainConstituentCustomFieldRelationships(List<ConstituentCustomFieldRelationship> list);

	  public ConstituentCustomFieldRelationship maintainConstituentCustomFieldRelationshipCustomFields(ConstituentCustomFieldRelationship constituentCustomFieldRelationship);
	  
	  public List<ConstituentCustomFieldRelationship> readAllByConstituentAndRelationship(Long personId, Long relationshipId);

	  public List<CustomField> readAllCustomFieldsByConstituent(Long personId);

}
