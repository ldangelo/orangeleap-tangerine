package com.orangeleap.tangerine.domain.paymentInfo;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.type.GiftEntryType;
import com.orangeleap.tangerine.type.GiftType;
import com.orangeleap.tangerine.util.StringConstants;

public class Gift extends AbstractPaymentInfoEntity { 

    private static final long serialVersionUID = 1L;
    private GiftType giftType;
    private Long commitmentId;
    private BigDecimal amount;
    private BigDecimal deductibleAmount;
    private Date transactionDate;
    private Date donationDate = new Date();
    private Date postmarkDate;
    private String authCode = StringConstants.EMPTY;
    private Long originalGiftId;
    private Long refundGiftId;
    private Date refundGiftTransactionDate;
    private boolean deductible = false;
    private String txRefNum;
    private String paymentStatus;
    private String paymentMessage;
    private GiftEntryType entryType = GiftEntryType.MANUAL;
    
    public Gift() { }

    public Gift(Commitment commitment, Date transactionDate) {
        this.commitmentId = commitment.getId();
        this.person = commitment.getPerson();
        this.transactionDate = transactionDate;
        this.amount = commitment.getAmountPerGift();
    }
    
    public Gift(GiftInKind giftInKind) {
        setGiftForGiftInKind(giftInKind);
    }

    public GiftType getGiftType() {
        return giftType;
    }

    public void setGiftType(GiftType giftType) {
        this.giftType = giftType;
    }

    public Long getCommitmentId() {
        return commitmentId;
    }

    public void setCommitmentId(Long commitmentId) {
        this.commitmentId = commitmentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDeductibleAmount() {
        return deductibleAmount;
    }

    public void setDeductibleAmount(BigDecimal deductibleAmount) {
        this.deductibleAmount = deductibleAmount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
    }

    public Date getPostmarkDate() {
        return postmarkDate;
    }

    public void setPostmarkDate(Date postmarkDate) {
        this.postmarkDate = postmarkDate;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Long getOriginalGiftId() {
        return originalGiftId;
    }

    public void setOriginalGiftId(Long originalGiftId) {
        this.originalGiftId = originalGiftId;
    }

    public Long getRefundGiftId() {
        return refundGiftId;
    }

    public void setRefundGiftId(Long refundGiftId) {
        this.refundGiftId = refundGiftId;
    }

    public Date getRefundGiftTransactionDate() {
        return refundGiftTransactionDate;
    }

    public void setRefundGiftTransactionDate(Date refundGiftTransactionDate) {
        this.refundGiftTransactionDate = refundGiftTransactionDate;
    }

    public boolean isDeductible() {
        return deductible;
    }

    public void setDeductible(boolean deductible) {
        this.deductible = deductible;
    }

    public GiftEntryType getEntryType() {
        return entryType;
    }

    public void setEntryType(GiftEntryType entryType) {
        this.entryType = entryType;
    }

    public String getTxRefNum() {
        return txRefNum;
    }

	public void setTxRefNum(String txRefNum) {
		this.txRefNum = txRefNum;
	}

    public String getPaymentStatus() {
        return paymentStatus;
    }
	
	public void setPaymentStatus(String status) {
		this.paymentStatus = status;
	}

    public String getPaymentMessage() {
        return paymentMessage;
    }
	
	public void setPaymentMessage(String message) {
		this.paymentMessage = message;
	}
	
	public Boolean getIsAuthorized() {
		return !StringUtils.trimToEmpty(authCode).equals("");
	}
	
	public Boolean getIsCaptured() {
		return !StringUtils.trimToEmpty(txRefNum).equals("");
	}
	
	public Boolean getIsProcessed() {
		return !StringUtils.trimToEmpty(txRefNum).equals("");
	}
	
	public Boolean getIsDeclined() {
		return StringUtils.trimToEmpty(paymentStatus).equals("");
	}
	
	public void setGiftForGiftInKind(GiftInKind giftInKind) {
        this.giftType = GiftType.GIFT_IN_KIND;
        this.currencyCode = giftInKind.getCurrencyCode();
        this.donationDate = giftInKind.getDonationDate();
        this.sendAcknowledgment = giftInKind.isSendAcknowledgment();
        this.acknowledgmentDate = giftInKind.getAcknowledgmentDate();
        this.transactionDate = giftInKind.getTransactionDate();
        this.person = giftInKind.getPerson();
        this.selectedEmail = giftInKind.getSelectedEmail();
	}
	
	@Override
    public void prePersist() {
        super.prePersist();
        if (deductibleAmount == null) {
            deductibleAmount = amount;
        }
    }
    
    @Override
    public String toString() {
        return new ToStringCreator(this).append(super.toString()).append("giftType", giftType).append("commitmentId", commitmentId).append("amount", amount).
            append(super.toString()).append("deductibleAmount", deductibleAmount).append("transactionDate", transactionDate).
            append(super.toString()).append("donationDate", donationDate).append("postmarkDate", postmarkDate).
            append(super.toString()).append("authCode", authCode).append("originalGiftId", originalGiftId).
            append(super.toString()).append("refundGiftId", refundGiftId).append("refundGiftTransactionDate", refundGiftTransactionDate).
            append(super.toString()).append("deductible", deductible).append("txRefNum", txRefNum).
            append(super.toString()).append("paymentStatus", paymentStatus).append("paymentMessage", paymentMessage).
            append(super.toString()).append("entryType", entryType).
            toString();
    }
}
