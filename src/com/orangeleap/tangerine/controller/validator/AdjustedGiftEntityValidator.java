package com.orangeleap.tangerine.controller.validator;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.validation.Errors;

import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.type.FormBeanType;

/**
 * This class is a bit of hack to get EntityValidator to validate paymentSource, address, and phone only when adjustedPaymentRequired is true.
 * 
 * TODO: The real solution is to refactor EntityValidator and decouple the validation of those types. 
 */
public class AdjustedGiftEntityValidator extends EntityValidator {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());
    
    @Override
    protected void validateSubEntities(Object target, Errors errors) {
        if (logger.isTraceEnabled()) {
            logger.trace("validateSubEntities:");
        }
        boolean validateSubEntities = true;
        AdjustedGift adjustedGift = (AdjustedGift)target;
        if (adjustedGift.isAdjustedPaymentRequired() == false) {
            validateSubEntities = false;
        }

        if (validateSubEntities) {
            if (FormBeanType.NEW.equals(adjustedGift.getPaymentSourceType())) {
                paymentSourceValidator.validatePaymentSource(adjustedGift, errors);
            }
            else if (FormBeanType.EXISTING.equals(adjustedGift.getPaymentSourceType())) {
                // TODO: validate ID > 0
            }
            
            if (FormBeanType.NEW.equals(adjustedGift.getAddressType())) {
                addressValidator.validateAddress(adjustedGift, errors);
            }
            else if (FormBeanType.EXISTING.equals(adjustedGift.getAddressType())) {
                // TODO: validate ID > 0
            }

            if (FormBeanType.NEW.equals(adjustedGift.getPhoneType())) {
                phoneValidator.validatePhone(adjustedGift, errors);
            }
            else if (FormBeanType.EXISTING.equals(adjustedGift.getPhoneType())) {
                // TODO: validate ID > 0
            }
        }
    }
}
