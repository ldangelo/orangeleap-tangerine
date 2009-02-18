package com.mpower.domain.model;

import java.io.Serializable;
import java.util.Date;

public class Site implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public Site() {
        super();
    }

    public Site(String name, String merchantNumber, Site parentSite) {
        this();
        this.name = name;
        this.merchantNumber = merchantNumber;
        this.parentSite = parentSite;
    }

    private String name;
    private String merchantNumber;
    private Site parentSite;
    private Date createDate;
    private Date updateDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMerchantNumber() {
        return merchantNumber;
    }

    public void setMerchantNumber(String merchantNumber) {
        this.merchantNumber = merchantNumber;
    }

    public Site getParentSite() {
        return parentSite;
    }

    public void setParentSite(Site parentSite) {
        this.parentSite = parentSite;
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
}