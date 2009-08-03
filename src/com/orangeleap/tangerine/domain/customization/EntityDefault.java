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

public class EntityDefault implements GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

	private Long id;
	private String defaultValue;
	/**
	 * Should be a field on the specified entity (e.g. "lastName" for Constituent)
	 */
	private String entityFieldName;
	private String entityType;
	private SectionDefinition sectionDefinition;
	private String conditionExp;
	private String siteName;

	public EntityDefault() {
        super();
    }

	public EntityDefault(String defaultValue) {
		this();
		this.defaultValue = defaultValue;
	}

	public EntityDefault(String defaultValue, String conditionExp) {
		this(defaultValue);
		this.conditionExp = conditionExp;
	}

	public EntityDefault(String defaultValue, String entityFieldName, String entityType, String siteName) {
        this(defaultValue);
        this.entityFieldName = entityFieldName;
        this.entityType = entityType;
        this.siteName = siteName;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

	public String getConditionExp() {
		return conditionExp;
	}

	public void setConditionExp(String conditionExp) {
		this.conditionExp = conditionExp;
	}

	public String getEntityFieldName() {
        return entityFieldName;
    }

    public void setEntityFieldName(String entityFieldName) {
        this.entityFieldName = entityFieldName;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

	public SectionDefinition getSectionDefinition() {
		return sectionDefinition;
	}

	public void setSectionDefinition(SectionDefinition sectionDefinition) {
		this.sectionDefinition = sectionDefinition;
	}

	public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", id).append("defaultValue", defaultValue).
		        append("conditionExp", conditionExp).
		        append("entityFieldName", entityFieldName).append("entityType", entityType).
                append("siteName", siteName).toString();
    }
}