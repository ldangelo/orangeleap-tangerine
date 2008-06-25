package com.mpower.domain.customization;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.mpower.domain.listener.EmptyStringNullifyerListener;

@Entity
@EntityListeners(value = { EmptyStringNullifyerListener.class })
@Table(name = "FIELD_REQUIRED", uniqueConstraints = { @UniqueConstraint(columnNames = { "SITE_ID", "SECTION_NAME", "FIELD_DEFINITION_ID", "SECONDARY_FIELD_DEFINITION_ID" }) })
public class FieldRequired implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "FIELD_REQUIRED_ID")
    private Long id;

    @Column(name = "SITE_ID")
    private Long siteId;

    @Column(name = "SECTION_NAME")
    private String sectionName;

    @Column(name = "FIELD_DEFINITION_ID")
    private String fieldDefinitionId;

    @Column(name = "SECONDARY_FIELD_DEFINITION_ID")
    private String secondaryFieldDefinitionId;

    @Column(name = "REQUIRED")
    private boolean required;

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

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getFieldDefinitionId() {
        return fieldDefinitionId;
    }

    public void setFieldDefinitionId(String fieldDefinitionId) {
        this.fieldDefinitionId = fieldDefinitionId;
    }

    public String getSecondaryFieldDefinitionId() {
        return secondaryFieldDefinitionId;
    }

    public void setSecondaryFieldDefinitionId(String secondaryFieldDefinitionId) {
        this.secondaryFieldDefinitionId = secondaryFieldDefinitionId;
    }
}
