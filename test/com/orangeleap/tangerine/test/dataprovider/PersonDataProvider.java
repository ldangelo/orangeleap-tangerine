package com.orangeleap.tangerine.test.dataprovider;

import org.testng.annotations.DataProvider;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.domain.customization.CustomField;

public class PersonDataProvider {

    @DataProvider(name = "setupCreateSite")
    public static Object[][] setupCreateSite() {
        Site site = new Site();
        site.setName("setupCreateSite");

        return new Object[][] { new Object[] { site } };
    }

    @DataProvider(name = "setupCreatePerson")
    public static Object[][] setupCreatePerson() {
        Site site = new Site();
        site.setName("setupCreatePerson");

        Person person = new Person();
        person.setFirstName("firstname");
        person.setLastName("lastname");
        person.setSite(site);

        return new Object[][] { new Object[] { site, person } };
    }

    @DataProvider(name = "setupCreatePersonAndAddress")
    public static Object[][] setupCreatePersonAndAddress() {
        Site site = new Site();
        site.setName("setupCreatePersonAndAddress");

        Person person = new Person();
        person.setFirstName("firstname");
        person.setLastName("lastname");
        person.setSite(site);

        Address address1 = new Address(person.getId());
        address1.setAddressLine1("addressLine1");
        address1.setAddressLine2("addressLine2");
        address1.setAddressLine3("addressLine3");
        address1.setCity("city");
        address1.setCountry("US");
        address1.setPostalCode("postalCode");
        address1.setStateProvince("TX");

        Address address2 = new Address(person.getId());
        address2.setAddressLine1("addressLine1");
        address2.setAddressLine2("addressLine2");
        address2.setAddressLine3("addressLine3");
        address2.setCity("city");
        address2.setCountry("US");
        address2.setPostalCode("postalCode");
        address2.setStateProvince("TX");

        return new Object[][] { new Object[] { site, person, address1, address2 } };
    }

    @DataProvider(name = "setupCreatePersonAndPhone")
    public static Object[][] setupCreatePersonAndPhone() {
        Site site = new Site();
        site.setName("site 1");

        Person person = new Person();
        person.setFirstName("firstname");
        person.setLastName("lastname");
        person.setSite(site);

        Phone phone1 = new Phone(person.getId());
        phone1.setNumber("111-222-3333");

        Phone phone2 = new Phone(person.getId());
        phone2.setNumber("111-222-3333");

        return new Object[][] { new Object[] { site, person, phone1, phone2 } };
    }

    @DataProvider(name = "setupCreatePersonAndCustomField")
    public static Object[][] setupCreatePersonAndCustomField() {
        Site site = new Site();
        site.setName("site 1");

        Person person = new Person();
        person.setFirstName("firstname");
        person.setLastName("lastname");
        person.setSite(site);

        CustomField cf1 = new CustomField();
        cf1.setName("dog");
        cf1.setValue("barney");

        CustomField cf2 = new CustomField();
        cf2.setName("dog2");
        cf2.setValue("barney2");

        return new Object[][] { new Object[] { site, person, cf1, cf2 } };
    }
}
