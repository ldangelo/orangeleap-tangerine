package com.mpower.domain.util;

import java.util.ArrayList;
import java.util.List;

import com.mpower.entity.Person;
import com.mpower.entity.PersonPhone;
import com.mpower.entity.Phone;

@SuppressWarnings("unchecked")
public class PhoneMap extends AbstractInstanceValuesMap {

    private static final long serialVersionUID = 1L;

    private Person person;

    public static PhoneMap buildPhoneMap(List<PersonPhone> personPhoneList, Person person) {
        List<Phone> phoneList = new ArrayList<Phone>();
        for (PersonPhone pp : personPhoneList) {
            phoneList.add(pp.getPhone());
        }
        PhoneMap pm = new PhoneMap();
        pm.initialize(phoneList);
        pm.person = person;
        return pm;
    }

    @Override
    public Object createObject(Object key) {
        Phone newPhone = new Phone();
        newPhone.setPhoneType((String) key);
        person.getPersonPhones().add(new PersonPhone(person, newPhone));
        return newPhone;
    }

    @Override
    public Object getKeyForItem(Object o) {
        Phone phone = (Phone) o;
        return phone.getPhoneType();
    }
}
