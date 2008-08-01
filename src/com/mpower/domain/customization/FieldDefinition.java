package com.mpower.domain.customization;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.Site;
import com.mpower.domain.listener.EmptyStringNullifyerListener;
import com.mpower.type.EntityType;
import com.mpower.type.FieldType;

@Entity
@EntityListeners(value = { EmptyStringNullifyerListener.class })
@Table(name = "FIELD_DEFINITION")
public class FieldDefinition implements Serializable {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "FIELD_DEFINITION_ID")
    private String id;

    @Column(name = "ENTITY_TYPE")
    @Enumerated(EnumType.STRING)
    private EntityType entityType;

    @Column(name = "FIELD_NAME")
    private String fieldName;

    @Column(name = "DEFAULT_LABEL", nullable = false)
    private String defaultLabel;

    @Column(name = "FIELD_TYPE")
    @Enumerated(EnumType.STRING)
    private FieldType fieldType;

    @ManyToOne
    @JoinColumn(name = "SITE_NAME")
    private Site site;

    /**
     * Field Info provides the details needed for some field types: lookup --> contains the Java classname for the entity
     */
    @Column(name = "FIELD_INFO")
    private String fieldInfo;

    public boolean isCustom() {
        return (site != null);
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public String getFieldInfo() {
        return fieldInfo;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getPropertyName() {
        return entityType.name() + "." + fieldName;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public String getId() {
        return id;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public void setFieldInfo(String fieldInfo) {
        this.fieldInfo = fieldInfo;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public void setId(String id) {
        this.id = id;
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
}
