package com.orangeleap.tangerine.domain.customization.rule;

import java.util.Date;
import java.math.BigDecimal;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;
import org.springframework.util.StringUtils;

import com.orangeleap.tangerine.domain.GeneratedId;
import com.orangeleap.tangerine.util.StringConstants;

public class RuleGeneratedCode implements GeneratedId, Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private String ruleEventTypeNameId;
    private boolean isTestOnly;
    private String generatedCodeText;
    private String siteName;
    private Date createDate;
    private Date updateDate;
	
    public RuleGeneratedCode() { }

    
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

    
    public boolean getIsTestOnly() {
        return isTestOnly;
    }

    public void setIsTestOnly(boolean isTestOnly) {
        this.isTestOnly = isTestOnly;
    }

    
    public String getGeneratedCodeText() {
        return generatedCodeText;
    }

    public void setGeneratedCodeText(String generatedCodeText) {
        this.generatedCodeText = generatedCodeText;
    }

    
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
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
        if (!(obj instanceof RuleGeneratedCode)) {
            return false;
        }
        RuleGeneratedCode a = (RuleGeneratedCode) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb
        .append(id, a.getId())
        .append(ruleEventTypeNameId, a.getRuleEventTypeNameId())
        .append(isTestOnly, a.getIsTestOnly())
        .append(generatedCodeText, a.getGeneratedCodeText())
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
        .append(""+isTestOnly)
        .append(""+generatedCodeText)
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
        .append("isTestOnly", ""+isTestOnly)
        .append("generatedCodeText", ""+generatedCodeText)
        .append("siteName", ""+siteName)
        .toString();
    }

}