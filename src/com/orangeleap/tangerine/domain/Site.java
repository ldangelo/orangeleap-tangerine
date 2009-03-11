package com.orangeleap.tangerine.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.core.style.ToStringCreator;

public class Site implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public Site() {
        super();
    }

    public Site(String name) {
        super();
        this.name = name;
    }

    public Site(String name, String merchantNumber, String merchantBin) {
        this(name);
        this.merchantNumber = merchantNumber;
        this.merchantBin = merchantBin;
    }

    public Site(String name, String merchantNumber, String merchantBin, Site parentSite) {
        this(name, merchantNumber, merchantBin);
        this.parentSite = parentSite;
    }

    private String name;
    private String merchantNumber;
    private String merchantBin;
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

    public String getMerchantBin() {
        return merchantBin;
    }

    public void setMerchantBin(String merchantBin) {
        this.merchantBin = merchantBin;
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

    @Override
    public String toString() {
        return new ToStringCreator(this).append("name", name).append("merchantNumber", merchantNumber).append("merchantBin", merchantBin).append("parentSite", parentSite).
                append("createDate", createDate).append("updateDate", updateDate).toString();
    }
}