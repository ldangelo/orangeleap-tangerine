package com.mpower.web.customization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.mpower.type.FieldType;

public class FieldVO {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    // TODO: move elsewhere
    public static final String NORMAL_DELIMITER = ",";
    public static final String DISPLAY_VALUE_DELIMITER = "|"; // To be used ONLY on display values that may have commas

    private List<String> referenceValues;
    private boolean cascading;
    private List<String> codes;
    private FieldType fieldType;
    private Long id;
    private List<Long> ids;
    private String entityName;
    private String entityAttributes;
    private String siteName;
    private String fieldName;
    private Object fieldValue;
    private List<String> fieldValues;
    private Object displayValue;
    private List<String> displayValues;
    private String helpText;
    private String labelText;
    private String validationExpression;
    private boolean helpAvailable;
    private boolean required;
    private boolean isHierarchy;
    private boolean isRelationship;

    private Object fieldToCheck;

    public void setFieldToCheck(Object fieldToCheck) {
        this.fieldToCheck = fieldToCheck;
    }

    public List<String> getCodes() {
        return codes;
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

    public void setEntityAttributes(String entityAttributes) {
        this.entityAttributes = entityAttributes;
    }

    public String getEntityAttributes() {
        return entityAttributes;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public List<Long> getIds() {
        return ids;
    }

    @SuppressWarnings("unchecked")
    public List<String> getFieldValues() {
        if (this.fieldValues == null) {
            if (fieldValue == null) {
                return null;
            }
            String[] vals = org.springframework.util.StringUtils.delimitedListToStringArray(fieldValue.toString(), NORMAL_DELIMITER);
            this.fieldValues = new ArrayList<String>(CollectionUtils.arrayToList(vals));
        }
        return fieldValues;
    }

    @SuppressWarnings("unchecked")
    public List<String> getDisplayValues() {
        if (this.displayValues == null) {
            if (displayValue == null) {
                return null;
            }
            String[] vals = org.springframework.util.StringUtils.delimitedListToStringArray(displayValue.toString(), DISPLAY_VALUE_DELIMITER);
            this.displayValues = new ArrayList<String>(CollectionUtils.arrayToList(vals));
        }
        return displayValues;
    }

    public void setDisplayValues(List<String> displayValues) {
        this.displayValues = displayValues;
    }

    public String getCodesString() {
        return getDelimitedString(getCodes(), NORMAL_DELIMITER);
    }

    public String getDisplayValuesString() {
        return getDelimitedString(getDisplayValues(), DISPLAY_VALUE_DELIMITER);
    }

    public String getFieldValuesString() {
        return getDelimitedString(getFieldValues(), NORMAL_DELIMITER);
    }

    public String getReferenceValuesString() {
        return getDelimitedString(getReferenceValues(), NORMAL_DELIMITER);
    }

    public String getIdsString() {
        return getDelimitedString(getIds(), NORMAL_DELIMITER);
    }

    public boolean isHasField() {
        if (getFieldValues() == null || getFieldValues().size() == 0) {
            if (fieldValue == null) {
                if (fieldToCheck == null) {
                    return true;
                }
                return false;
            }
            return fieldValue.equals(fieldToCheck);
        }
        return getFieldValues().contains(fieldToCheck);
    }

    @SuppressWarnings("unchecked")
    private String getDelimitedString(Collection o, String delimiter) {
        return org.springframework.util.StringUtils.collectionToDelimitedString(o, delimiter);
    }

	public void setHierarchy(boolean isHierarchy) {
		this.isHierarchy = isHierarchy;
	}

	public boolean isHierarchy() {
		return isHierarchy;
	}

	public void setRelationship(boolean isRelationship) {
		this.isRelationship = isRelationship;
	}

	public boolean isRelationship() {
		return isRelationship;
	}
}
