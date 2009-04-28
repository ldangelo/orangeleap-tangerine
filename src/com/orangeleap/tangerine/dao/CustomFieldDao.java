package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.CustomField;

/**
 * @version 1.0
 */
public interface CustomFieldDao {

	public List<CustomField> readCustomFieldsByConstituentAndFieldName(Long personId, String fieldName);
	
	public void maintainCustomFieldsByConstituentAndFieldName(Long personId, String fieldName, List<CustomField> list);
	
	public void deleteCustomField(CustomField customField);

}
