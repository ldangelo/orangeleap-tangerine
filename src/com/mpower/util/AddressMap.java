package com.mpower.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.Address;
import com.mpower.domain.Person;
import com.mpower.domain.PersonAddress;

public class AddressMap extends AbstractInstanceValuesMap {
	
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());


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
