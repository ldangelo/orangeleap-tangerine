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

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.ReferenceType;
import com.orangeleap.tangerine.util.StringConstants;
@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public class FieldDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private long numericId;
    private EntityType entityType;
    private ReferenceType referenceType;
    private String fieldName;
    private String defaultLabel;
    private FieldType fieldType;
    private String entityAttributes;
    private Site site;
    /**
     * Field Info provides the details needed for some field types: lookup --> contains the Java classname for the entity
     */
    private String fieldInfo;

    public FieldDefinition() { }

    public FieldDefinition(String fieldName) {
        this();
        this.fieldName = fieldName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public ReferenceType getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(ReferenceType referenceType) {
        this.referenceType = referenceType;
    }

    public String getFieldInfo() {
        return fieldInfo;
    }

    public void setFieldInfo(String fieldInfo) {
        this.fieldInfo = fieldInfo;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    
    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getDefaultLabel() {
        return defaultLabel;
    }

    public void setDefaultLabel(String defaultLabel) {
        this.defaultLabel = defaultLabel;
    }

    public void setEntityAttributes(String entityAttributes) {
        this.entityAttributes = entityAttributes;
    }

    public String getEntityAttributes() {
        return entityAttributes;
    }
    
    // Helper Methods
    public String getCustomFieldName() {
        if (!isCustom()) {
            return "";
        }
        return fieldName.substring(StringConstants.CUSTOM_FIELD_MAP_START.length(), fieldName.length() - 1);
    }

    public String getPropertyName() {
        return entityType.name() + "." + fieldName;
    }
    
    public boolean isCustom() {
        return (fieldName != null && fieldName.startsWith(StringConstants.CUSTOM_FIELD_MAP_START));
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", id).append("fieldName", fieldName).append("defaultLabel", defaultLabel).append("fieldType", fieldType).
                append("entityType", entityType).append("referenceType", referenceType).append("entityAttributes", entityAttributes).append("site", site).toString();
    }

	public void setNumericId(long numericId) {
		this.numericId = numericId;
	}

	public long getNumericId() {
		return numericId;
	}

}
