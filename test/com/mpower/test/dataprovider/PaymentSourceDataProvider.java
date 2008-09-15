package com.mpower.test.dataprovider;

import org.testng.annotations.DataProvider;

import com.mpower.domain.PaymentSource;
import com.mpower.domain.Person;
import com.mpower.domain.Site;

public class PaymentSourceDataProvider {

    @DataProvider(name = "setupPaymentSource")
    public static Object[][] createPaymentSource() {
        Site site1 = new Site();
        site1.setName("setupPaymentSourceSite-1");

        Person person1 = new Person();
        person1.setFirstName("createPaymentSourceFirstName-1");
        person1.setLastName("createPaymentSourceLastName-1");

        PaymentSource ps1 = new PaymentSource();
        ps1.setName("createPaymentSource-1");

        Site site2 = new Site();
        site2.setName("setupPaymentSourceSite-2");

        Person person2 = new Person();
        person2.setFirstName("createPaymentSourceFirstName-2");
        person2.setLastName("createPaymentSourceLastName-2");

        PaymentSource ps2 = new PaymentSource();
        ps2.setName("createPaymentSource-2");

        Site site3 = new Site();
        site3.setName("setupPaymentSourceSite-3");

        Person person3 = new Person();
        person3.setFirstName("createPaymentSourceFirstName-3");
        person3.setLastName("createPaymentSourceLastName-3");

        PaymentSource ps3 = new PaymentSource();
        ps2.setName("createPaymentSource-3");

        return new Object[][] { new Object[] { site1, person1, ps1 }, new Object[] { site2, person2, ps2 }, new Object[] { site3, person3, ps3 } };
    }
}
