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
import org.springframework.core.style.ToStringCreator;

import java.io.Serializable;
import java.util.List;

public class FieldValidation implements GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Site site;
    private String sectionName;
    private FieldDefinition fieldDefinition;
    private FieldDefinition secondaryFieldDefinition;
    private String regex;
    private List<FieldCondition> fieldConditions;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public FieldDefinition getFieldDefinition() {
        return fieldDefinition;
    }

    public void setFieldDefinition(FieldDefinition fieldDefinition) {
        this.fieldDefinition = fieldDefinition;
    }

    public FieldDefinition getSecondaryFieldDefinition() {
        return secondaryFieldDefinition;
    }

    public void setSecondaryFieldDefinition(FieldDefinition secondaryFieldDefinition) {
        this.secondaryFieldDefinition = secondaryFieldDefinition;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public List<FieldCondition> getFieldConditions() {
        return fieldConditions;
    }

    public void setFieldConditions(List<FieldCondition> fieldConditions) {
        this.fieldConditions = fieldConditions;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", id).append("sectionName", sectionName).append("fieldDefinition", fieldDefinition).append("secondaryFieldDefinition", secondaryFieldDefinition).
                append("regex", regex).append("site", site).toString();
    }
}
