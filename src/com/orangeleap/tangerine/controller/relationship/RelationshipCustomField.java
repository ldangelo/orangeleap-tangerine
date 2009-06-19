package com.orangeleap.tangerine.controller.relationship;

import java.util.Map;
import java.util.TreeMap;

import com.orangeleap.tangerine.domain.customization.CustomField;

public class RelationshipCustomField {
	
	private CustomField customField;
	private int index;
	private Map<String, Object> relationshipCustomizations;
	
	public CustomField getCustomField() {
		return customField;
	}
	
	public void setCustomField(CustomField customField) {
		this.customField = customField;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Map<String, Object> getRelationshipCustomizations() {
		return relationshipCustomizations;
	}
	
	public void setRelationshipCustomizations(Map<String, Object> relationshipCustomizations) {
		this.relationshipCustomizations = relationshipCustomizations;
	}
	
	public void addRelationshipCustomization(String key, Object value) {
		if (this.relationshipCustomizations == null) {
			relationshipCustomizations = new TreeMap<String, Object>();
		}
		relationshipCustomizations.put(key, value);
	}
}
