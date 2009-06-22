package com.orangeleap.tangerine.domain;

import java.util.Date;
import java.math.BigDecimal;
import java.io.Serializable;


import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;
import org.springframework.util.StringUtils;

import com.orangeleap.tangerine.type.ActivationType;
import com.orangeleap.tangerine.util.StringConstants;

@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public class Journal implements GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long postBatchId;
    private String entity;
    private Long entityId;
    private String masterEntity;
    private Long masterEntityId;
    private String origEntity;
    private Long origEntityId;
    private String code;
    private String glCode1;
    private String glCode2;
    private BigDecimal amount;
    private String jeType;
    private String paymentMethod;
    private String ccType;
    private String desc;
    private Date donationDate;
    private Date adjustmentDate;
    private Date postedDate;
    private Date exportDate;
    private String siteName;
    private Date createDate;
    private Date updateDate;

    public Journal() { }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getPostBatchId() {
        return postBatchId;
    }

    public void setPostBatchId(Long postBatchId) {
        this.postBatchId = postBatchId;
    }


    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }


    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }


    public String getMasterEntity() {
        return masterEntity;
    }

    public void setMasterEntity(String masterEntity) {
        this.masterEntity = masterEntity;
    }


    public Long getMasterEntityId() {
        return masterEntityId;
    }

    public void setMasterEntityId(Long masterEntityId) {
        this.masterEntityId = masterEntityId;
    }


    public String getOrigEntity() {
        return origEntity;
    }

    public void setOrigEntity(String origEntity) {
        this.origEntity = origEntity;
    }


    public Long getOrigEntityId() {
        return origEntityId;
    }

    public void setOrigEntityId(Long origEntityId) {
        this.origEntityId = origEntityId;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getGlCode1() {
        return glCode1;
    }

    public void setGlCode1(String glCode1) {
        this.glCode1 = glCode1;
    }


    public String getGlCode2() {
        return glCode2;
    }

    public void setGlCode2(String glCode2) {
        this.glCode2 = glCode2;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public String getJeType() {
        return jeType;
    }

    public void setJeType(String jeType) {
        this.jeType = jeType;
    }


    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    public String getCcType() {
        return ccType;
    }

    public void setCcType(String ccType) {
        this.ccType = ccType;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public Date getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
    }


    public Date getAdjustmentDate() {
        return adjustmentDate;
    }

    public void setAdjustmentDate(Date adjustmentDate) {
        this.adjustmentDate = adjustmentDate;
    }


    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }


    public Date getExportDate() {
        return exportDate;
    }

    public void setExportDate(Date exportDate) {
        this.exportDate = exportDate;
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
        if (!(obj instanceof Journal)) {
            return false;
        }
        Journal a = (Journal) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb
        .append(id, a.getId())
        .append(postBatchId, a.getPostBatchId())
        .append(entity, a.getEntity())
        .append(entityId, a.getEntityId())
        .append(masterEntity, a.getMasterEntity())
        .append(masterEntityId, a.getMasterEntityId())
        .append(origEntity, a.getOrigEntity())
        .append(origEntityId, a.getOrigEntityId())
        .append(code, a.getCode())
        .append(glCode1, a.getGlCode1())
        .append(glCode2, a.getGlCode2())
        .append(amount, a.getAmount())
        .append(jeType, a.getJeType())
        .append(paymentMethod, a.getPaymentMethod())
        .append(ccType, a.getCcType())
        .append(desc, a.getDesc())
        .append(donationDate, a.getDonationDate())
        .append(adjustmentDate, a.getAdjustmentDate())
        .append(postedDate, a.getPostedDate())
        .append(exportDate, a.getExportDate())
        .append(siteName, a.getSiteName())
		;
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb
        .append(""+id)
        .append(""+postBatchId)
        .append(""+entity)
        .append(""+entityId)
        .append(""+masterEntity)
        .append(""+masterEntityId)
        .append(""+origEntity)
        .append(""+origEntityId)
        .append(""+code)
        .append(""+glCode1)
        .append(""+glCode2)
        .append(""+amount)
        .append(""+jeType)
        .append(""+paymentMethod)
        .append(""+ccType)
        .append(""+desc)
        .append(""+donationDate)
        .append(""+adjustmentDate)
        .append(""+postedDate)
        .append(""+exportDate)
        .append(""+siteName)
		;
        return hcb.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
        .append(super.toString())
        .append("id", ""+id)
        .append("postBatchId", ""+postBatchId)
        .append("entity", ""+entity)
        .append("entityId", ""+entityId)
        .append("masterEntity", ""+masterEntity)
        .append("masterEntityId", ""+masterEntityId)
        .append("origEntity", ""+origEntity)
        .append("origEntityId", ""+origEntityId)
        .append("code", ""+code)
        .append("glCode1", ""+glCode1)
        .append("glCode2", ""+glCode2)
        .append("amount", ""+amount)
        .append("jeType", ""+jeType)
        .append("paymentMethod", ""+paymentMethod)
        .append("ccType", ""+ccType)
        .append("desc", ""+desc)
        .append("donationDate", ""+donationDate)
        .append("adjustmentDate", ""+adjustmentDate)
        .append("postedDate", ""+postedDate)
        .append("exportDate", ""+exportDate)
        .append("siteName", ""+siteName)
        .toString();
    }

}