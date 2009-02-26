package com.mpower.dao.interfaces;

import com.mpower.domain.model.customization.CustomField;

import java.util.Map;

/**
 * @version 1.0
 */
public interface CustomFieldDao {

    public Map<String, CustomField> readCustomFields(Long entityId, String entityType);

    public void maintainCustomFields(Map<String,CustomField> customFields);


}
