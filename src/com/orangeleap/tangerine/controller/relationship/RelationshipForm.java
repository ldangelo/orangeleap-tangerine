package com.orangeleap.tangerine.controller.relationship;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.ConstituentCustomFieldRelationship;

public class RelationshipForm {
	
	private List<ConstituentCustomFieldRelationship> relationshipList;

	
	public void setRelationshipList(List<ConstituentCustomFieldRelationship> relationshipList) {
		this.relationshipList = relationshipList;
	}

	public List<ConstituentCustomFieldRelationship> getRelationshipList() {
		return relationshipList;
	}
	
}
