package com.orangeleap.tangerine.controller.validator;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKindDetail;

public class GiftInKindDetailsValidator implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return GiftInKind.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.debug("validate:");
        
        GiftInKind gik = (GiftInKind)target;
        BigDecimal total = getTotal(gik.getMutableDetails());
        BigDecimal fairMarketValue = gik.getFairMarketValue();
        if (total == null || fairMarketValue == null || total.compareTo(fairMarketValue) != 0) {
            errors.reject("giftInKindFairMarketValues");
        }
    }
    
    private BigDecimal getTotal(List<GiftInKindDetail> details) {
        BigDecimal total = new BigDecimal(0); 
        for (GiftInKindDetail detail : details) {
            if (detail != null) {
                total = total.add(detail.getFairMarketValue() == null ? new BigDecimal(0) : detail.getFairMarketValue());
            }
        }
        return total;
    }
}
