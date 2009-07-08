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

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;

import java.util.List;

public class RelationshipForm {

    private FieldDefinition fieldDefinition;
    private Constituent constituent;
    private List<CustomField> customFieldList;
    private List<String> relationshipNames;
    private String fieldLabel;
    private String fieldType;

    public void setFieldDefinition(FieldDefinition fieldDefinition) {
        this.fieldDefinition = fieldDefinition;
    }

    public FieldDefinition getFieldDefinition() {
        return fieldDefinition;
    }

    public void setConstituent(Constituent constituent) {
        this.constituent = constituent;
    }

    public Constituent getConstituent() {
        return constituent;
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

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setRelationshipNames(List<String> relationshipNames) {
        this.relationshipNames = relationshipNames;
    }

    public List<String> getRelationshipNames() {
        return relationshipNames;
    }
}
