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

package com.orangeleap.tangerine.controller.customField;

import com.orangeleap.tangerine.type.FieldType;

public class CustomFieldRequest {

    private String label;
    private String entityType;
    private String constituentType;
    private String fieldName;
    private FieldType fieldType;
    private String validationType;
    private String regex;
    private String relateToField;
    private String referenceConstituentType;


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

    public void setRelateToField(String relateToField) {
        this.relateToField = relateToField;
    }

    public String getRelateToField() {
        return relateToField;
    }

    public void setReferenceConstituentType(String referenceConstituentType) {
        this.referenceConstituentType = referenceConstituentType;
    }

    public String getReferenceConstituentType() {
        return referenceConstituentType;
    }
}
