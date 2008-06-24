package com.mpower.util;

import java.util.ArrayList;
import java.util.List;

import com.mpower.entity.Address;
import com.mpower.entity.Person;
import com.mpower.entity.PersonAddress;

@SuppressWarnings("unchecked")
public class AddressMap extends AbstractInstanceValuesMap {

    private static final long serialVersionUID = 1L;

    private Person person;

    public static AddressMap buildAddressMap(List<PersonAddress> personAddressList, Person person) {
        List<Address> addressList = new ArrayList<Address>();
        for (PersonAddress pa : personAddressList) {
            addressList.add(pa.getAddress());
        }
        AddressMap addressMap = new AddressMap();
        addressMap.initialize(addressList);
        addressMap.person = person;
        return addressMap;
    }

    @Override
    public Object createObject(Object key) {
        Address newAddress = new Address();
        newAddress.setAddressType((String) key);
        person.getPersonAddresses().add(new PersonAddress(person, newAddress));
        return newAddress;
    }

    @Override
    public Object getKeyForItem(Object o) {
        Address addr = (Address) o;
        return addr.getAddressType();
    }
}
