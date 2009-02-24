package com.mpower.domain.model.customization;

import java.io.Serializable;
import java.util.List;

import com.mpower.domain.GeneratedId;
import com.mpower.domain.model.Site;

public class FieldValidation implements GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Site site;
    private String sectionName;
    private FieldDefinition fieldDefinition;
    private FieldDefinition secondaryFieldDefinition;
    private String regex;
    private List<FieldCondition> fieldConditions;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public List<FieldCondition> getFieldConditions() {
        return fieldConditions;
    }

    public void setFieldConditions(List<FieldCondition> fieldConditions) {
        this.fieldConditions = fieldConditions;
    }
}
