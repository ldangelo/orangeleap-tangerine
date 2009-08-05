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

package com.orangeleap.tangerine.domain.customization;

import com.orangeleap.tangerine.domain.GeneratedId;
import org.springframework.core.style.ToStringCreator;

import java.io.Serializable;

public class FieldCondition implements GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private FieldDefinition dependentFieldDefinition;
    private FieldDefinition dependentSecondaryFieldDefinition;
    private String value;
    private Long fieldRequiredId;
    private Long validationId;

    public FieldCondition() { }

    public FieldCondition(FieldDefinition dependentFieldDefinition, FieldDefinition dependentSecondaryFieldDefinition, String value) {
        this();
        this.dependentFieldDefinition = dependentFieldDefinition;
        this.dependentSecondaryFieldDefinition = dependentSecondaryFieldDefinition;
        this.value = value;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public FieldDefinition getDependentFieldDefinition() {
        return dependentFieldDefinition;
    }

    public void setDependentFieldDefinition(FieldDefinition dependentFieldDefinition) {
        this.dependentFieldDefinition = dependentFieldDefinition;
    }

    public FieldDefinition getDependentSecondaryFieldDefinition() {
        return dependentSecondaryFieldDefinition;
    }

    public void setDependentSecondaryFieldDefinition(FieldDefinition dependentSecondaryFieldDefinition) {
        this.dependentSecondaryFieldDefinition = dependentSecondaryFieldDefinition;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getFieldRequiredId() {
        return fieldRequiredId;
    }

    public void setFieldRequiredId(Long fieldRequiredId) {
        this.fieldRequiredId = fieldRequiredId;
    }

    public Long getValidationId() {
        return validationId;
    }

    public void setValidationId(Long validationId) {
        this.validationId = validationId;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", id).append("value", value).append("dependentFieldDefinition", dependentFieldDefinition).append("dependentSecondaryFieldDefinition", dependentSecondaryFieldDefinition).
                append("fieldRequiredId", fieldRequiredId).append("validationId", validationId).toString();
    }
}