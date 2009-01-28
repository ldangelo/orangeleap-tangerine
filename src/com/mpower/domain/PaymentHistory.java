package com.mpower.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.type.PaymentHistoryType;

/*
 * Payment history for all payments including non-Gift, by Constituent
 */

@Entity
@Table(name = "PAYMENT_HISTORY")
public class PaymentHistory implements SiteAware, Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "PAYMENT_HISTORY_ID", updatable = false)
    private Long id;

    @Column(name = "PAYMENT_HISTORY_TYPE", updatable = false)
    private PaymentHistoryType paymentHistoryType = PaymentHistoryType.UNSPECIFIED;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID", updatable = false)
    private Person person;

    @Column(name = "TRANSACTION_ID", updatable = false)
    private String transactionId = "";

    @Column(name = "TRANSACTION_DATE", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "CURRENCY_CODE")
    private String currencyCode;

    @Column(name = "PAYMENT_TYPE", updatable = false)
    private String paymentType = PaymentSource.CREDIT_CARD;

    // CC# and ACH# must be stored in masked format
    @Column(name = "PAYMENT_DESC", updatable = false)
    private String description = "";

    @ManyToOne(optional = true)
    @JoinColumn(name = "GIFT_ID", updatable = false)
    private Gift gift;

    
    
    public PaymentHistory() {
    }

    public PaymentHistory(Person person) {
        this.person = person;
    }

	@Override
	public Site getSite() {
		return person.getSite();
	}

    public Long getId() {
        return id;
    }

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


}
