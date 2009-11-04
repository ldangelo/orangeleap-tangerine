package com.orangeleap.tangerine.domain.rollup;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.domain.GeneratedId;

public class RollupValue implements GeneratedId, Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long rollupAttributeId;
    private String groupByValue;
    private Date startDate;
    private Date endDate;
    private String currencyCode;
    private BigDecimal totalValue;
	
    public RollupValue() { }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public Long getRollupAttributeId() {
        return rollupAttributeId;
    }

    public void setRollupAttributeId(Long rollupAttributeId) {
        this.rollupAttributeId = rollupAttributeId;
    }

    
    public String getGroupByValue() {
        return groupByValue;
    }

    public void setGroupByValue(String groupByValue) {
        this.groupByValue = groupByValue;
    }

    
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    
    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

	
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RollupValue)) {
            return false;
        }
        RollupValue a = (RollupValue) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb
        .append(id, a.getId())
        .append(rollupAttributeId, a.getRollupAttributeId())
        .append(groupByValue, a.getGroupByValue())
        .append(startDate, a.getStartDate())
        .append(endDate, a.getEndDate())
        .append(currencyCode, a.getCurrencyCode())
        .append(totalValue, a.getTotalValue())
		;
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb
        .append(""+id)
        .append(""+rollupAttributeId)
        .append(""+groupByValue)
        .append(""+startDate)
        .append(""+endDate)
        .append(""+currencyCode)
        .append(""+totalValue)
		;
        return hcb.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
        .append(super.toString())
        .append("id", ""+id)
        .append("rollupAttributeId", ""+rollupAttributeId)
        .append("groupByValue", ""+groupByValue)
        .append("startDate", ""+startDate)
        .append("endDate", ""+endDate)
        .append("currencyCode", ""+currencyCode)
        .append("totalValue", ""+totalValue)
        .toString();
    }

}