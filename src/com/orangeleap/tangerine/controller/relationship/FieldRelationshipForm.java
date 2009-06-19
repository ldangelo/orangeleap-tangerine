package com.orangeleap.tangerine.controller.relationship;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class FieldRelationshipForm implements Serializable {
	private String fieldName;
	private String fieldLabel;
	private String fieldDefinitionId;
	private String masterFieldDefinitionId;
	private String relationshipType;	
	private List<CustomFieldRelationshipForm> customFields;
	private boolean hasRelationshipCustomizations;
	private Map<String, Object> defaultRelationshipCustomizations;

	public String getFieldName() {
		return fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getFieldLabel() {
		return fieldLabel;
	}
	
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	public String getFieldDefinitionId() {
		return fieldDefinitionId;
	}

	public void setFieldDefinitionId(String fieldDefinitionId) {
		this.fieldDefinitionId = fieldDefinitionId;
	}

	public String getMasterFieldDefinitionId() {
		return masterFieldDefinitionId;
	}

	public void setMasterFieldDefinitionId(String masterFieldDefinitionId) {
		this.masterFieldDefinitionId = masterFieldDefinitionId;
	}

	public String getRelationshipType() {
		return relationshipType;
	}
	
	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}

	public List<CustomFieldRelationshipForm> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(List<CustomFieldRelationshipForm> customFields) {
		this.customFields = customFields;
	}

	public boolean isHasRelationshipCustomizations() {
		return hasRelationshipCustomizations;
	}

	public void setHasRelationshipCustomizations(boolean hasRelationshipCustomizations) {
		this.hasRelationshipCustomizations = hasRelationshipCustomizations;
	}

	public Map<String, Object> getDefaultRelationshipCustomizations() {
		return defaultRelationshipCustomizations;
	}

	public void setDefaultRelationshipCustomizations(Map<String, Object> defaultRelationshipCustomizations) {
		this.defaultRelationshipCustomizations = defaultRelationshipCustomizations;
	}
}
