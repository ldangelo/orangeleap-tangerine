package com.mpower.web.customization;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.type.FieldType;

public class FieldVO {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    public List<String> referenceValues;
    private boolean cascading;
    public List<String> codes;
    public List<String> displayValues;
    private FieldType fieldType;
    private Long id;
    private String entityName;
    private String siteName;
    private String fieldName;
    private Object fieldValue;
    private Object displayValue;
    private String helpText;
    private String labelText;
    private String validationExpression;
    private boolean helpAvailable;
    private boolean required;

    public List<String> getCodes() {
        return codes;
    }

    public List<String> getDisplayValues() {
        return displayValues;
    }

    public String getFieldName() {
        return fieldName;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public String getHelpText() {
        return helpText;
    }

    public Long getId() {
        return id;
    }

    public String getLabelText() {
        return labelText;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getValidationExpression() {
        return validationExpression;
    }

    public boolean isHelpAvailable() {
        return helpAvailable;
    }

    public boolean isRequired() {
        return required;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    public void setDisplayValues(List<String> displayValues) {
        this.displayValues = displayValues;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public void setHelpAvailable(boolean helpAvailable) {
        this.helpAvailable = helpAvailable;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public void setValidationExpression(String validationExpression) {
        this.validationExpression = validationExpression;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        if (fieldValue instanceof String) {
            this.fieldValue = StringUtils.trimToNull((String) fieldValue);
        } else {
            this.fieldValue = fieldValue;
        }
    }

    public List<String> getReferenceValues() {
        return referenceValues;
    }

    public void setReferenceValues(List<String> referenceValues) {
        this.referenceValues = referenceValues;
    }

    public Object getDisplayValue() {
        return displayValue != null ? displayValue : fieldValue;
    }

    public void setDisplayValue(Object displayValue) {
        this.displayValue = displayValue;
    }

    public boolean isCascading() {
        return cascading;
    }

    public void setCascading(boolean cascading) {
        this.cascading = cascading;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
}
