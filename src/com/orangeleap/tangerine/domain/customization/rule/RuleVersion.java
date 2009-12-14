package com.orangeleap.tangerine.domain.customization.rule;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.domain.GeneratedId;

public class RuleVersion implements GeneratedId, Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long ruleId;
    private Long ruleVersionSeq;
    private boolean ruleVersionIsTestOnly;
    private String updatedBy;
    private Date createDate;
    private Date updateDate;
	
    public RuleVersion() { }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    
    public Long getRuleVersionSeq() {
        return ruleVersionSeq;
    }

    public void setRuleVersionSeq(Long ruleVersionSeq) {
        this.ruleVersionSeq = ruleVersionSeq;
    }

    
    public boolean getRuleVersionIsTestOnly() {
        return ruleVersionIsTestOnly;
    }

    public void setRuleVersionIsTestOnly(boolean ruleVersionIsTestOnly) {
        this.ruleVersionIsTestOnly = ruleVersionIsTestOnly;
    }

    
    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

	
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RuleVersion)) {
            return false;
        }
        RuleVersion a = (RuleVersion) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb
        .append(id, a.getId())
        .append(ruleId, a.getRuleId())
        .append(ruleVersionSeq, a.getRuleVersionSeq())
        .append(ruleVersionIsTestOnly, a.getRuleVersionIsTestOnly())
        .append(updatedBy, a.getUpdatedBy())
		;
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb
        .append(""+id)
        .append(""+ruleId)
        .append(""+ruleVersionSeq)
        .append(""+ruleVersionIsTestOnly)
        .append(""+updatedBy)
		;
        return hcb.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
        .append(super.toString())
        .append("id", ""+id)
        .append("ruleId", ""+ruleId)
        .append("ruleVersionSeq", ""+ruleVersionSeq)
        .append("ruleVersionIsTestOnly", ""+ruleVersionIsTestOnly)
        .append("updatedBy", ""+updatedBy)
        .toString();
    }

}