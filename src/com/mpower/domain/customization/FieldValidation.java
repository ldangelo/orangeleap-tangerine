package com.mpower.domain.customization;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.listener.EmptyStringNullifyerListener;

@Entity
@EntityListeners(value = { EmptyStringNullifyerListener.class })
@Table(name = "FIELD_VALIDATION", uniqueConstraints = { @UniqueConstraint(columnNames = { "SITE_NAME", "SECTION_NAME", "FIELD_DEFINITION_ID", "SECONDARY_FIELD_DEFINITION_ID" }) })
public class FieldValidation implements Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "VALIDATION_ID")
    private Long id;

    @Column(name = "SITE_NAME")
    private String siteName;

    @Column(name = "SECTION_NAME")
    private String sectionName;

    @Column(name = "FIELD_DEFINITION_ID")
    private String fieldDefinitionId;

    @Column(name = "SECONDARY_FIELD_DEFINITION_ID")
    private String secondaryFieldDefinitionId;

    @Column(name = "VALIDATION_REGEX")
    private String regex;

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

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

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
}
