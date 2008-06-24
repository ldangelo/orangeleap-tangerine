package com.mpower.entity.customization;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.mpower.type.EntityType;

@Entity
@Table(name = "ENTITY_DEFAULT", uniqueConstraints = { @UniqueConstraint(columnNames = { "SITE_ID", "ENTITY_FIELD_NAME" }) })
public class EntityDefault implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ENTITY_DEFAULT_ID")
    private Long id;

    @Column(name = "SITE_ID")
    private Long siteId;

    @Column(name="ENTITY_TYPE")
    @Enumerated(EnumType.STRING)
    private EntityType entityType;

    /*
     * Should be a field on the specified entity (e.g. "lastName" for Person)
     */
    @Column(name = "ENTITY_FIELD_NAME")
    private String entityFieldName;

    @Column(name = "DEFAULT_VALUE")
    private String defaultValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public String getEntityFieldName() {
        return entityFieldName;
    }

    public void setEntityFieldName(String entityFieldName) {
        this.entityFieldName = entityFieldName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
