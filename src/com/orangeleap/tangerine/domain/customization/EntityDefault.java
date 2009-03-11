package com.orangeleap.tangerine.domain.customization;

import java.io.Serializable;

import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.domain.GeneratedId;

public class EntityDefault implements GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    public EntityDefault() {
        super();
    }

    public EntityDefault(String defaultValue, String entityFieldName, String entityType, String siteName) {
        this();
        this.defaultValue = defaultValue;
        this.entityFieldName = entityFieldName;
        this.entityType = entityType;
        this.siteName = siteName;
    }

    private Long id;
    private String defaultValue;
    /**
     * Should be a field on the specified entity (e.g. "lastName" for Person)
     */
    private String entityFieldName;
    private String entityType;
    private String siteName;

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

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", id).append("defaultValue", defaultValue).append("entityFieldName", entityFieldName).append("entityType", entityType).
                append("siteName", siteName).toString();
    }
}