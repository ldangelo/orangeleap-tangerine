package com.mpower.test.dataprovider;

import java.util.Arrays;
import java.util.Calendar;

import org.testng.annotations.DataProvider;

import com.mpower.domain.Address;
import com.mpower.domain.Person;
import com.mpower.domain.Site;
import com.mpower.util.CalendarUtils;

public class AddressDataProvider {

    @DataProvider(name = "setupAddresses")
    public static Object[][] createAddresses() {
        Site site1 = new Site();
        site1.setName("setupAddressSite-1");

        Person person1 = new Person();
        person1.setFirstName("createAddressFirstName-1");
        person1.setLastName("createAddressLastName-1");

        Address aPermanent1 = new Address();
        aPermanent1.setAddressLine1("1-permanent-addressLine1");
        aPermanent1.setCity("city");
        aPermanent1.setCountry("US");
        aPermanent1.setPostalCode("11111");
        aPermanent1.setStateProvince("state");
        aPermanent1.setActivationStatus("permanent");

        Address aPermanent2 = new Address();
        aPermanent2.setAddressLine1("2-permanent-addressLine1");
        aPermanent2.setCity("city");
        aPermanent2.setCountry("US");
        aPermanent2.setPostalCode("11111");
        aPermanent2.setStateProvince("state");
        aPermanent2.setActivationStatus("permanent");

        Address aPermanent3 = new Address();
        aPermanent3.setAddressLine1("3-permanent-addressLine1");
        aPermanent3.setCity("city");
        aPermanent3.setCountry("US");
        aPermanent3.setPostalCode("11111");
        aPermanent3.setStateProvince("state");
        aPermanent3.setActivationStatus("permanent");

        Address aSeasonal1 = new Address();
        aSeasonal1.setAddressLine1("1-seasonal-addressLine1");
        aSeasonal1.setCity("city");
        aSeasonal1.setCountry("US");
        aSeasonal1.setPostalCode("11111");
        aSeasonal1.setStateProvince("state");
        aSeasonal1.setActivationStatus("seasonal");
        Calendar seasonStart = CalendarUtils.getToday(false);
        seasonStart.set(0, seasonStart.get(Calendar.MONTH), seasonStart.get(Calendar.DAY_OF_MONTH));
        aSeasonal1.setSeasonalStartDate(seasonStart.getTime());
        Calendar seasonEnd = CalendarUtils.getToday(false);
        seasonEnd.set(0, seasonEnd.get(Calendar.MONTH), seasonEnd.get(Calendar.DAY_OF_MONTH));
        seasonEnd.add(Calendar.MONTH, 1);
        aSeasonal1.setSeasonalEndDate(seasonEnd.getTime());

        Address aSeasonal2 = new Address();
        aSeasonal2.setAddressLine1("2-seasonal-addressLine1");
        aSeasonal2.setCity("city");
        aSeasonal2.setCountry("US");
        aSeasonal2.setPostalCode("11111");
        aSeasonal2.setStateProvince("state");
        aSeasonal2.setActivationStatus("seasonal");
        aSeasonal2.setSeasonalStartDate(seasonStart.getTime());
        aSeasonal2.setSeasonalEndDate(seasonEnd.getTime());

        Address aSeasonal3 = new Address();
        aSeasonal3.setAddressLine1("3-seasonal-addressLine1");
        aSeasonal3.setCity("city");
        aSeasonal3.setCountry("US");
        aSeasonal3.setPostalCode("11111");
        aSeasonal3.setStateProvince("state");
        aSeasonal3.setActivationStatus("seasonal");
        aSeasonal3.setSeasonalStartDate(seasonStart.getTime());
        aSeasonal3.setSeasonalEndDate(seasonEnd.getTime());

        Address aTemporary1 = new Address();
        aTemporary1.setAddressLine1("1-temporary-addressLine1");
        aTemporary1.setCity("city");
        aTemporary1.setCountry("US");
        aTemporary1.setPostalCode("11111");
        aTemporary1.setStateProvince("state");
        aTemporary1.setActivationStatus("temporary");
        Calendar tempStart = CalendarUtils.getToday(false);
        aTemporary1.setTemporaryStartDate(tempStart.getTime());
        Calendar tempEnd = CalendarUtils.getToday(false);
        tempEnd.add(Calendar.WEEK_OF_MONTH, 1);
        aTemporary1.setTemporaryEndDate(tempEnd.getTime());

        Address aTemporary2 = new Address();
        aTemporary2.setAddressLine1("2-temporary-addressLine1");
        aTemporary2.setCity("city");
        aTemporary2.setCountry("US");
        aTemporary2.setPostalCode("11111");
        aTemporary2.setStateProvince("state");
        aTemporary2.setActivationStatus("temporary");
        aTemporary2.setTemporaryStartDate(tempStart.getTime());
        aTemporary2.setTemporaryEndDate(tempEnd.getTime());

        Address aTemporary3 = new Address();
        aTemporary3.setAddressLine1("3-temporary-addressLine1");
        aTemporary3.setCity("city");
        aTemporary3.setCountry("US");
        aTemporary3.setPostalCode("11111");
        aTemporary3.setStateProvince("state");
        aTemporary3.setActivationStatus("temporary");
        aTemporary3.setTemporaryStartDate(tempStart.getTime());
        aTemporary3.setTemporaryEndDate(tempEnd.getTime());

        return new Object[][] { new Object[] { site1, person1, Arrays.asList(new Address[] { aPermanent1, aPermanent2, aPermanent3, aSeasonal1, aSeasonal2, aSeasonal3, aTemporary1, aTemporary2, aTemporary3 }) } };
    }
}
