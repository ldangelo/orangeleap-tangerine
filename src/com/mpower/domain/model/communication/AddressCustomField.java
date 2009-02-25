package com.mpower.domain.model.communication;

import com.mpower.domain.model.CustomField;

public class AddressCustomField {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Address address;
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