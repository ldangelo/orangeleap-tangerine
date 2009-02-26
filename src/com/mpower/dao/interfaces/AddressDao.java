package com.mpower.dao.interfaces;

import java.util.List;

import com.mpower.domain.model.communication.Address;

public interface AddressDao {

    public Address maintainAddress(Address address);

    public List<Address> readAddressesByConstituentId(Long personId);

    public Address readAddressById(Long addressId);

    public List<Address> readActiveAddressesByConstituentId(Long constituentId);
//    public List<Address> readCurrentAddresses(Long personId, Calendar calendar, boolean mailOnly);

    public void inactivateAddresses();
}
