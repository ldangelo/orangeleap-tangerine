package com.mpower.dao;

import java.util.Calendar;
import java.util.List;

import com.mpower.domain.Address;

public interface AddressDao {

    public Address maintainAddress(Address address);

    public List<Address> readAddresses(Long personId);

    public void deleteAddress(Address address);

    public Address readAddress(Long addressId);

    public List<Address> readCurrentAddresses(Long personId, Calendar calendar, boolean mailOnly);

    public void inactivateAddresses();
}
