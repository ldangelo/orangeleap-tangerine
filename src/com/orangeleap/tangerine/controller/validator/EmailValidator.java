package com.orangeleap.tangerine.controller.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.orangeleap.tangerine.domain.EmailAware;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.type.ActivationType;

public class EmailValidator implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return Email.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.debug("in EmailValidator");
        validateEMail(target, errors);
    }
    
    public static void validateEMail(Object target, Errors errors) {

    	Email email = null;
    	String inPath = errors.getNestedPath();
    	if (target instanceof Email) {
    		email = (Email) target;
    	} else if (target instanceof EmailAware) {
    		email = ((EmailAware) target).getEmail();
    		errors.setNestedPath("email");
    	}

    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress", "invalidEmailAddress", "Email Address is required");
		if (ActivationType.seasonal.equals(email.getActivationStatus())) {
			if (email.getSeasonalStartDate() == null) {
				errors.rejectValue("seasonalStartDate", "invalidSeasonalStartDate", "Seasonal Start Date is required");
			}
			if (email.getSeasonalEndDate() == null) {
				errors.rejectValue("seasonalEndDate", "invalidSeasonalEndDate", "Seasonal End Date is required");
			}
			if (email.getSeasonalStartDate() != null && email.getSeasonalEndDate() != null) {
				if (!email.getSeasonalEndDate().after(email.getSeasonalStartDate())) {
					errors.rejectValue("seasonalEndDate", "invalidSeasonalEndDateBeforeStartDate", "Seasonal End Date must be after Seasonal Start Date");
				}
			}
		} else if (ActivationType.temporary.equals(email.getActivationStatus())) {
			if (email.getTemporaryStartDate() == null) {
				errors.rejectValue("temporaryStartDate", "invalidTemporaryStartDate", "Temporary Start Date is required");
			}
			if (email.getTemporaryEndDate() == null) {
				errors.rejectValue("temporaryEndDate", "invalidTemporaryEndDate", "Temporary End Date is required");
			}
		}
    	errors.setNestedPath(inPath);
    }
}
