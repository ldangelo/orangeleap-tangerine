package com.mpower.test.dataprovider;

import org.testng.annotations.DataProvider;

import com.mpower.domain.entity.Address;
import com.mpower.domain.entity.CustomField;
import com.mpower.domain.entity.Person;
import com.mpower.domain.entity.Phone;
import com.mpower.domain.entity.Site;

public class PersonDataProvider {

    @DataProvider(name = "setupCreateSite")
    public static Object[][] setupCreateSite() {
        Site site = new Site();
        site.setName("site 1");

        return new Object[][] { new Object[] { site } };
    }

    @DataProvider(name = "setupCreatePerson")
    public static Object[][] setupCreatePerson() {
        Site site = new Site();
        site.setName("site 1");

        Person person = new Person();
        person.setFirstName("firstname");
        person.setLastName("lastname");
        person.setSite(site);

        return new Object[][] { new Object[] { site, person } };
    }

    @DataProvider(name = "setupCreatePersonAndAddress")
    public static Object[][] setupCreatePersonAndAddress() {
        Site site = new Site();
        site.setName("site 1");

        Person person = new Person();
        person.setFirstName("firstname");
        person.setLastName("lastname");
        person.setSite(site);

        Address address1 = new Address();
        address1.setAddressLine1("addressLine1");
        address1.setAddressLine2("addressLine2");
        address1.setAddressLine3("addressLine3");
        address1.setAddressType("primaryAddress");
        address1.setCity("city");
        address1.setCountry("US");
        address1.setPostalCode("postalCode");
        address1.setStateProvince("TX");

        Address address2 = new Address();
        address2.setAddressLine1("addressLine1");
        address2.setAddressLine2("addressLine2");
        address2.setAddressLine3("addressLine3");
        address2.setAddressType("secondaryAddress");
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

        Phone phone1 = new Phone();
        phone1.setNumber("111-222-3333");
        phone1.setPhoneType("homePhone");

        Phone phone2 = new Phone();
        phone2.setNumber("111-222-3333");
        phone2.setPhoneType("workPhone");

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
