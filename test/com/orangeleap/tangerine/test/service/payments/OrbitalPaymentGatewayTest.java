package com.orangeleap.tangerine.test.service.payments;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.payments.OrbitalPaymentGateway;
import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.test.dataprovider.GiftDataProvider;


public class OrbitalPaymentGatewayTest extends BaseTest {
	

	 private OrbitalPaymentGateway paymentGateway;

    // Don't fail build if gateway is down.
    private static String GATEWAY_UNAVAILABLE = "Gateway Resources Unavailable";
	 
    @BeforeClass
	 public void setup() {
		 paymentGateway = (OrbitalPaymentGateway) applicationContext.getBean("paymentGateway");
//		 paymentGateway.setConfigFile("/usr/local/paymentech/config/linehandler.properties");
	 }
	 
	 @Test(groups = { "authorizeTest" }, dataProvider = "setupGift", dataProviderClass = GiftDataProvider.class)
	 public void authorizeTest(Site site, Constituent constituent, Gift gift)
	 {
		 paymentGateway.Authorize(gift);

         if (!gift.getIsAuthorized()) {
             logger.error(gift.getPaymentMessage());
             if (gift.getPaymentMessage().contains(GATEWAY_UNAVAILABLE)) return;
         }
		 assert gift.getIsAuthorized() == true;
	 }
	 
	 @Test(groups = { "captureTest" }, dataProvider = "setupGift", dataProviderClass = GiftDataProvider.class)
	 public void captureTest(Site site, Constituent constituent, Gift gift)
	 {
		 paymentGateway.Authorize(gift);
		 paymentGateway.Capture(gift);

         if (!gift.getIsCaptured()) {
             logger.error(gift.getPaymentMessage());
             if (gift.getPaymentMessage().contains(GATEWAY_UNAVAILABLE)) return;
         }
		 assert gift.getIsCaptured() == true;
	 }
	 
	 @Test(groups = { "authorizeAndcaptureTest" }, dataProvider = "setupGift", dataProviderClass = GiftDataProvider.class)	 
	 public void authorizeAndCaptureTest(Site site, Constituent constituent, Gift gift)
	 {
		 paymentGateway.AuthorizeAndCapture(gift);

         if (!gift.getIsAuthorized() || !gift.getIsCaptured()) {
             logger.error(gift.getPaymentMessage());
             if (gift.getPaymentMessage().contains(GATEWAY_UNAVAILABLE)) return;
         }
		 assert gift.getIsAuthorized() == true;
		 assert gift.getIsCaptured() == true;
	 }
	 
}
