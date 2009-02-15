package com.mpower.web.customization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
    public static final String OTHER_PREFIX = "other_";
    private static final String DOT_VALUE = ".value";

    private Object model;
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

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public List<String> getCodes() {
        return codes;
    }

    public List<String> getAugmentedCodes() {
    	Object o = getFieldValue();
    	if (o == null) {
            o = "";
        } 
    	String value = StringUtils.trimToEmpty(o.toString());
    	if (!codes.contains(value) && value.length() > 0) {
    		// This picklist item's previously saved value has been deleted from the list of available picklist values.  
    		// Add it back in as a temporary option so that it doesn't get reset when saving some other change from the gui.  
    		// We no longer have a display value, so use the code value for the display value in just this case.
    		codes.add(""+getFieldValue());
    		displayValues.add(""+getFieldValue());
    	}
        return codes;
    }

    public String getFieldName() {
        return fieldName;
    }
    
    private String escapeChars(String str) {
        return str == null ? "" : str.replace('.', '_').replace('[', '-').replace(']', '-');
    }

    public String getFieldId() {
        return escapeChars(fieldName);
    }
    
    public String getOtherFieldId() {
        return escapeChars(getOtherFieldName());
    }
    
    /**
     * Get the 'other' field name, i.e, customFieldMap[reference] --> customFieldMap[other_reference], motivationCode --> other_motivationCode, customFieldMap[individual.spouse] --> customFieldMap[individual.other_spouse]
     * @param fieldName
     * @return
     */
    public String getOtherFieldName() {
        return getOtherFieldName(fieldName);
    }
    
    public static String getOtherFieldName(String aFieldName) {
        String otherFieldName = null;
        
        boolean endsInValue = false;
        if (aFieldName.endsWith(DOT_VALUE)) {
            aFieldName = aFieldName.substring(0, aFieldName.length() - DOT_VALUE.length());
            endsInValue = true;
        }
        
        int startBracketIndex = aFieldName.indexOf('[');
        if (startBracketIndex > -1) {
            int periodIndex = aFieldName.indexOf('.', startBracketIndex);
            if (periodIndex > -1) {
                otherFieldName = new StringBuilder(aFieldName.substring(0, periodIndex + 1)).append(OTHER_PREFIX).append(aFieldName.substring(periodIndex + 1, aFieldName.length())).toString(); 
            }
            else {
                otherFieldName = new StringBuilder(aFieldName.substring(0, startBracketIndex + 1)).append(OTHER_PREFIX).append(aFieldName.substring(startBracketIndex + 1, aFieldName.length())).toString(); 
            }
        }
        if (otherFieldName == null) {
            otherFieldName = new StringBuilder(OTHER_PREFIX).append(aFieldName).toString();
        }
        if (endsInValue) {
            otherFieldName = otherFieldName.concat(DOT_VALUE);
        }
        return otherFieldName;      
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
    
    @SuppressWarnings("unchecked")
    public String getUniqueReferenceValues() {
        if (referenceValues == null) {
            return "";
        }
        Set<String> s = new TreeSet<String>();
        for (int i = 0; i < referenceValues.size(); i++) {
            if (referenceValues.get(i) != null) {
                s.addAll(org.springframework.util.StringUtils.commaDelimitedListToSet(referenceValues.get(i)));
            }
        } 
        return org.springframework.util.StringUtils.collectionToCommaDelimitedString(s);
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

    public boolean isMultiLine() {
    	return ("" + getDisplayValue()).contains("\n");
    }
    
    public String[] getSplitLineValues() {
    	String s = "" + getDisplayValue();
        return s.split("\\n");
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
