package com.mpower.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.listener.EmptyStringNullifyerListener;

@Entity
@EntityListeners(value = { EmptyStringNullifyerListener.class })
@Table(name = "PERSON_PHONE")
public class PersonPhone implements Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "PERSON_PHONE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "PHONE_ID")
    private Phone phone;

    public PersonPhone() {
    }

    public PersonPhone(Person person, Phone phone) {
        this.person = person;
        this.phone = phone;
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

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }
}
