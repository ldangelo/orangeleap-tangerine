package com.orangeleap.tangerine.domain.paymentInfo;

import java.math.BigDecimal;

import org.springframework.core.style.ToStringCreator;
import org.springframework.util.StringUtils;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;

public class GiftInKindDetail extends AbstractCustomizableEntity {
    private static final long serialVersionUID = 1L;

    private String description;
    private BigDecimal fairMarketValue;
    private String fmvMethod;
    private Integer quantity;
    private boolean taxDeductible = false;
    private Long giftInKindId;

    public GiftInKindDetail() {
        super();
    }

    public GiftInKindDetail(String description, BigDecimal fairMarketValue, String fmvMethod, Integer quantity, boolean taxDeductible, Long giftInKindId) {
        super();
        this.description = description;
        this.fairMarketValue = fairMarketValue;
        this.fmvMethod = fmvMethod;
        this.quantity = quantity;
        this.taxDeductible = taxDeductible;
        this.giftInKindId = giftInKindId;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal getFairMarketValue() {
        return fairMarketValue;
    }
    
    public void setFairMarketValue(BigDecimal fairMarketValue) {
        this.fairMarketValue = fairMarketValue;
    }
    
    public String getFmvMethod() {
        return fmvMethod;
    }
    
    public void setFmvMethod(String fmvMethod) {
        this.fmvMethod = fmvMethod;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public boolean isTaxDeductible() {
        return taxDeductible;
    }
    
    public void setTaxDeductible(boolean taxDeductible) {
        this.taxDeductible = taxDeductible;
    }
    
    public Long getGiftInKindId() {
        return giftInKindId;
    }
    
    public void setGiftInKindId(Long giftInKindId) {
        this.giftInKindId = giftInKindId;
    }
    
    @Override
    public String toString() {
        return new ToStringCreator(this).append(super.toString()).append("description", description).append("fairMarketValue", fairMarketValue).
            append("fmvMethod", fmvMethod).append("quantity", quantity).append("taxDeductible", taxDeductible).append("giftInKindId", giftInKindId).toString();
    }

    public boolean isFieldEntered() {
        return fairMarketValue != null || quantity != null || StringUtils.hasText(description) || StringUtils.hasText(fmvMethod);
    }

    public boolean isValid() {
        boolean valid = false;
        if (fairMarketValue != null) {
            valid = true;
        }
        return valid;
    }
}
