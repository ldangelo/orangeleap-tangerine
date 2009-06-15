package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.CustomField;

/**
 * @version 1.0
 */
public interface CustomFieldDao {

	public List<CustomField> readCustomFieldsByEntityAndFieldName(Long constituentId, String entityType, String fieldName);
	
	public void maintainCustomFieldsByEntityAndFieldName(Long entityId, String entityType, String fieldName, List<CustomField> list);
	
	public void deleteCustomField(CustomField customField);
	

}
