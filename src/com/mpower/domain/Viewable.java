package com.mpower.domain;

import java.util.Map;

/**
 * Use this class to indicate an entity is customizable
 * @author jball
 */
public interface Viewable {

    public Map<String, String> getFieldLabelMap();

    public void setFieldLabelMap(Map<String, String> fieldLabelMap);

    public Map<String, Object> getFieldValueMap();

    public void setFieldValueMap(Map<String, Object> fieldValueMap);

    public Long getId();

    public Site getSite();

    public Person getPerson();
}
