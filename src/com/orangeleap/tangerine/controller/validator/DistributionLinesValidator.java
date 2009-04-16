package com.orangeleap.tangerine.controller.validator;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;

public class DistributionLinesValidator implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return RecurringGift.class.equals(clazz) || Pledge.class.equals(clazz) || Gift.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.trace("validate:");
        
        BigDecimal total = null;
        BigDecimal amount = null;
        if (target instanceof Gift) {
            Gift gift = (Gift)target;
            total = getTotal(gift.getMutableDistributionLines());
            amount = gift.getAmount();
        }
        else if (target instanceof Pledge) {
            Pledge pledge = (Pledge)target;
            total = getTotal(pledge.getMutableDistributionLines());
            if (pledge.isRecurring()) {
                amount = pledge.getAmountPerGift();
            }
            else {
                amount = pledge.getAmountTotal();
            }
        }
        else if (target instanceof RecurringGift) {
            RecurringGift recurringGift = (RecurringGift)target;
            total = getTotal(recurringGift.getMutableDistributionLines());
            amount = recurringGift.getAmountPerGift();
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
