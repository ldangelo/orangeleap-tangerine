package com.orangeleap.tangerine.controller.validator;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.orangeleap.tangerine.domain.paymentInfo.Pledge;

public class PledgeValidator extends AbstractCommitmentValidator<Pledge> {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

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
        validateStartEndDate(pledge, errors);
        validateReminders(pledge, errors);
    }
}
