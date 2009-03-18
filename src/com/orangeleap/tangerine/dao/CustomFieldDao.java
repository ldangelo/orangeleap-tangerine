package com.orangeleap.tangerine.dao;

import java.util.Map;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.customization.CustomField;

/**
 * @version 1.0
 */
public interface CustomFieldDao {

    public Map<String, CustomField> readCustomFields(AbstractCustomizableEntity entity);

    public void maintainCustomFields(Map<String,CustomField> customFields);

}
