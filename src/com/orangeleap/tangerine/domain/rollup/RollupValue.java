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
    private Long rollupSeriesId;
    private Long rollupAttributeId;
    private String groupByValue;
    private Date startDate;
    private Date endDate;
    private String currencyCode;
    private BigDecimal countValue;
    private BigDecimal sumValue;
    private String siteName;
	
    public RollupValue() { }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public Long getRollupSeriesId() {
        return rollupSeriesId;
    }

    public void setRollupSeriesId(Long rollupSeriesId) {
        this.rollupSeriesId = rollupSeriesId;
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

    
    public BigDecimal getCountValue() {
        return countValue;
    }

    public void setCountValue(BigDecimal countValue) {
        this.countValue = countValue;
    }

    public BigDecimal getSumValue() {
        return sumValue;
    }

    public void setSumValue(BigDecimal sumValue) {
        this.sumValue = sumValue;
    }
    
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteName() {
		return siteName;
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
        .append(rollupSeriesId, a.getRollupSeriesId())
        .append(rollupAttributeId, a.getRollupAttributeId())
        .append(groupByValue, a.getGroupByValue())
        .append(startDate, a.getStartDate())
        .append(endDate, a.getEndDate())
        .append(currencyCode, a.getCurrencyCode())
        .append(countValue, a.getCountValue())
        .append(sumValue, a.getSumValue())
        .append(siteName, a.getSiteName())
		;
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb
        .append(""+id)
        .append(""+rollupSeriesId)
        .append(""+rollupAttributeId)
        .append(""+groupByValue)
        .append(""+startDate)
        .append(""+endDate)
        .append(""+currencyCode)
        .append(""+countValue)
        .append(""+sumValue)
        .append(""+siteName)
		;
        return hcb.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
        .append(super.toString())
        .append("id", ""+id)
        .append("rollupSeriesId", ""+rollupSeriesId)
        .append("rollupAttributeId", ""+rollupAttributeId)
        .append("groupByValue", ""+groupByValue)
        .append("startDate", ""+startDate)
        .append("endDate", ""+endDate)
        .append("currencyCode", ""+currencyCode)
        .append("countValue", ""+countValue)
        .append("sumValue", ""+sumValue)
        .append("siteName", ""+siteName)
        .toString();
    }

}