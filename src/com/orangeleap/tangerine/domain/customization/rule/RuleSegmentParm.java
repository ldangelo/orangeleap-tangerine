package com.orangeleap.tangerine.domain.customization.rule;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.domain.GeneratedId;

public class RuleSegmentParm implements GeneratedId, Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long ruleSegmentId;
    private Long ruleSegmentParmSeq;
    private String ruleSegmentParmStringValue;
    private BigDecimal ruleSegmentParmNumericValue;
    private Date ruleSegmentParmDateValue;
	
    public RuleSegmentParm() { }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public Long getRuleSegmentId() {
        return ruleSegmentId;
    }

    public void setRuleSegmentId(Long ruleSegmentId) {
        this.ruleSegmentId = ruleSegmentId;
    }

    
    public Long getRuleSegmentParmSeq() {
        return ruleSegmentParmSeq;
    }

    public void setRuleSegmentParmSeq(Long ruleSegmentParmSeq) {
        this.ruleSegmentParmSeq = ruleSegmentParmSeq;
    }

    
    public String getRuleSegmentParmStringValue() {
        return ruleSegmentParmStringValue;
    }

    public void setRuleSegmentParmStringValue(String ruleSegmentParmStringValue) {
        this.ruleSegmentParmStringValue = ruleSegmentParmStringValue;
    }

    
    public BigDecimal getRuleSegmentParmNumericValue() {
        return ruleSegmentParmNumericValue;
    }

    public void setRuleSegmentParmNumericValue(BigDecimal ruleSegmentParmNumericValue) {
        this.ruleSegmentParmNumericValue = ruleSegmentParmNumericValue;
    }

    
    public Date getRuleSegmentParmDateValue() {
        return ruleSegmentParmDateValue;
    }

    public void setRuleSegmentParmDateValue(Date ruleSegmentParmDateValue) {
        this.ruleSegmentParmDateValue = ruleSegmentParmDateValue;
    }

	
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RuleSegmentParm)) {
            return false;
        }
        RuleSegmentParm a = (RuleSegmentParm) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb
        .append(id, a.getId())
        .append(ruleSegmentId, a.getRuleSegmentId())
        .append(ruleSegmentParmSeq, a.getRuleSegmentParmSeq())
        .append(ruleSegmentParmStringValue, a.getRuleSegmentParmStringValue())
        .append(ruleSegmentParmNumericValue, a.getRuleSegmentParmNumericValue())
        .append(ruleSegmentParmDateValue, a.getRuleSegmentParmDateValue())
		;
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb
        .append(""+id)
        .append(""+ruleSegmentId)
        .append(""+ruleSegmentParmSeq)
        .append(""+ruleSegmentParmStringValue)
        .append(""+ruleSegmentParmNumericValue)
        .append(""+ruleSegmentParmDateValue)
		;
        return hcb.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
        .append(super.toString())
        .append("id", ""+id)
        .append("ruleSegmentId", ""+ruleSegmentId)
        .append("ruleSegmentParmSeq", ""+ruleSegmentParmSeq)
        .append("ruleSegmentParmStringValue", ""+ruleSegmentParmStringValue)
        .append("ruleSegmentParmNumericValue", ""+ruleSegmentParmNumericValue)
        .append("ruleSegmentParmDateValue", ""+ruleSegmentParmDateValue)
        .toString();
    }

}