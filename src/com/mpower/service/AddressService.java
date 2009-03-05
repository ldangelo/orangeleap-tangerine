package com.mpower.service;

import java.util.List;

import com.mpower.domain.model.communication.Address;

public interface AddressService {

    public Address saveAddress(Address address);

    public List<Address> readAddressesByConstituentId(Long constituentId);

    public List<Address> filterValidAddresses(Long constituentId);

    public Address readAddress(Long addressId);

    public List<Address> readCurrentAddresses(Long constituentId, boolean receiveCorrespondence);
}
