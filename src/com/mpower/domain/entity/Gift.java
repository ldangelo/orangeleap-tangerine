package com.mpower.domain.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mpower.domain.entity.listener.EmptyStringNullifyerListener;

@Entity
@EntityListeners(value = { EmptyStringNullifyerListener.class })
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PAYMENT_SOURCE_ID")
    private PaymentSource paymentSource;

    @Column(name = "VALUE")
    private BigDecimal value;

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

    public PaymentSource getPaymentSource() {
        if (paymentSource == null) {
            paymentSource = new PaymentSource(person);
        }
        return paymentSource;
    }

    public void setPaymentSource(PaymentSource paymentSource) {
        this.paymentSource = paymentSource;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
