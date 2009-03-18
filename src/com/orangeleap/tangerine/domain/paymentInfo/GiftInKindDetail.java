package com.orangeleap.tangerine.domain.paymentInfo;

import java.math.BigDecimal;

import org.springframework.core.style.ToStringCreator;
import org.springframework.util.StringUtils;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;

public class GiftInKindDetail extends AbstractCustomizableEntity {
    private static final long serialVersionUID = 1L;

    private String description;
    private BigDecimal detailFairMarketValue;
    private String fmvMethod;
    private String gikCategory;
    private Integer quantity;
    private boolean taxDeductible = false;
    private Long giftInKindId;

    public GiftInKindDetail() {
        super();
    }

    public GiftInKindDetail(String description, BigDecimal fairMarketValue, String fmvMethod, String gikCategory, Integer quantity, boolean taxDeductible, Long giftInKindId) {
        super();
        this.description = description;
        this.detailFairMarketValue = fairMarketValue;
        this.fmvMethod = fmvMethod;
        this.gikCategory = gikCategory;
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
    
    public BigDecimal getDetailFairMarketValue() {
        return detailFairMarketValue;
    }
    
    public void setDetailFairMarketValue(BigDecimal fairMarketValue) {
        this.detailFairMarketValue = fairMarketValue;
    }
    
    public String getFmvMethod() {
        return fmvMethod;
    }
    
    public void setFmvMethod(String fmvMethod) {
        this.fmvMethod = fmvMethod;
    }
    
    public String getGikCategory() {
        return gikCategory;
    }

    public void setGikCategory(String gikCategory) {
        this.gikCategory = gikCategory;
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

    public boolean isFieldEntered() {
        return detailFairMarketValue != null || quantity != null || StringUtils.hasText(description) || StringUtils.hasText(fmvMethod);
    }

    public boolean isValid() {
        boolean valid = false;
        if (detailFairMarketValue != null) {
            valid = true;
        }
        return valid;
    }
    
    @Override
    public String toString() {
        return new ToStringCreator(this).append(super.toString()).append("description", description).append("detailFairMarketValue", detailFairMarketValue).
            append("fmvMethod", fmvMethod).append("gikCategory", gikCategory).append("quantity", quantity).append("taxDeductible", taxDeductible).append("giftInKindId", giftInKindId).toString();
    }
}
