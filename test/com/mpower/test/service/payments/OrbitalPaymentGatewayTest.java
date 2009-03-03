package com.mpower.test.service.payments;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mpower.domain.model.Person;
import com.mpower.domain.model.Site;
import com.mpower.domain.model.paymentInfo.Gift;
import com.mpower.service.payments.OrbitalPaymentGateway;
import com.mpower.test.BaseTest;
import com.mpower.test.dataprovider.GiftDataProvider;


public class OrbitalPaymentGatewayTest extends BaseTest {
	

	 private OrbitalPaymentGateway paymentGateway;
	 
	 @Override
    @BeforeClass
	 public void setup() {
		 paymentGateway = new OrbitalPaymentGateway();
		 paymentGateway.setConfigFile(System.getProperty("testConfigDir") + "linehandler.properties");
	 }
	 
	 @Test(groups = { "authorizeTest" }, dataProvider = "setupGift", dataProviderClass = GiftDataProvider.class)
	 public void authorizeTest(Site site, Person person, Gift gift)
	 {
		 paymentGateway.Authorize(gift);
		 assert gift.getIsAuthorized() == true; 
	 }
	 
	 @Test(groups = { "captureTest" }, dataProvider = "setupGift", dataProviderClass = GiftDataProvider.class)
	 public void captureTest(Site site, Person person, Gift gift)
	 {
		 paymentGateway.Authorize(gift);
		 paymentGateway.Capture(gift);
		 assert gift.getIsCaptured() == true;
	 }
	 
	 @Test(groups = { "authorizeAndcaptureTest" }, dataProvider = "setupGift", dataProviderClass = GiftDataProvider.class)	 
	 public void authorizeAndCaptureTest(Site site, Person person, Gift gift)
	 {
		 paymentGateway.AuthorizeAndCapture(gift);
		 assert gift.getIsAuthorized() == true;
		 assert gift.getIsCaptured() == true;
	 }
}
