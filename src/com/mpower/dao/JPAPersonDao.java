package com.mpower.dao;

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

import com.mpower.entity.Person;
import com.mpower.entity.PersonPhone;
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
        return (Person) em.find(Person.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Person> readPersons(Long siteId, Map<String, String> params) {
        boolean whereUsed = true;
        StringBuffer queryString = new StringBuffer("SELECT person FROM com.mpower.entity.Person person WHERE person.site.id = :siteId");
        LinkedHashMap<String, Object> parameterMap = new LinkedHashMap<String, Object>();
        if (params != null) {
            String key;
            String value;
            for (Map.Entry<String, String> pair : params.entrySet()) {
                key = pair.getKey();
                value = pair.getValue();
                if (!GenericValidator.isBlankOrNull(value)) {
                    whereUsed = EntityUtility.addWhereOrAnd(whereUsed, queryString);
                    queryString.append(" person.");
                    queryString.append(key);
                    queryString.append(" LIKE :");
                    queryString.append(key);
                    parameterMap.put(key, value + "%");
                }
            }
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
}
