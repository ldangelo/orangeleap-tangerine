package com.orangeleap.tangerine.service;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.CustomFieldRelationship;

public interface CustomFieldRelationshipService {

	  public CustomFieldRelationship readById(Long id);
	  
	  public String getMasterFieldDefinitionId(String id);
	    
	  public CustomFieldRelationship readByFieldDefinitionId(String id);
	    
	  public CustomFieldRelationship maintainCustomFieldRelationshipCustomFields(CustomFieldRelationship customFieldRelationship);
	  
      public List<CustomFieldRelationship> readAllBySite();
      
	
}
