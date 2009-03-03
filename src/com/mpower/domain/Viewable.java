package com.mpower.domain;

import java.util.Map;

import com.mpower.domain.customization.FieldDefinition;

/**
 * Use this class to indicate an entity can be displayed on a page
 * @author jball
 */
@Deprecated
public interface Viewable {

    public Map<String, String> getFieldLabelMap();

    public void setFieldLabelMap(Map<String, String> fieldLabelMap);

    public Map<String, Object> getFieldValueMap();

    public void setFieldValueMap(Map<String, Object> fieldValueMap);

    public Map<String, FieldDefinition> getFieldTypeMap();

    public void setFieldTypeMap(Map<String, FieldDefinition> fieldTypeMap);

    public Long getId();

    public Person getPerson();
}
