package com.mpower.domain.model.customization;

import java.io.Serializable;

import com.mpower.domain.GeneratedId;

public class FieldCondition implements GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private FieldDefinition dependentFieldDefinition;
    private FieldDefinition dependentSecondaryFieldDefinition;
    private String value;
    private Long fieldRequiredId;
    private Long validationId;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public FieldDefinition getDependentFieldDefinition() {
        return dependentFieldDefinition;
    }

    public void setDependentFieldDefinition(FieldDefinition dependentFieldDefinition) {
        this.dependentFieldDefinition = dependentFieldDefinition;
    }

    public FieldDefinition getDependentSecondaryFieldDefinition() {
        return dependentSecondaryFieldDefinition;
    }

    public void setDependentSecondaryFieldDefinition(FieldDefinition dependentSecondaryFieldDefinition) {
        this.dependentSecondaryFieldDefinition = dependentSecondaryFieldDefinition;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getFieldRequiredId() {
        return fieldRequiredId;
    }

    public void setFieldRequiredId(Long fieldRequiredId) {
        this.fieldRequiredId = fieldRequiredId;
    }

    public Long getValidationId() {
        return validationId;
    }

    public void setValidationId(Long validationId) {
        this.validationId = validationId;
    }
}