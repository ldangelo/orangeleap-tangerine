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
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mpower.domain.Person;

public class PersonValidator implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private String pageName;

    /*
     * This is not implemented yet. Right now we have static validation rules in place. We need a PageCustomizationService that will allow for doing dynamic validation
     */
    /*
     * private PageCustomizationService pageCustomizationService; public PageCustomizationService getPageCustomizationService() { return pageCustomizationService; } public void setPageCustomizationService( PageCustomizationService pageCustomizationService) { this.pageCustomizationService =
     * pageCustomizationService; }
     */
    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        // TODO Auto-generated method stub
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        // used as a cache to prevent having to use reflection if the value has already been read
        Map<String, Object> fieldValueMap = new HashMap<String, Object>();

        // used to know that a field already has an error, so don't add another
        Set<String> errorSet = new HashSet<String>();

        // validate required fields
        Map<String, Boolean> requiredsMap = person.getRequiredFieldMap();
        if (requiredsMap != null) {
            for (String key : requiredsMap.keySet()) {
                if (errorSet.contains(key)) {
                    continue;
                }
                Object property = fieldValueMap.get(key);
                if (property == null) {
                    BeanWrapper personBeanWrapper = new BeanWrapperImpl(person);
                    property = personBeanWrapper.getPropertyValue(key);
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
        Map<String, String> validationsMap = person.getValidationMap();
        if (validationsMap != null) {
            for (String key : validationsMap.keySet()) {
                if (errorSet.contains(key)) {
                    continue;
                }
                Object property = fieldValueMap.get(key);
                if (property == null) {
                    BeanWrapper personBeanWrapper = new BeanWrapperImpl(person);
                    property = personBeanWrapper.getPropertyValue(key);
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

        // get person then get site

        // This is an example of validating that the lastName field in person
        // is not empty, null, or whitespace. If it is, we grab the empty msg
        // from message.properties.
        // ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",
        // "empty.lastName");

        // This is a different form of validation, very similar to the one above
        // but if the 3rd arg is not found in our message.properties, we use
        // 4th param as a default message.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "empty.lastName", "default message");

        // this is for working towards completely dynamic validation.
        // errors.rejectValue(field, errorCode, errorArgs, defaultMessage)
    }
}
