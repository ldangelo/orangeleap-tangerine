package com.orangeleap.tangerine.controller.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;

public class RecurringGiftValidator extends AbstractCommitmentValidator<RecurringGift> {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return RecurringGift.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RecurringGift recurringGift = (RecurringGift) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "amountPerGift", "invalidAmountPerGift", "Amount per gift is required");
        
        validateStartEndDate(recurringGift, errors);
        validateReminders(recurringGift, errors);
    }
}
