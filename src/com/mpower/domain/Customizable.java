package com.mpower.domain;

import java.util.Map;

/**
 * Use this class to indicate an entity is customizable
 * @author jball
 */
public interface Customizable {

	public Map<String, CustomField> getCustomFieldMap();

	public Map<String, Boolean> getRequiredFieldMap();

	public void setRequiredFieldMap(Map<String, Boolean> requiredFieldMap);

	public Map<String, String> getValidationMap();

	public void setValidationMap(Map<String, String> validationMap);
}
