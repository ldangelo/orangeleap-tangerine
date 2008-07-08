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

import com.mpower.dao.util.QueryUtil;
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
    public List<Person> readPersons(Long siteId, Map<String, Object> params) {
        boolean whereUsed = true;
        StringBuilder queryString = new StringBuilder("SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteId");
        Map<String, Object> addressParams = new HashMap<String, Object>();
        Map<String, Object> phoneParams = new HashMap<String, Object>();
        Map<String, String> customParams = new HashMap<String, String>();
        LinkedHashMap<String, Object> parameterMap = new LinkedHashMap<String, Object>();
        if (params != null) {
            for (Map.Entry<String, Object> pair : params.entrySet()) {
                String key = pair.getKey();
                Object value = pair.getValue();
                boolean isString = true;
                if (value instanceof String) {
                    if (GenericValidator.isBlankOrNull((String) value)) {
                        continue;
                    }
                } else {
                    if (value == null) {
                        continue;
                    }
                    isString = false;
                }
                if (key.startsWith("addressMap[")) {
                    addressParams.put(key.substring(key.indexOf('.') + 1), value);
                } else if (key.startsWith("phoneMap[")) {
                    phoneParams.put(key.substring(key.indexOf('.') + 1), value);
                } else if (key.startsWith("customFieldMap[")) {
                    customParams.put(key.substring(key.indexOf('[') + 1, key.indexOf(']')), (String) value);
                } else {
                    whereUsed = EntityUtility.addWhereOrAnd(whereUsed, queryString);
                    queryString.append(" person.");
                    queryString.append(key);
                    queryString.append(" LIKE :");
                    String paramName = key.replace(".", "_");
                    queryString.append(paramName);
                    if (isString) {
                        parameterMap.put(paramName, "%" + value + "%");
                    } else {
                        parameterMap.put(paramName, value);
                    }
                }
            }
        }
        queryString.append(QueryUtil.getAddressString(addressParams, parameterMap));
        queryString.append(QueryUtil.getPhoneString(phoneParams, parameterMap));
        queryString.append(QueryUtil.getCustomString(customParams, parameterMap));

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
}
