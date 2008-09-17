package com.mpower.domain.customization;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.listener.TemporalTimestampListener;

@Entity
@EntityListeners(value = { TemporalTimestampListener.class })
@Table(name = "FIELD_CONDITION")
public class FieldCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "CONDITION_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FIELD_REQUIRED_ID")
    private FieldRequired fieldRequired;

    @ManyToOne
    @JoinColumn(name = "VALIDATION_ID")
    private FieldValidation fieldValidation;

    @ManyToOne
    @JoinColumn(name = "DEPENDENT_FIELD_DEFINITION_ID")
    private FieldDefinition dependentFieldDefinition;

    @ManyToOne
    @JoinColumn(name = "DEPENDENT_SECONDARY_FIELD_DEFINITION_ID")
    private FieldDefinition dependentSecondaryFieldDefinition;

    @Column(name = "DEPENDENT_VALUE")
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FieldRequired getFieldRequired() {
        return fieldRequired;
    }

    public void setFieldRequired(FieldRequired fieldRequired) {
        this.fieldRequired = fieldRequired;
    }

    public FieldValidation getFieldValidation() {
        return fieldValidation;
    }

    public void setFieldValidation(FieldValidation fieldValidation) {
        this.fieldValidation = fieldValidation;
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
}