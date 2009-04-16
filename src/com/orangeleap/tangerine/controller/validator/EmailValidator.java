package com.orangeleap.tangerine.controller.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.orangeleap.tangerine.domain.EmailAware;
import com.orangeleap.tangerine.domain.communication.AbstractCommunicatorEntity;
import com.orangeleap.tangerine.domain.communication.Email;

public class EmailValidator extends AbstractCommunicationValidator<Email> {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return Email.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.trace("in EmailValidator");
        validateEMail(target, errors);
    }
    
    public void validateEMail(Object target, Errors errors) {
    	Email email = null;
    	String inPath = errors.getNestedPath();
    	if (target instanceof Email) {
    		email = (Email) target;
    	} 
    	else if (target instanceof EmailAware) {
    		email = ((EmailAware) target).getEmail();
    		errors.setNestedPath("email");
    	}
        else if (target instanceof AbstractCommunicatorEntity) {
            email = ((AbstractCommunicatorEntity) target).getPrimaryEmail();
            errors.setNestedPath("primaryEmail");
        }

    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress", "invalidEmailAddress", "Email Address is required");
    	validateDates(email, errors);
    	errors.setNestedPath(inPath);
    }
}
