package com.orangeleap.tangerine.test.dataprovider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.type.ActivationType;
import com.orangeleap.tangerine.util.CalendarUtils;

public class AddressDataProvider {

    @DataProvider(name = "setupAddresses")
    public static Object[][] createAddresses() {
        Site site1 = new Site();
        site1.setName("setupAddressSite-1");

        Person person1 = new Person();
        person1.setFirstName("createAddressFirstName-1");
        person1.setLastName("createAddressLastName-1");

        List<Address> addresses = new ArrayList<Address>();
        Address address = new Address(person1.getId());
        address.setAddressLine1("1-permanent-addressLine1");
        address.setCity("city");
        address.setCountry("US");
        address.setPostalCode("11111");
        address.setStateProvince("state");
        address.setActivationStatus(ActivationType.permanent);
        addresses.add(address);

        address = new Address(person1.getId());
        address.setAddressLine1("2-permanent-addressLine1");
        address.setCity("city");
        address.setCountry("US");
        address.setPostalCode("11111");
        address.setStateProvince("state");
        address.setActivationStatus(ActivationType.permanent);
        addresses.add(address);

        address = new Address(person1.getId());
        address.setAddressLine1("3-permanent-addressLine1");
        address.setCity("city");
        address.setCountry("US");
        address.setPostalCode("11111");
        address.setStateProvince("state");
        address.setActivationStatus(ActivationType.permanent);
        addresses.add(address);

        // set seasonals to be 10/1 - 3/31
        address = new Address(person1.getId());
        address.setAddressLine1("1-seasonal-addressLine1");
        address.setCity("city");
        address.setCountry("US");
        address.setPostalCode("11111");
        address.setStateProvince("state");
        address.setActivationStatus(ActivationType.seasonal);
        Calendar today = CalendarUtils.getToday(false);
        Calendar seasonStart = new GregorianCalendar(today.get(Calendar.YEAR) - 1, 9, 1);
        address.setSeasonalStartDate(seasonStart.getTime());
        Calendar seasonEnd = new GregorianCalendar(today.get(Calendar.YEAR), 2, 31);
        address.setSeasonalEndDate(seasonEnd.getTime());
        addresses.add(address);

        address = new Address(person1.getId());
        address.setAddressLine1("2-seasonal-addressLine1");
        address.setCity("city");
        address.setCountry("US");
        address.setPostalCode("11111");
        address.setStateProvince("state");
        address.setActivationStatus(ActivationType.seasonal);
        address.setSeasonalStartDate(seasonStart.getTime());
        address.setSeasonalEndDate(seasonEnd.getTime());
        addresses.add(address);

        address = new Address(person1.getId());
        address.setAddressLine1("3-seasonal-addressLine1");
        address.setCity("city");
        address.setCountry("US");
        address.setPostalCode("11111");
        address.setStateProvince("state");
        address.setActivationStatus(ActivationType.seasonal);
        address.setSeasonalStartDate(seasonStart.getTime());
        address.setSeasonalEndDate(seasonEnd.getTime());
        addresses.add(address);

        // set temporary addresses for 10/15/<today's year> - 11/14/<today's year>
        address = new Address(person1.getId());
        address.setAddressLine1("1-temporary-addressLine1");
        address.setCity("city");
        address.setCountry("US");
        address.setPostalCode("11111");
        address.setStateProvince("state");
        address.setActivationStatus(ActivationType.temporary);
        Calendar tempStart = CalendarUtils.getToday(false);
        tempStart.set(tempStart.get(Calendar.YEAR), 9, 15);
        address.setTemporaryStartDate(tempStart.getTime());
        Calendar tempEnd = CalendarUtils.getToday(false);
        tempEnd.set(tempEnd.get(Calendar.YEAR), 10, 14);
        address.setTemporaryEndDate(tempEnd.getTime());
        addresses.add(address);

        address = new Address(person1.getId());
        address.setAddressLine1("2-temporary-addressLine1");
        address.setCity("city");
        address.setCountry("US");
        address.setPostalCode("11111");
        address.setStateProvince("state");
        address.setActivationStatus(ActivationType.temporary);
        address.setTemporaryStartDate(tempStart.getTime());
        address.setTemporaryEndDate(tempEnd.getTime());
        addresses.add(address);

        address = new Address(person1.getId());
        address.setAddressLine1("3-temporary-addressLine1");
        address.setCity("city");
        address.setCountry("US");
        address.setPostalCode("11111");
        address.setStateProvince("state");
        address.setActivationStatus(ActivationType.temporary);
        address.setTemporaryStartDate(tempStart.getTime());
        address.setTemporaryEndDate(tempEnd.getTime());
        addresses.add(address);

        return new Object[][] { new Object[] { site1, person1, addresses } };
    }
}
