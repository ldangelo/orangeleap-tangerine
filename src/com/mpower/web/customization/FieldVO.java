package com.mpower.web.customization;

import java.util.List;

import com.mpower.domain.type.FieldType;

public class FieldVO {
    public List<String> codes;
    public List<String> displayValues;
    private FieldType fieldType;
    private Long id;
    private Long siteId;
    private String fieldName;
    private String fieldValue;
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

    public Long getSiteId() {
        return siteId;
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

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public void setValidationExpression(String validationExpression) {
        this.validationExpression = validationExpression;
    }

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
}
