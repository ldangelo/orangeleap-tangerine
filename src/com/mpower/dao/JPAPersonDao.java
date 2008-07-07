package com.mpower.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.springframework.stereotype.Repository;

import com.mpower.domain.Person;
import com.mpower.domain.PersonPhone;
import com.mpower.util.EntityUtility;

@Repository("personDao")
public class JPAPersonDao implements PersonDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Person savePerson(Person person) {
        for (Iterator<PersonPhone> iter = person.getPersonPhones().iterator(); iter.hasNext();) {
            PersonPhone personPhone = iter.next();
            if (personPhone == null || personPhone.getPhone() == null || StringUtils.stripToNull(personPhone.getPhone().getNumber()) == null) {
                iter.remove();
            }
        }
        if (person.getId() == null) {
            em.persist(person);
            return person;
        }
        return em.merge(person);
    }

    @Override
    public Person readPerson(Long id) {
        return em.find(Person.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Person> readPersons(Long siteId, Map<String, String> params) {
        boolean whereUsed = true;
        StringBuilder queryString = new StringBuilder("SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteId");
        Map<String, String> addressParams = new HashMap<String, String>();
        Map<String, String> phoneParams = new HashMap<String, String>();
        LinkedHashMap<String, Object> parameterMap = new LinkedHashMap<String, Object>();
        if (params != null) {
            String key;
            String value;
            for (Map.Entry<String, String> pair : params.entrySet()) {
                key = pair.getKey();
                value = pair.getValue();
                if (!GenericValidator.isBlankOrNull(value)) {
                    if (key.startsWith("addressMap[")) {
                        addressParams.put(key.substring(key.indexOf('.') + 1), value);
                    } else if (key.startsWith("phoneMap[")) {
                        addressParams.put(key.substring(key.indexOf('.' + 1)), value);
                    } else {
                        whereUsed = EntityUtility.addWhereOrAnd(whereUsed, queryString);
                        queryString.append(" person.");
                        queryString.append(key);
                        queryString.append(" LIKE :");
                        queryString.append(key);
                        parameterMap.put(key, "%" + value + "%");
                    }
                }
            }
        }
        if (!addressParams.isEmpty()) {
            whereUsed = EntityUtility.addWhereOrAnd(whereUsed, queryString);
            queryString.append(getAddressString(addressParams, parameterMap));
        }
        if (!phoneParams.isEmpty()) {
            whereUsed = EntityUtility.addWhereOrAnd(whereUsed, queryString);
            queryString.append(getPhoneString(phoneParams, parameterMap));
        }
        Query query = em.createQuery(queryString.toString());
        query.setParameter("siteId", siteId);
        for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Person matchSpouseLogically(String firstName, String lastName) {
        Query query = em.createNamedQuery("READ_PERSON_BY_FIRST_AND_LAST");
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        List<Person> results = query.getResultList();
        if (results != null && !results.isEmpty()) {
            if (results.size() == 1) {
                return results.get(0);
            } else {

            }
        }
        return null;
    }

    private StringBuilder getAddressString(Map<String, String> addressParams, LinkedHashMap<String, Object> parameterMap) {
        StringBuilder addressString = new StringBuilder(" EXISTS ( SELECT personAddress FROM com.mpower.domain.PersonAddress personAddress WHERE personAddress.person.id = person.id ");
        for (Map.Entry<String, String> pair : addressParams.entrySet()) {
            String key = pair.getKey();
            String value = pair.getValue();
            addressString.append("AND personAddress.address.");
            addressString.append(key);
            addressString.append(" LIKE :");
            addressString.append(key);
            parameterMap.put(key, "%" + value + "%");
        }
        addressString.append(")");
        return addressString;
    }

    private StringBuilder getPhoneString(Map<String, String> phoneParams, LinkedHashMap<String, Object> parameterMap) {
        StringBuilder phoneString = new StringBuilder(" EXISTS ( SELECT personPhone FROM com.mpower.domain.PersonPhone personPhone WHERE personPhone.person.id = person.id ");
        for (Map.Entry<String, String> pair : phoneParams.entrySet()) {
            String key = pair.getKey();
            String value = pair.getValue();
            phoneString.append("AND personPhone.phone.");
            phoneString.append(key);
            phoneString.append(" LIKE :");
            phoneString.append(key);
            parameterMap.put(key, "%" + value + "%");
        }
        phoneString.append(")");
        return phoneString;
    }
}
