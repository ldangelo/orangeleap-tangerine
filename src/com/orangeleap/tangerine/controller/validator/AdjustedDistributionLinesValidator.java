package com.orangeleap.tangerine.controller.validator;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.service.AdjustedGiftService;

public class AdjustedDistributionLinesValidator extends DistributionLinesValidator {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private AdjustedGiftService adjustedGiftService;
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return AdjustedGift.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.trace("validate:");
        
        AdjustedGift adjustedGift = (AdjustedGift)target;
        
        checkTotaledDistributionLineAmountMatch(adjustedGift, errors);
        checkAmountsNotPositive(adjustedGift, errors);
        checkTotalAdjustedAmount(adjustedGift, errors);
    }
    
    public void checkTotaledDistributionLineAmountMatch(AdjustedGift adjustedGift, Errors errors) {
        BigDecimal total = getTotal(adjustedGift.getDistributionLines());
        BigDecimal adjustedAmount = adjustedGift.getAdjustedAmount();
        if (total == null || adjustedAmount == null || adjustedAmount.compareTo(total) != 0) {
            errors.reject("errorDistributionLineAmounts");
        }
    }
    
    public void checkAmountsNotPositive(AdjustedGift adjustedGift, Errors errors) {
        if (adjustedGift.getAdjustedAmount() != null && adjustedGift.getAdjustedAmount().compareTo(BigDecimal.ZERO) >= 0) {
            errors.rejectValue("adjustedAmount", "errorAdjustedAmountPositive");
        }
        for (DistributionLine aLine : adjustedGift.getDistributionLines()) {
            if (aLine != null) {
                if (aLine.getAmount() != null && aLine.getAmount().compareTo(BigDecimal.ZERO) == 1) {
                    errors.reject("errorIndividualAdjustedDistributionLineAmountPositive");
                    break;
                }
            }
        }
    }
    
    public void checkTotalAdjustedAmount(AdjustedGift adjustedGift, Errors errors) {
        BigDecimal existingAmount = adjustedGiftService.findCurrentTotalAdjustedAmount(adjustedGift.getOriginalGiftId());
        BigDecimal totalAmount = existingAmount.add(adjustedGift.getAdjustedAmount() == null ? BigDecimal.ZERO : adjustedGift.getAdjustedAmount());
        BigDecimal originalAmount = adjustedGift.getOriginalAmount();
        
        BigDecimal leftoverAmount = originalAmount.add(totalAmount);
        if (leftoverAmount.compareTo(BigDecimal.ZERO) == -1) {
            errors.rejectValue("adjustedAmount", "errorTotalAdjustedAmount", new String[] { leftoverAmount.toString() }, null);
        }
    }

    /** Used only in unit tests */
    public void setAdjustedGiftService(AdjustedGiftService adjustedGiftService) {
        this.adjustedGiftService = adjustedGiftService;
    }
}
