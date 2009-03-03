package com.mpower.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.Person;
import com.mpower.domain.Phone;

@Deprecated
public class PhoneMap extends AbstractInstanceValuesMap {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private static final long serialVersionUID = 1L;

    private Person person;

    public static PhoneMap buildPhoneMap(List<Phone> phoneList, Person person) {
        PhoneMap pm = new PhoneMap();
        pm.initialize(phoneList);
        pm.person = person;
        return pm;
    }

    @Override
    public Object createObject(Object key) {
        Phone newPhone = new Phone(person);
        newPhone.setPhoneType((String) key);
        person.getPhones().add(newPhone);
        return newPhone;
    }

    @Override
    public Object getKeyForItem(Object o) {
        Phone phone = (Phone) o;
        return phone.getPhoneType();
    }
}
