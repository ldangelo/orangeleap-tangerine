package com.orangeleap.tangerine.domain.customization.rule;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;

public class RuleEventTypeXRuleSegmentType implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long ruleEventTypeId;
    private Long ruleSegmentTypeId;
	
    public RuleEventTypeXRuleSegmentType() { }

    
    public Long getRuleEventTypeId() {
        return ruleEventTypeId;
    }

    public void setRuleEventTypeId(Long ruleEventTypeId) {
        this.ruleEventTypeId = ruleEventTypeId;
    }

    
    public Long getRuleSegmentTypeId() {
        return ruleSegmentTypeId;
    }

    public void setRuleSegmentTypeId(Long ruleSegmentTypeId) {
        this.ruleSegmentTypeId = ruleSegmentTypeId;
    }

	
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RuleEventTypeXRuleSegmentType)) {
            return false;
        }
        RuleEventTypeXRuleSegmentType a = (RuleEventTypeXRuleSegmentType) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb
        .append(ruleEventTypeId, a.getRuleEventTypeId())
        .append(ruleSegmentTypeId, a.getRuleSegmentTypeId())
		;
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb
        .append(""+ruleEventTypeId)
        .append(""+ruleSegmentTypeId)
		;
        return hcb.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
        .append(super.toString())
        .append("ruleEventTypeId", ""+ruleEventTypeId)
        .append("ruleSegmentTypeId", ""+ruleSegmentTypeId)
        .toString();
    }

}