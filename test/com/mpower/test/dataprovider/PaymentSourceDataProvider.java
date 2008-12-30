package com.mpower.test.dataprovider;

import org.testng.annotations.DataProvider;

import com.mpower.controller.validator.PaymentSourceValidator;
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
        ps1.setType(PaymentSourceValidator.ACH);

        Site site2 = new Site();
        site2.setName("setupPaymentSourceSite-2");

        Person person2 = new Person();
        person2.setFirstName("createPaymentSourceFirstName-2");
        person2.setLastName("createPaymentSourceLastName-2");

        PaymentSource ps2 = new PaymentSource();
        ps2.setType(PaymentSourceValidator.ACH);

        Site site3 = new Site();
        site3.setName("setupPaymentSourceSite-3");

        Person person3 = new Person();
        person3.setFirstName("createPaymentSourceFirstName-3");
        person3.setLastName("createPaymentSourceLastName-3");

        PaymentSource ps3 = new PaymentSource();
        ps3.setType(PaymentSourceValidator.ACH);

        Site site4 = new Site();
        site4.setName("setupPaymentSourceSite-4");

        Person person4 = new Person();
        person4.setFirstName("createPaymentSourceFirstName-4");
        person4.setLastName("createPaymentSourceLastName-4");

        PaymentSource ps4 = new PaymentSource();
        ps4.setType(PaymentSourceValidator.CREDIT_CARD);
        ps4.setProfile("MyProfile");

        return new Object[][] { new Object[] { site1, person1, ps1 }, new Object[] { site2, person2, ps2 }, new Object[] { site3, person3, ps3 }, new Object[] { site4, person4, ps4 } };
    }
}
