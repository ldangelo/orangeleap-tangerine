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

import com.mpower.domain.CustomField;
import com.mpower.domain.Gift;
import com.mpower.domain.Person;
import com.mpower.domain.Viewable;

public class EntityValidator implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return Person.class.equals(clazz) || Gift.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Viewable viewable = (Viewable) target;
        Set<String> requiredFields = viewable.getRequiredFields();
        Map<String, String> fieldLabelMap = viewable.getFieldLabelMap();
        Map<String, String> validationMap = viewable.getValidationMap();

        // used as a cache to prevent having to use reflection if the value has already been read
        Map<String, Object> fieldValueMap = new HashMap<String, Object>();

        // used to know that a field already has an error, so don't add another
        Set<String> errorSet = new HashSet<String>();

        // validate required fields
        if (requiredFields != null) {
            for (String key : requiredFields) {
                if (errorSet.contains(key)) {
                    continue;
                }
                Object property = fieldValueMap.get(key);
                if (property == null) {
                    BeanWrapper beanWrapper = new BeanWrapperImpl(viewable);
                    property = beanWrapper.getPropertyValue(key);
                    if (property instanceof CustomField) {
                        property = ((CustomField) property).getValue();
                    }
                    fieldValueMap.put(key, property);
                }
                String propertyString = property == null ? "" : property.toString();
                if (StringUtils.isEmpty(propertyString) && !errorSet.contains(key)) {
                    errors.rejectValue(key, "fieldRequiredFailure", new String[] { fieldLabelMap.get(key) }, "no message provided for the validation error: fieldRequiredFailure");
                    errorSet.add(key);
                }
            }
        }

        // validate regex
        if (validationMap != null) {
            for (String key : validationMap.keySet()) {
                if (errorSet.contains(key)) {
                    continue;
                }
                Object property = fieldValueMap.get(key);
                if (property == null) {
                    BeanWrapper beanWrapper = new BeanWrapperImpl(viewable);
                    property = beanWrapper.getPropertyValue(key);
                    if (property instanceof CustomField) {
                        property = ((CustomField) property).getValue();
                    }
                    fieldValueMap.put(key, property);
                }
                String propertyString = property == null ? "" : property.toString();
                String regex = validationMap.get(key);
                boolean matches = propertyString.matches(regex);
                if (!matches && !errorSet.contains(key)) {
                    // String defaultMessage = messageService.lookupMessage(SessionServiceImpl., MessageResourceType.FIELD_VALIDATION, "fieldValidationFailure", null);
                    errors.rejectValue(key, "fieldValidationFailure", new String[] { fieldLabelMap.get(key), propertyString }, "no message provided for the validation error: fieldValidationFailure");
                    // errors.reject("fieldValidationFailure", new String[] { key, propertyString }, "no message provided for the validation error: fieldValidationFailure");
                }
            }
        }
    }
}
