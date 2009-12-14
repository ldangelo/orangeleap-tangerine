package com.orangeleap.tangerine.domain.customization.rule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.domain.GeneratedId;

public class Rule implements GeneratedId, Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private String ruleEventTypeNameId;
    private Long ruleSeq;
    private String ruleDesc;
    private boolean ruleIsActive;
    private String siteName;
	
    private List<RuleVersion> ruleVersions = new ArrayList<RuleVersion>();

    
    public Rule() { }

    
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

    
    public Long getRuleSeq() {
        return ruleSeq;
    }

    public void setRuleSeq(Long ruleSeq) {
        this.ruleSeq = ruleSeq;
    }

    
    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    
    public boolean getRuleIsActive() {
        return ruleIsActive;
    }

    public void setRuleIsActive(boolean ruleIsActive) {
        this.ruleIsActive = ruleIsActive;
    }

    
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

	
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Rule)) {
            return false;
        }
        Rule a = (Rule) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb
        .append(id, a.getId())
        .append(ruleEventTypeNameId, a.getRuleEventTypeNameId())
        .append(ruleSeq, a.getRuleSeq())
        .append(ruleDesc, a.getRuleDesc())
        .append(ruleIsActive, a.getRuleIsActive())
        .append(siteName, a.getSiteName())
		;
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb
        .append(""+id)
        .append(""+ruleEventTypeNameId)
        .append(""+ruleSeq)
        .append(""+ruleDesc)
        .append(""+ruleIsActive)
        .append(""+siteName)
		;
        return hcb.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
        .append(super.toString())
        .append("id", ""+id)
        .append("ruleEventTypeNameId", ""+ruleEventTypeNameId)
        .append("ruleSeq", ""+ruleSeq)
        .append("ruleDesc", ""+ruleDesc)
        .append("ruleIsActive", ""+ruleIsActive)
        .append("siteName", ""+siteName)
        .toString();
    }


	public void setRuleVersions(List<RuleVersion> ruleVersions) {
		this.ruleVersions = ruleVersions;
	}


	public List<RuleVersion> getRuleVersions() {
		return ruleVersions;
	}

}