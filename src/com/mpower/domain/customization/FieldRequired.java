package com.mpower.domain.customization;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Entity
@Table(name = "FIELD_REQUIRED")
public class FieldRequired implements Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "FIELD_REQUIRED_ID")
    private Long id;

    @Column(name = "SITE_NAME")
    private String siteName;

    @Column(name = "SECTION_NAME")
    private String sectionName;

    @ManyToOne
    @JoinColumn(name = "FIELD_DEFINITION_ID")
    private FieldDefinition fieldDefinition;

    @ManyToOne
    @JoinColumn(name = "SECONDARY_FIELD_DEFINITION_ID")
    private FieldDefinition secondaryFieldDefinition;

    @Column(name = "REQUIRED")
    private boolean required = false;

    @OneToMany(mappedBy = "fieldRequired", cascade = CascadeType.ALL)
    private List<FieldCondition> fieldConditions;

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

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public List<FieldCondition> getFieldConditions() {
        return fieldConditions;
    }

    public void setFieldConditions(List<FieldCondition> fieldConditions) {
        this.fieldConditions = fieldConditions;
    }

    public boolean hasConditions() {
        return fieldConditions != null && fieldConditions.size() > 0;
    }
}
