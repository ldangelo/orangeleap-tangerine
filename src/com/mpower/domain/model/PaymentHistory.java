package com.mpower.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.core.style.ToStringCreator;

import com.mpower.domain.GeneratedId;
import com.mpower.domain.model.paymentInfo.Gift;
import com.mpower.type.PaymentHistoryType;
import com.mpower.util.StringConstants;

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
    // CC# and ACH# must be stored in masked format
    private String description = StringConstants.EMPTY;
    private Person person;
    private Gift gift;
    
    public PaymentHistory() {  }

    public PaymentHistory(Person person) {
        this.person = person;
    }

	@Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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

	public void setGift(Gift gift) {
		this.gift = gift;
	}

	public Gift getGift() {
		return gift;
	}

	public void setPaymentHistoryType(PaymentHistoryType paymentHistoryType) {
		this.paymentHistoryType = paymentHistoryType;
	}

	public PaymentHistoryType getPaymentHistoryType() {
		return paymentHistoryType;
	}

    public Site getSite() {
        return person.getSite();
    }
    
    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", id).append("paymentHistoryType", paymentHistoryType).
            append(super.toString()).append("transactionId", transactionId).append("transactionDate", transactionDate).
            append(super.toString()).append("amount", amount).append("currencyCode", currencyCode).
            append(super.toString()).append("paymentType", paymentType).append("description", description).
            append(super.toString()).append("person", person).append("gift", gift).
            toString();
    }
}
