package com.orangeleap.tangerine.controller.validator;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.orangeleap.tangerine.domain.NewPhoneAware;
import com.orangeleap.tangerine.domain.communication.AbstractCommunicatorEntity;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.util.StringConstants;

public class PhoneValidator extends AbstractCommunicationValidator<Phone> {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return Phone.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.trace("in PhoneValidator");
        validatePhone(target, errors);
    }

    public void validatePhone(Object target, Errors errors) {
        Phone phone = null;
        String inPath = errors.getNestedPath();
        if (target instanceof Phone) {
            phone = (Phone) target;
        } 
        else if (target instanceof NewPhoneAware) {
            phone = ((NewPhoneAware) target).getPhone();
            errors.setNestedPath("phone");
        }
        else if (target instanceof AbstractCommunicatorEntity) {
            phone = ((AbstractCommunicatorEntity) target).getPrimaryPhone();
            errors.setNestedPath("primaryPhone");
        }

        if (StringUtils.hasText(phone.getCustomFieldValue(StringConstants.PHONE_TYPE)) == false) {
            errors.rejectValue(StringConstants.CUSTOM_FIELD_MAP_START + StringConstants.PHONE_TYPE + StringConstants.CUSTOM_FIELD_MAP_END, "errorPhoneTypeRequired");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "number", "invalidNumber", "Phone number is required");
        validateDates(phone, errors);
        errors.setNestedPath(inPath);
    }
}
