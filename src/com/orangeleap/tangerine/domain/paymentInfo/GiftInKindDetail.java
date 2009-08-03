/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.domain.paymentInfo;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import org.springframework.core.style.ToStringCreator;
import org.springframework.util.StringUtils;

import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public class GiftInKindDetail extends AbstractCustomizableEntity {
    private static final long serialVersionUID = 1L;

    private BigDecimal detailFairMarketValue;
    private String description;
    private String projectCode;
    private boolean taxDeductible = false;
    private Long giftInKindId;

    private String fmvMethod;
    private String gikCategory;
    private Integer quantity;

    public GiftInKindDetail() {
        super();
    }

    public GiftInKindDetail(BigDecimal fairMarketValue, String description, String projectCode, boolean taxDeductible,
                            Long giftInKindId, String fmvMethod, String gikCategory, Integer quantity) {
        super();
        this.description = description;
        this.detailFairMarketValue = fairMarketValue;
        this.projectCode = projectCode;
        this.fmvMethod = fmvMethod;
        this.gikCategory = gikCategory;
        this.quantity = quantity;
        this.taxDeductible = taxDeductible;
        this.giftInKindId = giftInKindId;
    }

	public GiftInKindDetail(Long id) {
		this();
		this.id = id;
	}

	public BigDecimal getDetailFairMarketValue() {
        return detailFairMarketValue;
    }
    
    public void setDetailFairMarketValue(BigDecimal fairMarketValue) {
        this.detailFairMarketValue = fairMarketValue;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
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

    public boolean isFieldEntered() {
        return detailFairMarketValue != null || StringUtils.hasText(description) || StringUtils.hasText(projectCode);
    }

    public boolean isValid() {
        boolean valid = false;
        if (detailFairMarketValue != null && StringUtils.hasText(description)) {
            valid = true;
        }
        return valid;
    }
    
    @Override
    public String toString() {
        return new ToStringCreator(this).append(super.toString()).append("detailFairMarketValue", detailFairMarketValue).append("description", description).append("projectCode", projectCode).
            append("taxDeductible", taxDeductible).append("giftInKindId", giftInKindId).append("fmvMethod", fmvMethod).append("gikCategory", gikCategory).append("quantity", quantity).toString();
    }
}
