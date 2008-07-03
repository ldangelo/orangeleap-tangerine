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

import com.mpower.domain.listener.EmptyStringNullifyerListener;

@Entity
@EntityListeners(value = { EmptyStringNullifyerListener.class })
@Table(name = "GIFT_CUSTOM_FIELD")
public class GiftCustomField implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "GIFT_CUSTOM_FIELD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "GIFT_ID")
    private Gift gift;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOM_FIELD_ID")
    private CustomField customField;

    public GiftCustomField() {
    }

    public GiftCustomField(Gift gift, CustomField customField) {
        this.gift = gift;
        this.customField = customField;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public CustomField getCustomField() {
        return customField;
    }

    public void setCustomField(CustomField customField) {
        this.customField = customField;
    }
}
