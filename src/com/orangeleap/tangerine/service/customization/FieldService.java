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

package com.orangeleap.tangerine.service.customization;

import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.FieldValidation;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.type.EntityType;

import java.util.List;
import java.util.Map;

public interface FieldService {
    public Picklist readPicklistByFieldNameEntityType(String fieldName, EntityType entityType);

    public FieldRequired lookupFieldRequired(SectionField currentField);

	public boolean isFieldRequired(SectionField currentField);

    public FieldValidation lookupFieldValidation(SectionField currentField);
    
    public boolean isFieldDisabled(SectionField sectionField, Object model);

	Map<String,List<SectionField>> groupSectionFields(List<SectionField> sectionFields);
}
