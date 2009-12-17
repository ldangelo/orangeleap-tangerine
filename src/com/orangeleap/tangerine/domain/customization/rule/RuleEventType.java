package com.orangeleap.tangerine.domain.customization.rule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.domain.GeneratedId;

public class RuleEventType implements GeneratedId, Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private String ruleEventTypeNameId;
	
    private List<RuleEventTypeXRuleSegmentType> ruleEventTypeXRuleSegmentTypes = new ArrayList<RuleEventTypeXRuleSegmentType>();

    public RuleEventType() { }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public String getRuleEventTypeNameId() {
        return ruleEventTypeNameId;
    }

    public void setRuleEventTypeNameId(String ruleEventTypeNameId) {
        this.ruleEventTypeNameId = ruleEventTypeNameId;
    }

	
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RuleEventType)) {
            return false;
        }
        RuleEventType a = (RuleEventType) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb
        .append(id, a.getId())
        .append(ruleEventTypeNameId, a.getRuleEventTypeNameId())
		;
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb
        .append(""+id)
        .append(""+ruleEventTypeNameId)
		;
        return hcb.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
        .append(super.toString())
        .append("id", ""+id)
        .append("ruleEventTypeNameId", ""+ruleEventTypeNameId)
        .toString();
    }


	
	public void setRuleEventTypeXRuleSegmentTypes(
			List<RuleEventTypeXRuleSegmentType> ruleEventTypeXRuleSegmentTypes) {
		this.ruleEventTypeXRuleSegmentTypes = ruleEventTypeXRuleSegmentTypes;
	}


	public List<RuleEventTypeXRuleSegmentType> getRuleEventTypeXRuleSegmentTypes() {
		return ruleEventTypeXRuleSegmentTypes;
	}

}