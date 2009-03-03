package com.mpower.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.Email;
import com.mpower.domain.Person;

@Deprecated
public class EmailMap extends AbstractInstanceValuesMap {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private static final long serialVersionUID = 1L;

    private Person person;

    public static EmailMap buildEmailMap(List<Email> emailList, Person person) {
        EmailMap pm = new EmailMap();
        pm.initialize(emailList);
        pm.person = person;
        return pm;
    }

    @Override
    public Object createObject(Object key) {
        Email newEmail = new Email(person);
        newEmail.setEmailType((String) key);
        person.getEmails().add(newEmail);
        return newEmail;
    }

    @Override
    public Object getKeyForItem(Object o) {
        Email email = (Email) o;
        return email.getEmailType();
    }
}
