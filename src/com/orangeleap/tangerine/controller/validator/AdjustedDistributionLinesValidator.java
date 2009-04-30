package com.orangeleap.tangerine.controller.validator;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.service.GiftService;

public class AdjustedDistributionLinesValidator extends DistributionLinesValidator {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private GiftService giftService;
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return AdjustedGift.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.trace("validate:");
        
        BigDecimal total = null;
        BigDecimal adjustedAmount = null;
        AdjustedGift adjustedGift = (AdjustedGift)target;
        BigDecimal originalAmount = adjustedGift.getOriginalAmount();
        total = getTotal(adjustedGift.getMutableDistributionLines());
        adjustedAmount = adjustedGift.getAdjustedAmount();
        if (total == null || adjustedAmount == null || adjustedAmount.compareTo(adjustedAmount) != 0) {
            errors.reject("errorDistributionLineAmounts");
        }
        if (total != null && total.compareTo(originalAmount) == 1) {
            errors.reject("errorAdjustedDistributionLinesAmounts");
        }
    }
}
