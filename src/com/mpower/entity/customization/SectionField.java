package com.mpower.entity.customization;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.mpower.entity.Site;
import com.mpower.entity.listener.EmptyStringNullifyerListener;
import com.mpower.type.FieldType;

@Entity
@EntityListeners(value = { EmptyStringNullifyerListener.class })
@Table(name = "SECTION_FIELD", uniqueConstraints = @UniqueConstraint(columnNames = { "SECTION_NAME", "FIELD_DEFINITION_ID", "SECONDARY_FIELD_DEFINITION_ID", "SITE_ID" }))
public class SectionField implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "SECTION_FIELD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FIELD_DEFINITION_ID")
    private FieldDefinition fieldDefinition;

    @ManyToOne
    @JoinColumn(name = "SECONDARY_FIELD_DEFINITION_ID")
    private FieldDefinition secondaryFieldDefinition;

    @ManyToOne
    @JoinColumn(name = "SITE_ID")
    private Site site;

    @Column(name = "SECTION_NAME")
    private String sectionName;

    @Column(name = "FIELD_ORDER")
    private Integer fieldOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FieldDefinition getFieldDefinition() {
        return fieldDefinition;
    }

    public void setFieldDefinition(FieldDefinition field) {
        this.fieldDefinition = field;
    }

    public Integer getFieldOrder() {
        return fieldOrder;
    }

    public void setFieldOrder(Integer fieldOrder) {
        this.fieldOrder = fieldOrder;
    }

    public FieldDefinition getSecondaryFieldDefinition() {
        return secondaryFieldDefinition;
    }

    public void setSecondaryFieldDefinition(FieldDefinition secondaryFieldDefinition) {
        this.secondaryFieldDefinition = secondaryFieldDefinition;
    }

    public boolean isCompoundField() {
        return secondaryFieldDefinition != null;
    }

    public String getFieldLabelName() {
        if (!isCompoundField()) {
            return fieldDefinition.getId();
        } else {
            return secondaryFieldDefinition.getId();
        }
    }

    public String getPicklistName() {
        if (!isCompoundField()) {
            return fieldDefinition.getFieldName();
        } else {
            return secondaryFieldDefinition.getFieldName();
        }
    }

    public String getFieldRequiredName() {
        if (!isCompoundField()) {
            return fieldDefinition.getId();
        } else {
            return fieldDefinition.getEntityType().name() + "." + fieldDefinition.getFieldInfo() + "." + secondaryFieldDefinition.getFieldName();
        }
    }

    public FieldType getFieldType() {
        if (!isCompoundField()) {
            return fieldDefinition.getFieldType();
        } else {
            return secondaryFieldDefinition.getFieldType();
        }
    }

    public String getFieldPropertyName() {
        if (!isCompoundField()) {
            return fieldDefinition.getFieldName();
        } else {
            return fieldDefinition.getFieldName() + "." + secondaryFieldDefinition.getFieldName();
        }
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
