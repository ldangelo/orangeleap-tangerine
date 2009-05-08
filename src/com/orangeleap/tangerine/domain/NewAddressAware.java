package com.orangeleap.tangerine.domain;

import com.orangeleap.tangerine.domain.communication.Address;

public interface NewAddressAware extends AddressAware {

    public Address getAddress();

    public void setAddress(Address address);

}
