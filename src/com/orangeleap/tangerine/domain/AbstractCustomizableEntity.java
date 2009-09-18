/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlType;

import org.apache.commons.beanutils.BeanUtils;

import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.util.StringConstants;

/**
 * Extends AbstractEntity to include methods needed on Entities which
 * can be customized via Custom Fields.
 *
 * @version 1.0
 */
@XmlType(namespace = "http://www.orangeleap.com/orangeleap/schemas")
@SuppressWarnings("serial")
public abstract class AbstractCustomizableEntity extends AbstractEntity implements Customizable {

    protected Map<String, CustomField> customFieldMap = null;

    /**
     * Static method to return a CustomFieldMap implementation
     *
     * @return CustomFieldMap
     */
    public static Map<String, CustomField> createCustomFieldMap(final AbstractCustomizableEntity entity) {

        return new TreeMap<String, CustomField>() {
            @Override
            public CustomField get(Object key) {
                CustomField field = super.get(key);
                if (field == null) {
                    field = new CustomField((String) key);
                    field.setEntityId(entity.getId());
                    field.setEntityType(entity.getType());

                    super.put((String) key, field);
                }
                return field;
            }
        };

    }
    
    @Override
    public Set<String> getFullTextSearchKeywords() {
		Set<String> set = new TreeSet<String>();
        set.addAll(super.getFullTextSearchKeywords());
        for (CustomField cf: this.getCustomFieldMap().values()) {
        	String value = cf.getValue();
        	if (value != null) {
        		String[] sa = value.split(StringConstants.CUSTOM_FIELD_SEPARATOR);
        		for (String s: sa) if (s.trim().length() > 0) set.add(s.trim().toLowerCase());
        	}
        }
        return set;
    }


    public void clearCustomFieldMap() {
        getCustomFieldMap().clear();
    }

    /**
     * Get the map of custom fields associated with this Entity. The returned
     * Map points to the internal map, so modifying the returned map will change
     * the internal representation.
     *
     * @return a Map<String,CustomField>
     */
    public Map<String, CustomField> getCustomFieldMap() {
        if (customFieldMap == null) {
            customFieldMap = createCustomFieldMap(this);
        }

        for (String key : customFieldMap.keySet()) {
            CustomField field = customFieldMap.get(key);
            field.setEntityId(this.getId());
            field.setEntityType(this.getType());
        }

        return customFieldMap;
    }

    /**
     * Sets the Map of custom fields associated with this entity. Calling
     * this will overwrite the internal Map, so ensure this is called before
     * attempting to set or get any custom field values.
     *
     * @param customFieldMap a Map<String,CustomField> with the custom fields
     *                       for this entity
     */
    public void setCustomFieldMap(Map<String, CustomField> customFieldMap) {
        this.customFieldMap = customFieldMap;
    }

    /**
     * Gets the value of a custom field for this Entity. If there is not value
     * corresponding to the fieldName, this method will return null. Note that
     * the custom field value could containing a single value or a comma-separated
     * list of values, depending on the Entity type and field
     *
     * @param fieldName the custom field name to get the value for
     * @return the CustomField, or null if no custom field exists with the given name
     */
    public String getCustomFieldValue(String fieldName) {
        CustomField customField = getCustomFieldMap().get(fieldName);
        if (customField == null || customField.getValue() == null) {
            return null;
        }
        return customField.getValue();
    }

    /**
     * Sets the value of the custom field for this entity to the specified value.
     * If the custom field does not already exist in the internal map, a new
     * custom field with the given name will be created and all the values will
     * be set.
     *
     * @param fieldName the name of the Custom Field
     * @param value     the value of the custom field
     */
    public void setCustomFieldValue(String fieldName, String value) {
        CustomField customField = getCustomFieldMap().get(fieldName);
        if (customField == null) {
            customField = new CustomField();
            customField.setName(fieldName);
            customField.setValue(value);
            customField.setEntityId(this.getId());
            customField.setEntityType(this.getType());
            getCustomFieldMap().put(fieldName, customField);

        } else {
            customField.setValue(value);
        }
    }

    public static final String FMT = "MM/dd/yyyy";

    public Date getCustomFieldAsDate(String field) {
		SimpleDateFormat sdf = new SimpleDateFormat(FMT);
		String value = getCustomFieldValue(field);
		try {
			return value == null?null:sdf.parse(value);
		} catch (Exception e) {
			return null;
		}
    }
    
