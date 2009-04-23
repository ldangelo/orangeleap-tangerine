package com.orangeleap.tangerine.service;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.CustomFieldRelationship;

public interface CustomFieldRelationshipService {

	  public CustomFieldRelationship readById(Long id);
	    
	  public List<CustomFieldRelationship> maintainCustomFieldRelationships(List<CustomFieldRelationship> list);

	  public CustomFieldRelationship maintainCustomFieldRelationshipCustomFields(CustomFieldRelationship customFieldRelationship);
	  
      public List<CustomFieldRelationship> readAllBySite();

	
}
