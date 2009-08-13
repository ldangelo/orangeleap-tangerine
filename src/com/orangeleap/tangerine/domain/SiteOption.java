package com.orangeleap.tangerine.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;

//@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public class SiteOption implements GeneratedId, Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private String siteName;
    private String optionName;
    private String optionValue;
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

    
    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
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
        .append(optionValue, a.getOptionValue())
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
        .append(""+optionValue)
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
        .append("optionValue", ""+optionValue)
        .append("modifiedBy", ""+modifiedBy)
        .toString();
    }

}