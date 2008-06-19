package com.mpower.domain.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.mpower.domain.entity.annotation.AutoPopulate;
import com.mpower.domain.entity.listener.EmptyStringNullifyerListener;
import com.mpower.domain.entity.listener.TemporalTimestampListener;

@Entity
@EntityListeners(value = { EmptyStringNullifyerListener.class, TemporalTimestampListener.class })
@Table(name = "GIFT")
public class Gift implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "GIFT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "VALUE")
    private BigDecimal value;

    @Column(name = "PAYMENT_TYPE")
    private String paymentType;

    @Column(name = "CREDIT_CARD_TYPE")
    private String creditCardType;

    @Column(name = "CREDIT_CARD_NUMBER")
    private String creditCardNumber;

    @Column(name = "CREDIT_CARD_EXPIRATION_DATE")
    private Date creditCardExpirationDate;

    @Column(name = "GIFT_ENTERED_DATE", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @AutoPopulate
    private Date giftEnteredDate;

    @Transient
    private String creditCardExpiration;

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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
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

    public String getCreditCardExpiration() {
        if (creditCardExpiration == null) {
            if (getCreditCardExpirationDate() != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(creditCardExpirationDate);
                creditCardExpiration = "" + (calendar.get(Calendar.MONTH + 1)) + "/" + calendar.get(Calendar.YEAR);
            }
        }
        return creditCardExpiration;
    }

    public void setCreditCardExpiration(String creditCardExpiration) {
        SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(creditCardExpiration));
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getMaximum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
            calendar.set(Calendar.MILLISECOND, calendar.getMaximum(Calendar.MILLISECOND));
            creditCardExpirationDate = calendar.getTime();
            this.creditCardExpiration = creditCardExpiration;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Date getGiftEnteredDate() {
        return giftEnteredDate;
    }

    public void setGiftEnteredDate(Date giftEnteredDate) {
        this.giftEnteredDate = giftEnteredDate;
    }
}
