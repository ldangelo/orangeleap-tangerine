package com.orangeleap.tangerine.test.dataprovider;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import org.testng.annotations.DataProvider;

import java.math.BigDecimal;

public class GiftDataProvider {

    @DataProvider(name = "setupGift")
    public static Object[][] createGift() {
        Site site = new Site();
        site.setName("company1");
        site.setMerchantNumber("700000007668");
        site.setMerchantBin("000002");
        
        Constituent constituent = new Constituent();
        constituent.setFirstName("firstname");
        constituent.setLastName("lastname");
        constituent.setSite(site);

        PaymentSource paymentSource = new PaymentSource();
        paymentSource.setCreditCardExpirationMonth(11);
        paymentSource.setCreditCardExpirationYear(2010);
        paymentSource.setCreditCardHolderName("John Q. Test");
        paymentSource.setCreditCardNumber("4111111111111111");
        paymentSource.setPaymentType("CREDIT_CARD");
        
        Gift gift = new Gift();
        gift.setComments("My gift to you");
        gift.setPaymentType("Cash");
        gift.setAmount(BigDecimal.valueOf(1000L));
        gift.setConstituent(constituent);
        gift.setId(100L);
        gift.setPaymentSource(paymentSource);
        return new Object[][] { new Object[] { site, constituent, gift } };
    }
}
