package com.mpower.service;

import java.util.List;

import com.mpower.domain.Address;

public interface AddressService {

    public Address saveAddress(Address address);

    public List<Address> readAddresses(Long personId);

    // public List<Address> readActiveAddresss(Long personId);
    //
    public void setAuditService(AuditService auditService);

    // public void removeAddress(Long addressId);

    // public void inactivateAddress(Long addressId);

    public void deleteAddress(Address address);

    public Address readAddress(Long addressId);
}
