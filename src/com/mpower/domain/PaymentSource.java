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
import javax.persistence.UniqueConstraint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Entity
@Table(name = "PAYMENT_SOURCE", uniqueConstraints = @UniqueConstraint(columnNames = { "PAYMENT_NAME", "PERSON_ID" }))
public class PaymentSource implements SiteAware, Serializable {

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

    @Column(name = "PAYMENT_NAME")
    private String name;

    @Column(name = "PAYMENT_TYPE")
    private String type;

    @Column(name = "CREDIT_CARD_TYPE")
    private String creditCardType;

    @Column(name = "CREDIT_CARD_NUMBER")
    private String creditCardNumber;

    @Column(name = "CREDIT_CARD_EXPIRATION")
    private Date creditCardExpiration;

    @Column(name = "CHECK_NUMBER")
    private Integer checkNumber;

    @Column(name = "ACH_TYPE")
    private String achType;

    @Column(name = "ACH_ROUTING_NUMBER")
    private String achRoutingNumber;

    @Column(name = "ACH_ACCOUNT_NUMBER")
    private String achAccountNumber;

    // absolutely don't store this in the db - see VISA merchant rules only used for processing
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Date getCreditCardExpiration() {
        return creditCardExpiration;
    }

    public void setCreditCardExpiration(Date creditCardExpiration) {
        this.creditCardExpiration = creditCardExpiration;
    }

    public Integer getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(Integer checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getAchType() {
        return achType;
    }

    public void setAchType(String achType) {
        this.achType = achType;
    }

    public String getAchRoutingNumber() {
        return achRoutingNumber;
    }

    public void setAchRoutingNumber(String achRoutingNumber) {
        this.achRoutingNumber = achRoutingNumber;
    }

    public String getAchAccountNumber() {
        return achAccountNumber;
    }

    public void setAchAccountNumber(String achAccountNumber) {
        this.achAccountNumber = achAccountNumber;
    }

    public String getCreditCardSecurityCode() {
        return creditCardSecurityCode;
    }

    public void setCreditCardSecurityCode(String creditCardSecurityCode) {
        this.creditCardSecurityCode = creditCardSecurityCode;
    }

    public Site getSite() {
        return person != null ? person.getSite() : null;
    }
}
