package com.mpower.test.dataprovider;

import java.math.BigDecimal;

import org.testng.annotations.DataProvider;

import com.mpower.domain.Gift;
import com.mpower.domain.Person;
import com.mpower.domain.Site;

public class GiftDataProvider {

    @DataProvider(name = "setupGift")
    public static Object[][] createGift() {
        Site site = new Site();
        site.setName("site 1");

        Person person = new Person();
        person.setFirstName("firstname");
        person.setLastName("lastname");

        Gift gift = new Gift();
        gift.setComments("My gift to you");
        gift.setPaymentType("Cash");
        gift.setAmount(BigDecimal.valueOf(1000L));

        return new Object[][] { new Object[] { site, person, gift } };
    }
}
