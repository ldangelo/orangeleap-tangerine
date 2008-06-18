package com.mpower.test.dataprovider;

import java.math.BigDecimal;

import org.testng.annotations.DataProvider;

import com.mpower.domain.entity.Gift;
import com.mpower.domain.entity.PaymentSource;
import com.mpower.domain.entity.Person;
import com.mpower.domain.entity.Site;

public class GiftDataProvider {

    @DataProvider(name = "setupGift")
    public static Object[][] createGift() {
        Site site = new Site();
        site.setName("site 1");

        Person person = new Person();
        person.setFirstName("firstname");
        person.setLastName("lastname");
        person.setSite(site);

        Gift gift = new Gift();
        gift.setValue(BigDecimal.valueOf(1000L));

        PaymentSource paymentSource = new PaymentSource();
        paymentSource.setPaymentType("Cash");
        return new Object[][] { new Object[] { site, person, gift, paymentSource } };
    }
}
