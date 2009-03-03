package com.mpower.controller.validator;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mpower.domain.model.paymentInfo.Commitment;
import com.mpower.domain.model.paymentInfo.DistributionLine;
import com.mpower.domain.model.paymentInfo.Gift;

public class DistributionLinesValidator implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return Commitment.class.equals(clazz) || Gift.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.debug("validate:");
        
        BigDecimal total = null;
        BigDecimal amount = null;
        if (target instanceof Gift) {
            Gift gift = (Gift)target;
            total = getTotal(gift.getDistributionLines());
            amount = gift.getAmount();
        }
        else if (target instanceof Commitment) {
            Commitment commitment = (Commitment)target;
            total = getTotal(commitment.getDistributionLines());
            if (commitment.isRecurring()) {
                amount = commitment.getAmountPerGift();
            }
            else {
                amount = commitment.getAmountTotal();
            }
        }
        if (total == null || amount == null || total.compareTo(amount) != 0) {
            errors.reject("distributionLineAmounts");
        }
    }
    
    private BigDecimal getTotal(List<DistributionLine> lines) {
        BigDecimal total = new BigDecimal(0); 
        for (DistributionLine line : lines) {
            if (line != null) {
                total = total.add(line.getAmount() == null ? new BigDecimal(0) : line.getAmount());
            }
        }
        return total;
    }
}
