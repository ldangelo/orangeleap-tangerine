package com.mpower.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Entity
@Table(name = "PAYMENT_SOURCE")
public class PaymentSource implements Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "PAYMENT_SOURCE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    @Column(name = "PAYMENT_TYPE")
    private String paymentType;

    @Column(name = "CREDIT_CARD_TYPE")
    private String creditCardType;

    @Column(name = "CREDIT_CARD_NUMBER")
    private String creditCardNumber;

    @Column(name = "CREDIT_CARD_EXPIRATION_DATE")
    private Date creditCardExpirationDate;

    // absolutely don't store this in the db - see VISA merchant rules
    // only used for processing
    @Transient
    private String creditCardSecurityCode;

    public PaymentSource() {
    }

    public PaymentSource(Person person) {
        this.person = person;
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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Date getCreditCardExpirationDate() {
        return creditCardExpirationDate;
    }

    public void setCreditCardExpirationDate(Date creditCardExpirationDate) {
        this.creditCardExpirationDate = creditCardExpirationDate;
    }

    public String getCreditCardSecurityCode() {
        return creditCardSecurityCode;
    }

    public void setCreditCardSecurityCode(String creditCardSecurityCode) {
        this.creditCardSecurityCode = creditCardSecurityCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PaymentSource == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        PaymentSource rhs = (PaymentSource) obj;
        return new EqualsBuilder().append(person, rhs.person).append(paymentType, rhs.paymentType).append(creditCardType, rhs.creditCardType).append(creditCardNumber, rhs.creditCardNumber).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(person).append(paymentType).append(creditCardType).append(creditCardNumber).toHashCode();
    }
}
