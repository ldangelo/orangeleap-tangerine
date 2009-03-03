package com.mpower.domain.model;

import com.mpower.domain.model.communication.Address;

public interface AddressAware {

    public Address getAddress();

    public void setAddress(Address address);

    public Address getSelectedAddress();
}
