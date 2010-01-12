package com.orangeleap.tangerine.test.ws;

import java.math.BigDecimal;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.test.MockRequestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.service.ws.OrangeLeapWSV2;
import com.orangeleap.tangerine.service.ws.exception.InvalidRequestException;
import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.ws.schema.Site;
import com.orangeleap.tangerine.ws.schema.v2.AddCommunicationHistoryRequest;
import com.orangeleap.tangerine.ws.schema.v2.Address;
import com.orangeleap.tangerine.ws.schema.v2.BulkAddCommunicationHistoryRequest;
import com.orangeleap.tangerine.ws.schema.v2.CommunicationHistory;
import com.orangeleap.tangerine.ws.schema.v2.Constituent;
import com.orangeleap.tangerine.ws.schema.v2.Email;
import com.orangeleap.tangerine.ws.schema.v2.FindConstituentsRequest;
import com.orangeleap.tangerine.ws.schema.v2.PaymentSource;
import com.orangeleap.tangerine.ws.schema.v2.PaymentType;
import com.orangeleap.tangerine.ws.schema.v2.SaveOrUpdateConstituentRequest;
import com.orangeleap.tangerine.ws.schema.v2.SaveOrUpdateConstituentResponse;
import com.orangeleap.tangerine.ws.schema.v2.SaveOrUpdateGiftRequest;
import com.orangeleap.tangerine.ws.schema.v2.SaveOrUpdateGiftResponse;
import com.orangeleap.theguru.client.FindConstituentsResponse;
import com.orangeleap.theguru.client.Gift;

public class WSTest extends BaseTest {
	private RequestContext mockRequestContext;

	@Autowired
	public OrangeLeapWSV2 constituentEndpointV2;

	@BeforeMethod
	public void setupMocks() {
		mockRequestContext = new MockRequestContext();
	}

	@Test(groups = "soapAPITests")
	void testAutowired() {
		Assert.assertNotNull(constituentEndpointV2);
	}

	@Test(groups = "soapAPITests")
	void testAddConstituent() {
		try {
			SaveOrUpdateConstituentRequest request = new SaveOrUpdateConstituentRequest();
			SaveOrUpdateConstituentResponse response = null;

			Constituent c = new Constituent();
			com.orangeleap.tangerine.ws.schema.v2.Site s = new com.orangeleap.tangerine.ws.schema.v2.Site();

			s.setName("company1");

			//
			// Test without constituent type
			c.setFirstName("Test");
			c.setLastName("User");
			c.setSite(s);
			request.setConstituent(c);

			try {
				response = constituentEndpointV2.maintainConstituent(request);
			} catch (ConstituentValidationException e) {
				//
				// This test should throw an error
				Assert.assertTrue(true);
			}

			//
			// Test with a constituent type
			c.setConstituentType("individual");
			request.setConstituent(c);
			try {
				response = constituentEndpointV2.maintainConstituent(request);
			} catch (ConstituentValidationException e) {
				//
				// Should not get here
				Assert.assertTrue(false);
			}
			
			//
			// Test to make sure I got back a constituent id
			Constituent returned = response.getConstituent();
			Assert.assertTrue(returned.getId() != null && returned.getId() > 0);
		} catch (BindException e) {
			//
			// We better not get here!
			Assert.assertTrue(false);
		}

	}

	@Test(groups = "soapAPITests")
	void testAddGift() {
		SaveOrUpdateGiftRequest request = new SaveOrUpdateGiftRequest();
		SaveOrUpdateGiftResponse response = null;
		com.orangeleap.tangerine.ws.schema.v2.Gift g = new com.orangeleap.tangerine.ws.schema.v2.Gift();
		
		g.setConstituentId(100L);
		g.setAmount(new BigDecimal(100.00));
		request.setGift(g);
		
		//
		// test without a paymentsource
		try {
			response = constituentEndpointV2.maintainGift(request);
		} catch (InvalidRequestException e) {
			//
			// This should fail because there is no payment source
			logger.error(e.getMessage());
			Assert.assertTrue(true);
		}
		
		PaymentSource paySource = new PaymentSource();
		paySource.setPaymentType(PaymentType.CREDIT_CARD);
		paySource.setCreditCardHolderName("Leo DAngelo");
		paySource.setCreditCardNumber("4111111111111111");
		paySource.setCreditCardExpirationMonth(11);
		paySource.setCreditCardExpirationYear(2011);
		g.setPaymentSource(paySource);
		request.setGift(g);
		try {
			response = constituentEndpointV2.maintainGift(request);
		} catch (InvalidRequestException e) {
			//
			// This should fail because the constituent id is missing from the paymentsource
			logger.error(e.getMessage());
			Assert.assertTrue(true);
		}		

		paySource.setConstituentId(100L);
		request.setGift(g);
		try {
			response = constituentEndpointV2.maintainGift(request);
		} catch (InvalidRequestException e) {
			//
			// This should fail because the credit card type is missing from the paymentsource
			logger.error(e.getMessage());
			Assert.assertTrue(true);
		}		
		
		paySource.setCreditCardType("bogus");
		request.setGift(g);
		try {
			response = constituentEndpointV2.maintainGift(request);
		} catch (InvalidRequestException e) {
			//
			// This should fail because the credit card type is bogus
			logger.error(e.getMessage());
			Assert.assertTrue(true);
		}		
		
		paySource.setCreditCardType("Visa");
		try {
			response = constituentEndpointV2.maintainGift(request);
			Assert.assertTrue(false);
		} catch (InvalidRequestException e) {
			//
			//  This should fail because we don't have a constituent id on the request
			logger.error(e.getMessage());
			Assert.assertTrue(true);
		}		
		
		g.setPaymentType(PaymentType.CREDIT_CARD);
		request.setConstituentId(100L);
		
		try {
			response = constituentEndpointV2.maintainGift(request);
			Assert.assertTrue(true);
		} catch (InvalidRequestException e) {
			//
			//  This should pass
			logger.error(e.getMessage());
			Assert.assertTrue(false);
		}				
		
	}

