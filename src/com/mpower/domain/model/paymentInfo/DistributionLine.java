package com.mpower.domain.model.paymentInfo;

import java.math.BigDecimal;

import com.mpower.domain.model.AbstractCustomizableEntity;

public class DistributionLine extends AbstractCustomizableEntity { //Customizable, Viewable, TODO: for IBatis 
 
    private static final long serialVersionUID = 1L;
    private BigDecimal amount;
    private BigDecimal percentage;
    private String projectCode;
    private String motivationCode;
    private String other_motivationCode;
    private Long giftId;
    private Long commitmentId;

    public DistributionLine() { }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getMotivationCode() {
        return motivationCode;
    }

    public void setMotivationCode(String motivationCode) {
        this.motivationCode = motivationCode;
    }

    public String getOther_motivationCode() {
        return other_motivationCode;
    }

    public void setOther_motivationCode(String other_motivationCode) {
        this.other_motivationCode = other_motivationCode;
    }

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    public Long getCommitmentId() {
        return commitmentId;
    }

    public void setCommitmentId(Long commitmentId) {
        this.commitmentId = commitmentId;
    }

//    @Override
// TODO: for IBatis
//    public Person getPerson() {
//        return gift != null ? gift.getPerson() : null;
//    }
}
