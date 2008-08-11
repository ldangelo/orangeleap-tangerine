package com.mpower.controller.validator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mpower.domain.Customizable;
import com.mpower.domain.Gift;
import com.mpower.domain.Person;

public class EntityValidator implements Validator {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	protected String pageName;

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz) {
		return Person.class.equals(clazz) || Gift.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Customizable customizableEntity = (Customizable) target;

		// used as a cache to prevent having to use reflection if the value has already been read
		Map<String, Object> fieldValueMap = new HashMap<String, Object>();

		// used to know that a field already has an error, so don't add another
		Set<String> errorSet = new HashSet<String>();

		// validate required fields
		Map<String, Boolean> requiredsMap = customizableEntity.getRequiredFieldMap();
		if (requiredsMap != null) {
			for (String key : requiredsMap.keySet()) {
				if (errorSet.contains(key)) {
					continue;
				}
				Object property = fieldValueMap.get(key);
				if (property == null) {
					BeanWrapper beanWrapper = new BeanWrapperImpl(customizableEntity);
					property = beanWrapper.getPropertyValue(key);
					fieldValueMap.put(key, property);
				}
				if (property != null) {
					String propertyString = property.toString();
					boolean bool = requiredsMap.get(key);
					if (bool && StringUtils.isEmpty(propertyString) && !errorSet.contains(key)) {
						errors.rejectValue(key, "fieldRequiredFailure", new String[] { key, propertyString }, "no message provided for the validation error: fieldRequiredFailure");
						errorSet.add(key);
					}
				}
			}
		}

		// validate regex
		Map<String, String> validationsMap = customizableEntity.getValidationMap();
		if (validationsMap != null) {
			for (String key : validationsMap.keySet()) {
				if (errorSet.contains(key)) {
					continue;
				}
				Object property = fieldValueMap.get(key);
				if (property == null) {
					BeanWrapper beanWrapper = new BeanWrapperImpl(customizableEntity);
					property = beanWrapper.getPropertyValue(key);
					fieldValueMap.put(key, property);
				}
				if (property != null) {
					String propertyString = property.toString();
					String regex = validationsMap.get(key);
					boolean matches = propertyString.matches(regex);
					if (!matches && !errorSet.contains(key)) {
						// String defaultMessage = messageService.lookupMessage(SessionServiceImpl., MessageResourceType.FIELD_VALIDATION, "fieldValidationFailure", null);
						errors.rejectValue(key, "fieldValidationFailure", new String[] { key, propertyString }, "no message provided for the validation error: fieldValidationFailure");
						// errors.reject("fieldValidationFailure", new String[] { key, propertyString }, "no message provided for the validation error: fieldValidationFailure");
					}
				}
			}
		}
	}
}
