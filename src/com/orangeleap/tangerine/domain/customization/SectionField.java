package com.orangeleap.tangerine.domain.customization;

import java.io.Serializable;

import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.domain.GeneratedId;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.type.FieldType;

public class SectionField implements GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private FieldDefinition fieldDefinition;
    private FieldDefinition secondaryFieldDefinition;
    private Site site;
    private Integer fieldOrder;
    private SectionDefinition sectionDefinition;

    @Override
    public Long getId() {
        return id;
    }

    @Override
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

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public SectionDefinition getSectionDefinition() {
        return sectionDefinition;
    }

    public void setSectionDefinition(SectionDefinition sectionDefinition) {
        this.sectionDefinition = sectionDefinition;
    }
    
    public String getFieldLabelName() {
        if (!isCompoundField()) {
            return fieldDefinition.getId();
        } 
        else {
            return secondaryFieldDefinition.getId();
        }
    }

    public String getPicklistName() {
        if (!isCompoundField()) {
            return fieldDefinition.getFieldName();
        } 
        else {
            return secondaryFieldDefinition.getFieldName();
        }
    }

    public String getFieldRequiredName() {
        if (!isCompoundField()) {
            return fieldDefinition.getId();
        } 
        else {
            return fieldDefinition.getEntityType().name() + "." + fieldDefinition.getFieldInfo() + "." + secondaryFieldDefinition.getFieldName();
        }
    }

    public FieldType getFieldType() {
        if (!isCompoundField()) {
            return fieldDefinition.getFieldType();
        } 
        else {
            return secondaryFieldDefinition.getFieldType();
        }
    }

    public String getFieldPropertyName() {
        if (!isCompoundField()) {
            return fieldDefinition.getFieldName();
        } 
        else {
            return fieldDefinition.getFieldName() + "." + secondaryFieldDefinition.getFieldName();
        }
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", id).append("fieldDefinition", fieldDefinition).append("secondaryFieldDefinition", secondaryFieldDefinition).append("site", site).
                append("fieldOrder", fieldOrder).append("sectionDefinition", sectionDefinition).toString();
    }
}
