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
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.type.FieldType;
import org.springframework.core.style.ToStringCreator;

import java.io.Serializable;

public class SectionField implements GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private FieldDefinition fieldDefinition;
    private FieldDefinition secondaryFieldDefinition;
    private Site site;
    private Integer fieldOrder;
    private SectionDefinition sectionDefinition;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public FieldDefinition getFieldDefinition() {
        return fieldDefinition;
    }

    public void setFieldDefinition(FieldDefinition field) {
        this.fieldDefinition = field;
    }

    public Integer getFieldOrder() {
        return fieldOrder;
    }

    public void setFieldOrder(Integer fieldOrder) {
        this.fieldOrder = fieldOrder;
    }

    public FieldDefinition getSecondaryFieldDefinition() {
        return secondaryFieldDefinition;
    }

    public void setSecondaryFieldDefinition(FieldDefinition secondaryFieldDefinition) {
        this.secondaryFieldDefinition = secondaryFieldDefinition;
    }

    public boolean isCompoundField() {
        return secondaryFieldDefinition != null;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public SectionDefinition getSectionDefinition() {
        return sectionDefinition;
    }

    public void setSectionDefinition(SectionDefinition sectionDefinition) {
        this.sectionDefinition = sectionDefinition;
    }

    public String getFieldLabelName() {
        if (!isCompoundField()) {
            return fieldDefinition.getId();
        } else {
            return secondaryFieldDefinition.getId();
        }
    }

    public String getPicklistName() {
        if (!isCompoundField()) {
            return fieldDefinition.getFieldName();
        } else {
            return secondaryFieldDefinition.getFieldName();
        }
    }

    public String getFieldRequiredName() {
        if (!isCompoundField()) {
            return fieldDefinition.getId();
        } else {
            return fieldDefinition.getEntityType().name() + "." + fieldDefinition.getFieldInfo() + "." + secondaryFieldDefinition.getFieldName();
        }
    }

    public FieldType getFieldType() {
        if (!isCompoundField()) {
            return fieldDefinition.getFieldType();
        } else {
            return secondaryFieldDefinition.getFieldType();
        }
    }

    public String getFieldPropertyName() {
        if (!isCompoundField()) {
            return fieldDefinition.getFieldName();
        } else {
            return fieldDefinition.getFieldName() + "." + secondaryFieldDefinition.getFieldName();
        }
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", id).append("fieldDefinition", fieldDefinition).append("secondaryFieldDefinition", secondaryFieldDefinition).append("site", site).
                append("fieldOrder", fieldOrder).append("sectionDefinition", sectionDefinition).toString();
    }
}