    public void setCustomFieldAsDate(String field, Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat(FMT);
		String value = (d == null)?null:sdf.format(d);
    	setCustomFieldValue(field, value);
    }
    
    public BigDecimal getCustomFieldAsBigDecimal(String field) {
		String value = getCustomFieldValue(field);
		try {
			return value == null?null:new BigDecimal(value);
		} catch (Exception e) {
			return null;
		}
    }
    
    public void setCustomFieldAsBigDecimal(String field, BigDecimal bd) {
		String value = (bd == null)?null:bd.toString();
    	setCustomFieldValue(field, value);
    }
    

    /**
     * Default set the custom field to a specified value if an existing value is null.
     *
     * @param fieldName the name of the Custom Field
     * @param value     the value of the custom field
     */
    public void setDefaultCustomFieldValue(String fieldName, String value) {
        if (getCustomFieldValue(fieldName) == null) {
            setCustomFieldValue(fieldName, value);
        }
    }

    /**
     * Remove the custom field
     *
     * @param fieldName the custom field to be removed
     */
    public void removeCustomField(String fieldName) {
        if (getCustomFieldMap().containsKey(fieldName)) {
            getCustomFieldMap().remove(fieldName);
        }
    }

    /**
     * If the custom field contains the 'value' then remove the value
     *
     * @param fieldName the custom field to check the value for
     * @param value     value to remove
     */
    public void removeCustomFieldValue(String fieldName, String value) {

        String existingValue = getCustomFieldValue(fieldName);

        //
        // if the custom field value does not contain the value
        // we are removing simply return
        if (!existingValue.contains(value)) {
            return;
        }

        //
        // if the existing value is equal to the value we are removing
        // then set the field value to an empty string (remove it)
        if (!existingValue.contains(StringConstants.CUSTOM_FIELD_SEPARATOR)) {
            if (existingValue.compareTo(value) == 0) {
                setCustomFieldValue(fieldName, StringConstants.EMPTY);
            }
        } else {
            //
            // if the existing value is a custom-field-separator separated list of values
            // then find the value we are removing in the string and remove it
            // then reset the field value
            String[] values = existingValue.split(StringConstants.CUSTOM_FIELD_SEPARATOR);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                if (!values[i].equals(value)) {
                    sb.append(values[i]);
                }

                if (i != (values.length - 1)) {
                    sb.append(StringConstants.CUSTOM_FIELD_SEPARATOR);
                }
            }

            setCustomFieldValue(fieldName, sb.toString());
        }
    }

    /**
     * Check if a value already exists for a fieldName.  If so, append to the existing value, else
     * set the value to the specified value
     *
     * @param fieldName - name of the custom field to add
     * @param value     - value of the custom field to set
     */
    public void addCustomFieldValue(String fieldName, String value) {
        String existingValue = getCustomFieldValue(fieldName);
        if (existingValue == null) {
            setCustomFieldValue(fieldName, value);
        } else {
            setCustomFieldValue(fieldName, existingValue + StringConstants.CUSTOM_FIELD_SEPARATOR + value);
        }
    }

    /**
     * Check if a value for a fieldName has the specified value.  If this is a multi-valued custom field,
     * split the values by the custom field separator and check each individually against the specified value
     *
     * @param fieldName - name of the custom field to test
     * @param value     specified value to compare against.
     * @return true or false
     */
    public boolean hasCustomFieldValue(String fieldName, String value) {
        boolean hasValue = false;
        String val = getCustomFieldValue(fieldName);
        if (val != null) {
            if (val.indexOf(StringConstants.CUSTOM_FIELD_SEPARATOR) > -1) {
                String values[] = val.split(StringConstants.CUSTOM_FIELD_SEPARATOR);
                for (String thisValue : values) {
                    if (thisValue.equals(value)) {
                        hasValue = true;
                        break;
                    }
                }
            } else {
                hasValue = val.equals(value);
            }
        }
        return hasValue;
    }

    /**
     * Clones custom fields as well as object.
     *
     * @return AbscractCustomizableEntity
     */
    public AbstractCustomizableEntity createCopy() {
        AbstractCustomizableEntity e2;
        try {
            e2 = (AbstractCustomizableEntity) BeanUtils.cloneBean(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        e2.setId(null);
        e2.setCustomFieldMap(null);
        for (Map.Entry<String, CustomField> me : getCustomFieldMap().entrySet()) {
            CustomField oldcf = me.getValue();
            e2.setCustomFieldValue(oldcf.getName(), oldcf.getValue());
        }
        return e2;
    }


}
