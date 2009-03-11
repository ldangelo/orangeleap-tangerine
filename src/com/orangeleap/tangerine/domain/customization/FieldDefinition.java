package com.orangeleap.tangerine.domain.customization;

import java.io.Serializable;

import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.ReferenceType;
import com.orangeleap.tangerine.util.StringConstants;

public class FieldDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
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
        return fieldName.substring(StringConstants.CUSTOM_FIELD_MAP.length(), fieldName.length() - 1);
    }

    public String getPropertyName() {
        return entityType.name() + "." + fieldName;
    }
    
    public boolean isCustom() {
        return (fieldName != null && fieldName.startsWith(StringConstants.CUSTOM_FIELD_MAP));
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", id).append("fieldName", fieldName).append("defaultLabel", defaultLabel).append("fieldType", fieldType).
                append("entityType", entityType).append("referenceType", referenceType).append("entityAttributes", entityAttributes).append("site", site).toString();
    }

}
