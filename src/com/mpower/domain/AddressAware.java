package com.mpower.domain;

@Deprecated
public interface AddressAware {

    public Address getAddress();

    public void setAddress(Address address);

    public Address getSelectedAddress();
}
