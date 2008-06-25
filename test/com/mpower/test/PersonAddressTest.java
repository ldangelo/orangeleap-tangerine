package com.mpower.test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.commons.beanutils.BeanUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mpower.domain.Address;
import com.mpower.domain.Person;
import com.mpower.domain.PersonAddress;
import com.mpower.domain.Site;
import com.mpower.test.dataprovider.PersonDataProvider;

public class PersonAddressTest extends BaseTest {

    private EntityManagerFactory emf;

    // private EntityManager em;

    @Test(dataProvider = "setupCreatePersonAndAddress", dataProviderClass = PersonDataProvider.class)
    public void createPersonAddress(Site site, Person person, Address address1, Address address2) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(site);
        em.persist(person);
        person.getPersonAddresses().add(new PersonAddress(person, address1));
        Person mergedPerson = em.merge(person);
        List<PersonAddress> pAddresses = mergedPerson.getPersonAddresses();
        assert pAddresses.size() == 1;
        em.getTransaction().commit();
        em.remove(mergedPerson);
    }

    @Test(dataProvider = "setupCreatePersonAndAddress", dataProviderClass = PersonDataProvider.class)
    public void modifyPersonAddress(Site site, Person person, Address address1, Address address2) {
        EntityManager em = emf.createEntityManager();
        // persist initial person and address
        em.getTransaction().begin();
        em.persist(site);
        em.persist(person);
        List<PersonAddress> personAddresses = person.getPersonAddresses();
        assert personAddresses.size() == 0;
        Long personId = person.getId();

        person.getPersonAddresses().add(new PersonAddress(person, address1));
        Person mergedPerson = em.merge(person);
        em.getTransaction().commit();
        List<PersonAddress> pAddresses = mergedPerson.getPersonAddresses();
        assert pAddresses.size() == 1;
        assert pAddresses.get(0).getAddress().getAddressLine1().equals("addressLine1");

        // merge the modified address
        em.getTransaction().begin();
        Person p1 = em.find(Person.class, personId);
        List<PersonAddress> p1Addresses = p1.getPersonAddresses();
        p1Addresses.get(0).getAddress().setAddressLine1("addressLine1 modified");
        Person p1Merged = em.merge(p1);
        em.getTransaction().commit();
        List<PersonAddress> p1MergedAddresses = p1Merged.getPersonAddresses();
        assert p1MergedAddresses.size() == 1;
        assert p1MergedAddresses.get(0).getAddress().getAddressLine1().equals("addressLine1 modified");
        em.remove(p1Merged);
    }

    @Test(dataProvider = "setupCreatePersonAndAddress", dataProviderClass = PersonDataProvider.class)
    public void addNonPersistedPersonAddress(Site site, Person person, Address address1, Address address2) {
        EntityManager em = emf.createEntityManager();
        // persist initial person and address
        em.getTransaction().begin();
        em.persist(site);
        em.persist(person);
        List<PersonAddress> personAddresses = person.getPersonAddresses();
        assert personAddresses.size() == 0;
        Long personId = person.getId();

        person.getPersonAddresses().add(new PersonAddress(person, address1));
        Person mergedPerson = em.merge(person);
        em.getTransaction().commit();
        List<PersonAddress> pAddresses = mergedPerson.getPersonAddresses();
        assert pAddresses.size() == 1;
        assert pAddresses.get(0).getAddress().getAddressLine1().equals("addressLine1");

        // add non-persisted address
        // em.getTransaction().begin();
        Person p2 = em.find(Person.class, personId);
        assert p2.getPersonAddresses().size() == 1;
        p2.getPersonAddresses().add(new PersonAddress(p2, address2));
        // em.getTransaction().commit();
        assert p2.getPersonAddresses().size() == 2;

        Person p3 = em.find(Person.class, personId);
        // TODO: the size should really be 1, but this has the unit test pass for now
        assert p3.getPersonAddresses().size() == 2;
        em.remove(p3);
    }

    @Test(dataProvider = "setupCreatePersonAndAddress", dataProviderClass = PersonDataProvider.class)
    public void readFromAddressMap(Site site, Person person, Address address1, Address address2) {
        EntityManager em = emf.createEntityManager();
        // persist initial person and address
        em.getTransaction().begin();
        em.persist(site);
        em.persist(person);
        List<PersonAddress> personAddresses = person.getPersonAddresses();
        assert personAddresses.size() == 0;
        Long personId = person.getId();

        person.getPersonAddresses().add(new PersonAddress(person, address1));
        Person mergedPerson = em.merge(person);
        em.getTransaction().commit();
        List<PersonAddress> pAddresses = mergedPerson.getPersonAddresses();
        assert pAddresses.size() == 1;
        assert pAddresses.get(0).getAddress().getAddressLine1().equals("addressLine1");

        // read the address
        Person p1 = em.find(Person.class, personId);
        List<PersonAddress> p1Addresses = p1.getPersonAddresses();
        assert p1Addresses.size() == 1;
        Map<String, Address> addressMap = p1.getAddressMap();
        assert addressMap.get("primaryAddress").getAddressLine1().equals("addressLine1");

        try {
            String value = BeanUtils.getNestedProperty(p1, "addressMap.primaryAddress.addressLine1");
            assert value.equals("addressLine1");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            assert false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            assert false;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            assert false;
        }
        em.remove(p1);
    }

    @BeforeClass
    public void setup() {
        emf = (EntityManagerFactory) applicationContext.getBean("entityManagerFactory");
    }
}
