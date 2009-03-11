package com.orangeleap.tangerine.dao;

import com.orangeleap.tangerine.domain.customization.CustomField;

import java.util.Map;

/**
 * @version 1.0
 */
public interface CustomFieldDao {

    public Map<String, CustomField> readCustomFields(Long entityId, String entityType);

    public void maintainCustomFields(Map<String,CustomField> customFields);


}
