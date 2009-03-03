package com.mpower.domain.model.paymentInfo;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.mpower.type.GiftEntryType;
import com.mpower.util.StringConstants;

public class Gift extends AbstractPaymentInfoEntity { // implements SiteAware, PaymentSourceAware, AddressAware, PhoneAware, EmailAware, Customizable, Viewable TODO: for IBatis 

    private static final long serialVersionUID = 1L;
    private Long commitmentId;
    private BigDecimal amount;
    private BigDecimal deductibleAmount;
    private Date transactionDate;
    private Date donationDate;
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
        if (donationDate == null) {
            donationDate = new Date();
        }
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
	
	@Override
    public void prePersist() {
        super.prePersist();
        if (deductibleAmount == null) {
            deductibleAmount = amount;
        }
    }
	
	public String toString() {
		return ""  + getAmount();
	}
	
}
