package com.orangeleap.tangerine.controller.validator;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.validator.GenericValidator;

public class ExtendedValidationSupport {
	
    protected final Log logger = OLLogger.getLog(getClass());

	protected static Object validator = new GenericValidator();

	/* 
	 * Add ability to use for example in place of regex:  'extensions:isEmail'
	 * INSERT INTO FIELD_VALIDATION (SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, VALIDATION_REGEX) VALUES ('company1', 'constituent.contactInfo', 'constituent.emailMap[home]', 'email.emailAddress', 'extensions:isEmail');
	 * 
	 * @param value - string to validate
	 * @param expression - isCreditCard/isEmail/isUrl
	 */
	public boolean validate(String value, String expression) {
    	String methodname = expression;
    	Method[] methods = validator.getClass().getDeclaredMethods();
    	for (Method method: methods) {
    		if (method.getName().equals(methodname)) {
    			try {
    			   return ((Boolean)method.invoke(validator, new Object[]{value})).booleanValue();
    			} catch (Exception e) {
    			   logger.error(e.getCause());
    			   return false;
    			}
    		}
    	}
		return false;
	}
	
}
