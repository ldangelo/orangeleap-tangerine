package com.orangeleap.tangerine.domain;

import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.type.FormBeanType;

public interface AddressAware {

    public Address getAddress();

    public void setAddress(Address address);

    public Address getSelectedAddress();

    public void setSelectedAddress(Address address);
    
    public void setAddressType(FormBeanType type);
    
    public FormBeanType getAddressType();
}
