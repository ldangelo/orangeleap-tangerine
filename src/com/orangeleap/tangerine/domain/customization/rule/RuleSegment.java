package com.orangeleap.tangerine.domain.customization.rule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.domain.GeneratedId;

public class RuleSegment implements GeneratedId, Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long ruleVersionId;
    private Long ruleSegmentSeq;
    private Long ruleSegmentTypeId;

    private List<RuleSegmentParm> ruleSegmentParms = new ArrayList<RuleSegmentParm>();

	
    public RuleSegment() { }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public Long getRuleVersionId() {
        return ruleVersionId;
    }

    public void setRuleVersionId(Long ruleVersionId) {
        this.ruleVersionId = ruleVersionId;
    }

    
    public Long getRuleSegmentSeq() {
        return ruleSegmentSeq;
    }

    public void setRuleSegmentSeq(Long ruleSegmentSeq) {
        this.ruleSegmentSeq = ruleSegmentSeq;
    }

    
    public Long getRuleSegmentTypeId() {
        return ruleSegmentTypeId;
    }

    public void setRuleSegmentTypeId(Long ruleSegmentTypeId) {
        this.ruleSegmentTypeId = ruleSegmentTypeId;
    }

	
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RuleSegment)) {
            return false;
        }
        RuleSegment a = (RuleSegment) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb
        .append(id, a.getId())
        .append(ruleVersionId, a.getRuleVersionId())
        .append(ruleSegmentSeq, a.getRuleSegmentSeq())
        .append(ruleSegmentTypeId, a.getRuleSegmentTypeId())
		;
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb
        .append(""+id)
        .append(""+ruleVersionId)
        .append(""+ruleSegmentSeq)
        .append(""+ruleSegmentTypeId)
		;
        return hcb.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
        .append(super.toString())
        .append("id", ""+id)
        .append("ruleVersionId", ""+ruleVersionId)
        .append("ruleSegmentSeq", ""+ruleSegmentSeq)
        .append("ruleSegmentTypeId", ""+ruleSegmentTypeId)
        .toString();
    }


	public void setRuleSegmentParms(List<RuleSegmentParm> ruleSegmentParms) {
		this.ruleSegmentParms = ruleSegmentParms;
	}


	public List<RuleSegmentParm> getRuleSegmentParms() {
		return ruleSegmentParms;
	}

}