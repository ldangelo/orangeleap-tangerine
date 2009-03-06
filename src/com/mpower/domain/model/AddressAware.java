package com.mpower.domain.model;

import com.mpower.domain.model.communication.Address;
import com.mpower.type.FormBeanType;

public interface AddressAware {

    public Address getAddress();

    public void setAddress(Address address);

    public Address getSelectedAddress();

    public void setSelectedAddress(Address address);
    
    public void setAddressType(FormBeanType type);
    
    public FormBeanType getAddressType();
}
