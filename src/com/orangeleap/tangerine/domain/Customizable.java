package com.orangeleap.tangerine.domain;

import java.util.Map;

import com.orangeleap.tangerine.domain.customization.CustomField;

public interface Customizable extends Entity {

    public String getCustomFieldValue(String fieldName);

    public void setCustomFieldValue(String fieldName, String value);

    public void removeCustomField(String fieldName);

    public Map<String, CustomField> getCustomFieldMap();

}
