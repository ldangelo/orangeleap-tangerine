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