	@Test(groups = "soapAPITests")
	void testAddCommunicationHistory() {
		AddCommunicationHistoryRequest request = new AddCommunicationHistoryRequest();
		CommunicationHistory ch = new CommunicationHistory();
		
		request.setConstituentId(100L);
		ch.setConstituentId(100L);
		ch.setComments("This is a test");
		ch.setCommunicationHistoryType("");

		try {
			constituentEndpointV2.addCommunicationHistory(request);
		} catch (InvalidRequestException e) {
			Assert.assertTrue(false);			
		}
		Assert.assertTrue(true);
	}

	@Test(groups = "soapAPITests")
	void testBulkAddCommunicationHistory() {
		BulkAddCommunicationHistoryRequest request = new BulkAddCommunicationHistoryRequest();
		CommunicationHistory ch = new CommunicationHistory();
		
		request.getConstituentId().add(100L);
		request.getConstituentId().add(200L);
		ch.setConstituentId(100L);
		ch.setComments("This is a test");
		ch.setCommunicationHistoryType("");
		
		try {
			constituentEndpointV2.bulkAddCommunicationHistory(request);
		} catch (InvalidRequestException e) {
			Assert.assertTrue(false);			
		}
		Assert.assertTrue(true);		

	}

	@Test(groups = "soapAPITests")
	void testFind() {
		FindConstituentsRequest request = new FindConstituentsRequest();
		com.orangeleap.tangerine.ws.schema.v2.FindConstituentsResponse response = null;
		//
		// test a bogus find request
		try {
			response = constituentEndpointV2.findConstituent(request);
			Assert.assertTrue(false);
		} catch (InvalidRequestException e1) {
			Assert.assertTrue(true);
		}

		//
		// Test with firstName LastName
		request.setFirstName("Howdy");
		request.setLastName("Doody");
		try {
			response = constituentEndpointV2.findConstituent(request);
		} catch (InvalidRequestException e1) {
			// TODO Auto-generated catch block
			Assert.assertTrue(false);
		}
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getConstituent());
		Assert.assertTrue(response.getConstituent().size() > 0);

		//
		// Test with e-mail address
		Email e = new Email();
		e.setEmailAddress("");
		request.setPrimaryEmail(e);
		try {
			response = constituentEndpointV2.findConstituent(request);
		} catch (InvalidRequestException e1) {
			Assert.assertTrue(false);
		}
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getConstituent());
		Assert.assertTrue(response.getConstituent().size() > 0);

		//
		// Make sure we don't find him with a different e-mail
		e.setEmailAddress("dog@dog.com");
		request.setPrimaryEmail(e);
		try {
			response = constituentEndpointV2.findConstituent(request);
		} catch (InvalidRequestException e1) {
			Assert.assertTrue(false);
		}
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getConstituent());
		Assert.assertTrue(response.getConstituent().size() == 0);

		//
		// See if we can find with an address
		request.setFirstName("Pablo");
		request.setLastName("");
		e.setEmailAddress("");
		request.setPrimaryEmail(e);

		Address address = new Address();
		address.setAddressLine1("8457 ACORN");
		request.setPrimaryAddress(address);
		try {
			response = constituentEndpointV2.findConstituent(request);
		} catch (InvalidRequestException e1) {
			Assert.assertTrue(false);
		}
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getConstituent());
		Assert.assertTrue(response.getConstituent().size() > 0);

	}
}
