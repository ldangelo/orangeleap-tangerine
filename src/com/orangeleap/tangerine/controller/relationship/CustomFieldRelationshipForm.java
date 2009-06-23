package com.orangeleap.tangerine.controller.relationship;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import com.orangeleap.tangerine.domain.customization.ConstituentCustomFieldRelationship;
import com.orangeleap.tangerine.domain.customization.CustomField;

/**
 * A form bean for custom field relationships
 * @author alexlo
 */
@SuppressWarnings("serial")
public class CustomFieldRelationshipForm implements Serializable {
	private String fieldDefinitionId;
	private String fieldName;
	private String fieldValue;
	private String constituentName;
	private Long customFieldId;
	private String startDate;
	private String endDate;
	private Map<String, Object> relationshipCustomizations;
	private ConstituentCustomFieldRelationship constituentCustomFieldRelationship;
	private ConstituentCustomFieldRelationship reverseConstituentCustomFieldRelationship;

	public CustomFieldRelationshipForm() {
		super();
	}

	public String getFieldDefinitionId() {
		return fieldDefinitionId;
	}

	public void setFieldDefinitionId(String fieldDefinitionId) {
		this.fieldDefinitionId = fieldDefinitionId;
	}

	public String getFieldName() {
		return fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getConstituentName() {
		return constituentName;
	}
	
	public void setConstituentName(String constituentName) {
		this.constituentName = constituentName;
	}
	
	public Long getCustomFieldId() {
		return customFieldId;
	}

	public void setCustomFieldId(Long customFieldId) {
		this.customFieldId = customFieldId;
	}

	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Map<String, Object> getRelationshipCustomizations() {
		return relationshipCustomizations;
	}
	
	public void setRelationshipCustomizations(Map<String, Object> relationshipCustomizations) {
		this.relationshipCustomizations = relationshipCustomizations;
	}

	public void setRelationshipCustomizationsFromCustomFields(Map<String, Object> defaultCustomizations, Map<String, CustomField> relationshipCustomizations) {
		Map<String, Object> result = new TreeMap<String, Object>();
		for (Map.Entry<String, CustomField> entry : relationshipCustomizations.entrySet()) {
			if (defaultCustomizations.containsKey(entry.getKey())) {
				result.put(entry.getValue().getName(), entry.getValue().getValue());
			}
		}

		this.relationshipCustomizations = result;
	}

	public ConstituentCustomFieldRelationship getConstituentCustomFieldRelationship() {
		return constituentCustomFieldRelationship;
	}

	public void setConstituentCustomFieldRelationship(ConstituentCustomFieldRelationship constituentCustomFieldRelationship) {
		this.constituentCustomFieldRelationship = constituentCustomFieldRelationship;
	}

	public ConstituentCustomFieldRelationship getReverseConstituentCustomFieldRelationship() {
		return reverseConstituentCustomFieldRelationship;
	}

	public void setReverseConstituentCustomFieldRelationship(ConstituentCustomFieldRelationship reverseConstituentCustomFieldRelationship) {
		this.reverseConstituentCustomFieldRelationship = reverseConstituentCustomFieldRelationship;
	}

}
