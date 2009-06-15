package com.orangeleap.tangerine.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.type.PaymentHistoryType;
import com.orangeleap.tangerine.util.StringConstants;

/*
 * Payment history for all payments including non-Gift, by Constituent
 */
public class PaymentHistory implements GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private PaymentHistoryType paymentHistoryType = PaymentHistoryType.UNSPECIFIED;
    private String transactionId = StringConstants.EMPTY;
    private Date transactionDate;
    private BigDecimal amount;
    private String currencyCode;
    private String paymentType = PaymentSource.CREDIT_CARD;
    private String paymentStatus;
    // CC# and ACH# must be stored in masked format
    private String description = StringConstants.EMPTY;
    private Constituent constituent;
    private Long giftId;
    private Long adjustedGiftId;
    
    public PaymentHistory() {  }

    public PaymentHistory(Constituent constituent) {
        this.constituent = constituent;
    }

	@Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Constituent getConstituent() {
        return constituent;
    }

    public void setConstituent(Constituent constituent) {
        this.constituent = constituent;
    }

    public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

    public String getPaymentType() {
		return paymentType;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setPaymentHistoryType(PaymentHistoryType paymentHistoryType) {
		this.paymentHistoryType = paymentHistoryType;
	}

	public PaymentHistoryType getPaymentHistoryType() {
		return paymentHistoryType;
	}

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    public Long getAdjustedGiftId() {
        return adjustedGiftId;
    }

    public void setAdjustedGiftId(Long adjustedGiftId) {
        this.adjustedGiftId = adjustedGiftId;
    }

    public Site getSite() {
        return constituent.getSite();
    }
    
    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", id).append("paymentHistoryType", paymentHistoryType).
            append(super.toString()).append("transactionId", transactionId).append("transactionDate", transactionDate).
            append(super.toString()).append("amount", amount).append("currencyCode", currencyCode).
            append(super.toString()).append("paymentType", paymentType).append("paymentStatus", paymentStatus).append("description", description).
            append(super.toString()).append("constituent", constituent).
            toString();
    }
}
