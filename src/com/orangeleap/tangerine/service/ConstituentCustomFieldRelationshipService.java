package com.orangeleap.tangerine.service;

import java.util.Date;
import java.util.Map;

import com.orangeleap.tangerine.domain.customization.ConstituentCustomFieldRelationship;
import com.orangeleap.tangerine.domain.customization.CustomField;

public interface ConstituentCustomFieldRelationshipService {

	public ConstituentCustomFieldRelationship readById(Long id);
	
	public ConstituentCustomFieldRelationship readByConstituentFieldDefinitionCustomFieldIds(Long constituentId, String fieldDefinitionId, String customFieldValue, Date customFieldStartDate);
	  
	public ConstituentCustomFieldRelationship maintainConstituentCustomFieldRelationship(ConstituentCustomFieldRelationship constituentCustomFieldRelationship);
	  
	public void deleteConstituentCustomFieldRelationship(Long entityId, String masterFieldDefinitionId, String value, Date startDate);

	void deleteConstituentCustomFieldRelationship(CustomField oldCustomFld, String masterFieldDefinitionId);

	ConstituentCustomFieldRelationship findConstituentCustomFieldRelationships(Long constituentId, String masterFieldDefinitionId, String customFieldValue, Date customFieldStartDate, Map<String, Object> defaultRelationshipCustomizations);

	void updateConstituentCustomFieldRelationshipValue(CustomField newCustomFld, CustomField oldCustomFld, String masterFieldDefinitionId, Map<String, Object> relationshipCustomizations);

	void saveNewConstituentCustomFieldRelationship(CustomField newCustomFld, String masterFieldDefinitionId, Map<String, Object> relationshipCustomizations);
}
