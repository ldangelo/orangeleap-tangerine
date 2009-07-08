/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
