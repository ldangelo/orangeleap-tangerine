package com.orangeleap.tangerine.domain.paymentInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.type.GiftEntryType;
import com.orangeleap.tangerine.type.GiftType;
import com.orangeleap.tangerine.util.StringConstants;

public class Gift extends AbstractPaymentInfoEntity { 

    private static final long serialVersionUID = 1L;
    private String giftStatus;
    private GiftType giftType;
    private Long recurringGiftId;
    private Long pledgeId;
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
    private List<Pledge> pledges;
    
    public Gift() { 
        super();
    }

    // TODO: fix
    public Gift(Commitment commitment, Date transactionDate) {
        this();
        if (commitment instanceof RecurringGift) {
            this.recurringGiftId = commitment.getId();
        }
        else if (commitment instanceof Pledge) {
            this.pledgeId = commitment.getId();
        }
        this.person = commitment.getPerson();
        this.transactionDate = transactionDate;
        this.amount = commitment.getAmountPerGift();
    }
    
    public Gift(GiftInKind giftInKind) {
        this();
        setGiftForGiftInKind(giftInKind);
    }

    public String getGiftStatus() {
        return giftStatus;
    }

    public void setGiftStatus(String giftStatus) {
        this.giftStatus = giftStatus;
    }

    public GiftType getGiftType() {
        return giftType;
    }

    public void setGiftType(GiftType giftType) {
        this.giftType = giftType;
    }

    public Long getRecurringGiftId() {
        return recurringGiftId;
    }

    public void setRecurringGiftId(Long recurringGiftId) {
        this.recurringGiftId = recurringGiftId;
    }

    public Long getPledgeId() {
        return pledgeId;
    }

    public void setPledgeId(Long pledgeId) {
        this.pledgeId = pledgeId;
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
	
	public List<Pledge> getPledges() {
        return pledges;
    }

    public void setPledges(List<Pledge> pledges) {
        this.pledges = pledges;
    }

    public void addPledge(Pledge pledge) {
        if (pledges == null) {
            setPledges(new ArrayList<Pledge>());
        }
        getPledges().add(pledge);
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
        if (this.giftType == null) {
            this.giftType = GiftType.MONETARY_GIFT;
        }
        if (deductibleAmount == null) {
            deductibleAmount = amount;
        }
    }
    
    @Override
    public String toString() {
        return new ToStringCreator(this).append(super.toString()).append("giftStatus", giftStatus).append("giftType", giftType).append("recurringGiftId", recurringGiftId).
            append("amount", amount).append("pledgeId", pledgeId).
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
