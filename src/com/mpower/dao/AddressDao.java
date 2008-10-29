package com.mpower.dao;

import java.util.List;

import com.mpower.domain.Address;

public interface AddressDao {

    public Address maintainAddress(Address address);

    public List<Address> readAddresses(Long personId);

    public void deleteAddress(Address Address);

    // public List<Address> readActiveAddresses(Long personId);

    // public void inactivateAddress(Long AddressId);

    // public void removeAddress(Long AddressId);

    public Address readAddress(Long AddressId);
}
