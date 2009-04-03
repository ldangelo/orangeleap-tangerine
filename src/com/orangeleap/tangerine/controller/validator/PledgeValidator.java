package com.orangeleap.tangerine.controller.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.orangeleap.tangerine.domain.paymentInfo.Pledge;

public class PledgeValidator implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return Pledge.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Pledge pledge = (Pledge) target;
        
        if (pledge.isRecurring()) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "invalidStartDate", "Start Date is required");
        }
        
        if (pledge.getEndDate() != null) {
            if (pledge.getEndDate().before(pledge.getStartDate())) {
                errors.rejectValue("endDate", "invalidEndDate", "Start date must be before end date");
            }
        }
    }
}
