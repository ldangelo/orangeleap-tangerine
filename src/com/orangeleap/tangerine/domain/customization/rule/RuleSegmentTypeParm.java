package com.orangeleap.tangerine.domain.customization.rule;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.domain.GeneratedId;

public class RuleSegmentTypeParm implements GeneratedId, Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long ruleSegmentTypeId;
    private Long ruleSegmentTypeParmSeq;
    private String ruleSegmentTypeParmType;
    private String ruleSegmentTypeParmPicklistNameId;
	
    public RuleSegmentTypeParm() { }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public Long getRuleSegmentTypeId() {
        return ruleSegmentTypeId;
    }

    public void setRuleSegmentTypeId(Long ruleSegmentTypeId) {
        this.ruleSegmentTypeId = ruleSegmentTypeId;
    }

    
    public Long getRuleSegmentTypeParmSeq() {
        return ruleSegmentTypeParmSeq;
    }

    public void setRuleSegmentTypeParmSeq(Long ruleSegmentTypeParmSeq) {
        this.ruleSegmentTypeParmSeq = ruleSegmentTypeParmSeq;
    }

    
    public String getRuleSegmentTypeParmType() {
        return ruleSegmentTypeParmType;
    }

    public void setRuleSegmentTypeParmType(String ruleSegmentTypeParmType) {
        this.ruleSegmentTypeParmType = ruleSegmentTypeParmType;
    }

    
    public String getRuleSegmentTypeParmPicklistNameId() {
        return ruleSegmentTypeParmPicklistNameId;
    }

    public void setRuleSegmentTypeParmPicklistNameId(String ruleSegmentTypeParmPicklistNameId) {
        this.ruleSegmentTypeParmPicklistNameId = ruleSegmentTypeParmPicklistNameId;
    }

	
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RuleSegmentTypeParm)) {
            return false;
        }
        RuleSegmentTypeParm a = (RuleSegmentTypeParm) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb
        .append(id, a.getId())
        .append(ruleSegmentTypeId, a.getRuleSegmentTypeId())
        .append(ruleSegmentTypeParmSeq, a.getRuleSegmentTypeParmSeq())
        .append(ruleSegmentTypeParmType, a.getRuleSegmentTypeParmType())
        .append(ruleSegmentTypeParmPicklistNameId, a.getRuleSegmentTypeParmPicklistNameId())
		;
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb
        .append(""+id)
        .append(""+ruleSegmentTypeId)
        .append(""+ruleSegmentTypeParmSeq)
        .append(""+ruleSegmentTypeParmType)
        .append(""+ruleSegmentTypeParmPicklistNameId)
		;
        return hcb.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
        .append(super.toString())
        .append("id", ""+id)
        .append("ruleSegmentTypeId", ""+ruleSegmentTypeId)
        .append("ruleSegmentTypeParmSeq", ""+ruleSegmentTypeParmSeq)
        .append("ruleSegmentTypeParmType", ""+ruleSegmentTypeParmType)
        .append("ruleSegmentTypeParmPicklistNameId", ""+ruleSegmentTypeParmPicklistNameId)
        .toString();
    }

}