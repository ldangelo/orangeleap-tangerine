package com.mpower.domain.model;

import java.io.Serializable;
import java.util.Date;

import com.mpower.domain.annotation.AutoPopulate;

public class Site implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String name;

    private String merchantNumber;

    private Site parentSite;

    @AutoPopulate
    private Date createDate;

    @AutoPopulate
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