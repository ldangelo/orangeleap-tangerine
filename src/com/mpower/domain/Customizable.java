package com.mpower.domain;

import java.util.Map;

/**
 * Use this class to indicate an entity is customizable
 * @author jball
 */
@Deprecated
public interface Customizable {

    public Map<String, CustomField> getCustomFieldMap();
}
