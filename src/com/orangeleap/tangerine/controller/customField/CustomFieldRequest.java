package com.orangeleap.tangerine.controller.customField;

import com.orangeleap.tangerine.type.FieldType;

public class CustomFieldRequest {
	
	private String label;
	private String entityType;
	private String constituentType;
	private String section;
	private String fieldName;
	private FieldType fieldType;
	private String validationType;
	private String regex;
	
	

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setConstituentType(String constituentType) {
		this.constituentType = constituentType;
	}

	public String getConstituentType() {
		return constituentType;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSection() {
		return section;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}

	public FieldType getFieldType() {
		return fieldType;
	}

	public void setValidationType(String validationType) {
		this.validationType = validationType;
	}

	public String getValidationType() {
		return validationType;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getRegex() {
		return regex;
	}

}
