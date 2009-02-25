package com.mpower.domain.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.*;

public class AddressCustomField {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "ADDRESS_CUSTOM_FIELD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOM_FIELD_ID")
    private CustomField customField;

    public AddressCustomField() {
    }

    public AddressCustomField(Address address, CustomField customField) {
        this.address = address;
        this.customField = customField;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public CustomField getCustomField() {
        return customField;
    }

    public void setCustomField(CustomField customField) {
        this.customField = customField;
    }
}