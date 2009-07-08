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
import java.util.Date;
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
	private String displayStartDate;
	private String displayEndDate;
	private Date startDate;
	private Date endDate;
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

	public String getDisplayStartDate() {
		return displayStartDate;
	}
	
	public void setDisplayStartDate(String startDate) {
		this.displayStartDate = startDate;
	}
	
	public String getDisplayEndDate() {
		return displayEndDate;
	}

	public void setDisplayEndDate(String endDate) {
		this.displayEndDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
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
