package com.mpower.domain.model;

import java.util.HashMap;
import java.util.Map;

import com.mpower.domain.model.customization.CustomField;

/**
 * Extends AbstractEntity to include methods needed on Entites which
 * can be customized via Custom Fields.
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class AbstractCustomizableEntity extends AbstractEntity {

    protected Map<String, CustomField> customFieldMap = null;


    /**
     * Get the map of custom fields associated with this Entity. The returned
     * Map points to the internal map, so modifying the returned map will chage
     * the internal representation.
     * @return a Map<String,CustomField>
     */
    public Map<String, CustomField> getCustomFieldMap() {
        if (customFieldMap == null) {
            customFieldMap = new HashMap<String, CustomField>();
        }
        return customFieldMap;
    }

    /**
     * Sets the Map of custom fields associated with this entity. Calling
     * this will overwrite the internal Map, so ensure this is called before
     * attempting to set or get any custom field values.
     * @param customFieldMap a Map<String,CustomField> with the custom fields
     *                       for this entity
     */
    public void setCustomFieldMap(Map<String,CustomField> customFieldMap) {
        this.customFieldMap = customFieldMap;
    }

    /**
     * Gets the value of a custom field for this Entity. If there is not value
     * corresponding to the fieldName, this method will return null. Note that
     * the custom field value could containing a single value or a comma-separted
     * list of values, depending on the Entity type and field
     * @param fieldName the custom field name to get the value for
     * @return the CustomField, or null if no custom field exisits with the given name
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
     * If the custom field does not already exisit in the internal map, a new
     * custom field with the given name will be created and all the values will
     * be set.
     * @param fieldName the name of the Custom Field
     * @param value the value of the custom field
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
}
