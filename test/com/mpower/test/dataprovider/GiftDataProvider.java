package com.mpower.test.dataprovider;

import java.math.BigDecimal;

import org.testng.annotations.DataProvider;

import com.mpower.entity.Gift;
import com.mpower.entity.Person;
import com.mpower.entity.Site;

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
        gift.setValue(BigDecimal.valueOf(1000L));

        return new Object[][] { new Object[] { site, person, gift } };
    }
}
