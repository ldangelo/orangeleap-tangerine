package com.mpower.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mpower.domain.Person;
import com.mpower.domain.Site;
import com.mpower.service.PersonService;
import com.mpower.test.dataprovider.PersonDataProvider;

public class PersonTest extends BaseTest {

    private PersonService personService;

    @Test(dataProvider = "setupCreatePerson", dataProviderClass = PersonDataProvider.class)
    public void searchPerson(Site site, Person person) {
        em.getTransaction().begin();
        em.persist(site);
        em.persist(person);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("lastName", "last");
        List<Person> persons = personService.readPersons(site.getName(), params);
        assert persons.size() == 1;
        em.remove(person);
        em.remove(site);
        em.getTransaction().rollback();
    }

    @Test(dataProvider = "setupCreateSite", dataProviderClass = PersonDataProvider.class)
    public void searchPerson(Site site) {
        em.getTransaction().begin();
        em.persist(site);
        String siteName = site.getName();
        Person person = personService.createDefaultPerson(site.getName());
        person.setSite(site);
        em.persist(person);
        Long personId = person.getId();
        assert em.find(Person.class, personId) != null;
        assert em.find(Person.class, personId).getSite().getName().equals(siteName);
        em.remove(person);
        em.remove(site);
        em.getTransaction().rollback();
    }

    @BeforeClass
    public void setup() {
        getEntityManager();
        personService = (PersonService) applicationContext.getBean("personService");
    }
}
