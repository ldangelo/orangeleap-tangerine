package com.orangeleap.tangerine.domain.customization.rule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.domain.GeneratedId;

public class RuleSegmentType implements GeneratedId, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public static final String CONDITION_TYPE = "condition";
    public static final String CONSEQUENCE_TYPE = "consequence";
    
    

    private Long id;
    private String ruleSegmentTypeType;
    private String ruleSegmentTypeText;
    
	private List<RuleSegmentTypeParm> ruleSegmentTypeParms = new ArrayList<RuleSegmentTypeParm>();

	
    public RuleSegmentType() { }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public String getRuleSegmentTypeType() {
        return ruleSegmentTypeType;
    }

    public void setRuleSegmentTypeType(String ruleSegmentTypeType) {
        this.ruleSegmentTypeType = ruleSegmentTypeType;
    }

    
    public String getRuleSegmentTypeText() {
        return ruleSegmentTypeText;
    }

    public void setRuleSegmentTypeText(String ruleSegmentTypeText) {
        this.ruleSegmentTypeText = ruleSegmentTypeText;
    }

	
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RuleSegmentType)) {
            return false;
        }
        RuleSegmentType a = (RuleSegmentType) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb
        .append(id, a.getId())
        .append(ruleSegmentTypeType, a.getRuleSegmentTypeType())
        .append(ruleSegmentTypeText, a.getRuleSegmentTypeText())
		;
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb
        .append(""+id)
        .append(""+ruleSegmentTypeType)
        .append(""+ruleSegmentTypeText)
		;
        return hcb.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
        .append(super.toString())
        .append("id", ""+id)
        .append("ruleSegmentTypeType", ""+ruleSegmentTypeType)
        .append("ruleSegmentTypeText", ""+ruleSegmentTypeText)
        .toString();
    }


	public void setRuleSegmentTypeParms(List<RuleSegmentTypeParm> ruleSegmentTypeParms) {
		this.ruleSegmentTypeParms = ruleSegmentTypeParms;
	}


	public List<RuleSegmentTypeParm> getRuleSegmentTypeParms() {
		return ruleSegmentTypeParms;
	}

}