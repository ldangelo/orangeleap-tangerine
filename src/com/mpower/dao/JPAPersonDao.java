package com.mpower.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.validator.GenericValidator;
import org.springframework.stereotype.Repository;

import com.mpower.domain.entity.Person;

@Repository("personDao")
public class JPAPersonDao implements PersonDao {

	@PersistenceContext
    private EntityManager em;

	@Override
	public Person savePerson(Person person) {
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
        StringBuffer queryString = new StringBuffer("SELECT person FROM com.mpower.domain.entity.Person person WHERE person.site.id = :siteId");
        LinkedHashMap<String, Object> parameterMap = new LinkedHashMap<String, Object>();
        if (params != null) {
            String key;
            String value;
            for (Map.Entry<String, String> pair : params.entrySet()) {
                key = pair.getKey();
                value = pair.getValue();
                if (!GenericValidator.isBlankOrNull(value)) {
                    whereUsed = addWhereOrAnd(whereUsed, queryString);
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

    public static boolean addWhereOrAnd(boolean whereUsed, StringBuffer queryString) {
        if (whereUsed) {
            queryString.append(" AND");
            return whereUsed;
        }
        queryString.append(" WHERE");
        return true;
    }
}
