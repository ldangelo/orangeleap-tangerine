package com.mpower.controller.validator;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mpower.domain.Person;

public class PersonValidator implements Validator {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	private String pageName;

	/*
	 * This is not implemented yet. Right now we have static validation rules in
	 * place. We need a PageCustomizationService that will allow for doing
	 * dynamic validation
	 */
	/*
	 * private PageCustomizationService pageCustomizationService;
	 *
	 * public PageCustomizationService getPageCustomizationService() { return
	 * pageCustomizationService; }
	 *
	 * public void setPageCustomizationService( PageCustomizationService
	 * pageCustomizationService) { this.pageCustomizationService =
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
		// TODO Auto-generated method stub
		logger.info("**** Validating Person from Save person form");

		Person person = (Person) target;
		@SuppressWarnings("unused")
        Map<String, String> validationsMap = person.getValidationMap();
        @SuppressWarnings("unused")
        Map<String, Boolean> requiredsMap = person.getRequiredFieldMap();
		// get person then get site

		// This is an example of validating that the lastName field in person
		// is not empty, null, or whitespace. If it is, we grab the empty msg
		// from message.properties.
		// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",
		// "empty.lastName");

		// This is a different form of validation, very similar to the one above
		// but if the 3rd arg is not found in our message.properties, we use
		// 4th param as a default message.
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",
				"empty.lastName", "default message");

		// this is for working towards completely dynamic validation.
		// errors.rejectValue(field, errorCode, errorArgs, defaultMessage)
	}

}
