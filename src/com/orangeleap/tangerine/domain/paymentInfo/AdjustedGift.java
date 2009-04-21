package com.orangeleap.tangerine.domain.paymentInfo;

import com.orangeleap.tangerine.domain.customization.ExtendableGift;

import java.math.BigDecimal;

/**
 * 
 */
public class AdjustedGift extends ExtendableGift {

    private BigDecimal originalAmount;

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }
}
