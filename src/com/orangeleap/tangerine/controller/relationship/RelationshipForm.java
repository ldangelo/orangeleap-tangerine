package com.orangeleap.tangerine.controller.relationship;

import java.util.List;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;

public class RelationshipForm {
	
	private FieldDefinition fieldDefinition;
	private Person person;
	private List<CustomField> customFieldList;
	private String fieldLabel;
	
	public void setFieldDefinition(FieldDefinition fieldDefinition) {
		this.fieldDefinition = fieldDefinition;
	}
	public FieldDefinition getFieldDefinition() {
		return fieldDefinition;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Person getPerson() {
		return person;
	}
	public void setCustomFieldList(List<CustomField> relationshipList) {
		this.customFieldList = relationshipList;
	}
	public List<CustomField> getCustomFieldList() {
		return customFieldList;
	}
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}
	public String getFieldLabel() {
		return fieldLabel;
	}
}
