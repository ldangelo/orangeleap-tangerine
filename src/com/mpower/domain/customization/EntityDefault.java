package com.mpower.domain.customization;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.type.EntityType;

@Entity
@Table(name = "ENTITY_DEFAULT", uniqueConstraints = { @UniqueConstraint(columnNames = { "SITE_NAME", "ENTITY_FIELD_NAME" }) })
public class EntityDefault implements Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "ENTITY_DEFAULT_ID")
    private Long id;

    @Column(name = "SITE_NAME")
    private String siteName;

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

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
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
