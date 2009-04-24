package com.orangeleap.tangerine.controller.relationship;

import java.util.List;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.customization.ConstituentCustomFieldRelationship;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.FieldRelationship;

public class RelationshipForm {
	
	private FieldRelationship fieldRelationship;
	private Person person;
	private List<CustomField> relationshipList;
	private String fieldLabel;
	
	public void setFieldRelationship(FieldRelationship fieldRelationship) {
		this.fieldRelationship = fieldRelationship;
	}
	public FieldRelationship getFieldRelationship() {
		return fieldRelationship;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Person getPerson() {
		return person;
	}
	public void setRelationshipList(List<CustomField> relationshipList) {
		this.relationshipList = relationshipList;
	}
	public List<CustomField> getRelationshipList() {
		return relationshipList;
	}
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}
	public String getFieldLabel() {
		return fieldLabel;
	}
}
