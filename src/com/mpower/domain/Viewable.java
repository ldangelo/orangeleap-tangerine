package com.mpower.domain;

import java.util.Map;
import java.util.Set;

/**
 * Use this class to indicate an entity is customizable
 * @author jball
 */
public interface Viewable {

    public Set<String> getRequiredFields();

    public void setRequiredFields(Set<String> requiredFields);

    public Map<String, String> getValidationMap();

    public void setValidationMap(Map<String, String> validationMap);

    public Map<String, String> getFieldLabelMap();

    public void setFieldLabelMap(Map<String, String> fieldLabelMap);

    public Map<String, Object> getFieldValueMap();

    public void setFieldValueMap(Map<String, Object> fieldValueMap);

    public Long getId();
}
