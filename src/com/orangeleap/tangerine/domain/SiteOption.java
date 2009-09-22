package com.orangeleap.tangerine.domain;

import java.util.Date;
import java.math.BigDecimal;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;
import org.springframework.util.StringUtils;

import com.orangeleap.tangerine.util.StringConstants;

public class SiteOption implements GeneratedId, Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private String siteName;
    private String optionName;
    private boolean optionNameReadOnly;
    private String optionDesc;
    private String optionValue;
    private boolean optionValueReadOnly;
    private Long modifiedBy;
    private Date createDate;
    private Date updateDate;
	
    public SiteOption() { }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    
    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    
    public boolean getOptionNameReadOnly() {
        return optionNameReadOnly;
    }

    public void setOptionNameReadOnly(boolean optionNameReadOnly) {
        this.optionNameReadOnly = optionNameReadOnly;
    }

    
    public String getOptionDesc() {
        return optionDesc;
    }

    public void setOptionDesc(String optionDesc) {
        this.optionDesc = optionDesc;
    }

    
    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    
    public boolean getOptionValueReadOnly() {
        return optionValueReadOnly;
    }

    public void setOptionValueReadOnly(boolean optionValueReadOnly) {
        this.optionValueReadOnly = optionValueReadOnly;
    }

    
    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
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
        if (!(obj instanceof SiteOption)) {
            return false;
        }
        SiteOption a = (SiteOption) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb
        .append(id, a.getId())
        .append(siteName, a.getSiteName())
        .append(optionName, a.getOptionName())
        .append(optionNameReadOnly, a.getOptionNameReadOnly())
        .append(optionDesc, a.getOptionDesc())
        .append(optionValue, a.getOptionValue())
        .append(optionValueReadOnly, a.getOptionValueReadOnly())
        .append(modifiedBy, a.getModifiedBy())
		;
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb
        .append(""+id)
        .append(""+siteName)
        .append(""+optionName)
        .append(""+optionNameReadOnly)
        .append(""+optionDesc)
        .append(""+optionValue)
        .append(""+optionValueReadOnly)
        .append(""+modifiedBy)
		;
        return hcb.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
        .append(super.toString())
        .append("id", ""+id)
        .append("siteName", ""+siteName)
        .append("optionName", ""+optionName)
        .append("optionNameReadOnly", ""+optionNameReadOnly)
        .append("optionDesc", ""+optionDesc)
        .append("optionValue", ""+optionValue)
        .append("optionValueReadOnly", ""+optionValueReadOnly)
        .append("modifiedBy", ""+modifiedBy)
        .toString();
    }

}