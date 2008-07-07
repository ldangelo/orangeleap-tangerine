package com.mpower.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mpower.domain.Person;
import com.mpower.domain.Site;
import com.mpower.service.PersonService;
import com.mpower.test.dataprovider.PersonDataProvider;

public class PersonTest extends BaseTest {

    private EntityManagerFactory emf;

    private PersonService personService;

    @Test(dataProvider = "setupCreatePerson", dataProviderClass = PersonDataProvider.class)
    public void searchPerson(Site site, Person person) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(site);
        em.persist(person);
        em.getTransaction().commit();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("lastName", "last");
        List<Person> persons = personService.readPersons(site.getId(), params);
        assert persons.size() == 1;
        em.remove(person);
        em.remove(site);
    }

    @Test(dataProvider = "setupCreateSite", dataProviderClass = PersonDataProvider.class)
    public void searchPerson(Site site) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(site);
        Long siteId = site.getId();
        Person person = personService.createDefaultPerson(site.getId());
        em.persist(person);
        Long personId = person.getId();
        em.getTransaction().commit();
        assert em.find(Person.class, personId) != null;
        assert em.find(Person.class, personId).getSite().getId() == siteId;
        em.remove(person);
        em.remove(site);
    }

    @BeforeClass
    public void setup() {
        emf = (EntityManagerFactory) applicationContext.getBean("entityManagerFactory");
        personService = (PersonService) applicationContext.getBean("personService");
    }
}
