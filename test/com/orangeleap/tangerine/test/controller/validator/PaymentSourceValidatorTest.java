package com.orangeleap.tangerine.test.controller.validator;

import com.orangeleap.tangerine.controller.validator.PaymentSourceValidator;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.service.PaymentSourceService;
import com.orangeleap.tangerine.test.BaseTest;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.springframework.validation.BindException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PaymentSourceValidatorTest extends BaseTest {

    private PaymentSourceValidator validator;
    private PaymentSource source;
    private BindException errors;
    private Mockery mockery;
    private final Long CONSTITUENT_ID = 1L;

    @BeforeMethod
    public void setupMocks() {
        mockery = new Mockery();
        validator = new PaymentSourceValidator();

        source = new PaymentSource();
        Constituent constituent = new Constituent();
        constituent.setId(CONSTITUENT_ID);
        source.setConstituent(constituent);

        errors = new BindException(source, "paymentSource");

        final PaymentSourceService service = mockery.mock(PaymentSourceService.class);
        validator.setPaymentSourceService(service);

        mockery.checking(new Expectations() {{
            allowing (service).findPaymentSourceProfile(CONSTITUENT_ID, "MyProfile"); will(returnValue(new PaymentSource()));
        }});
    }

    @Test(groups = { "validatePaymentProfile" })
    public void testValidPaymentProfile() throws Exception {
        source.setProfile(null);
        validator.validatePaymentProfile(source, errors);

        mockery.assertIsSatisfied();
        assert errors.hasFieldErrors() == false;
    }

    @Test(groups = { "validatePaymentProfile" })
    public void testBlankPaymentProfile() throws Exception {
        source.setProfile("  ");
        validator.validatePaymentProfile(source, errors);

        mockery.assertIsSatisfied();
        assert "blankPaymentProfile".equals(errors.getFieldError("profile").getCode());
    }

    @Test(groups = { "validatePaymentProfile" })
    public void testExistingPaymentProfileForNewPaymentSource() throws Exception {
        source.resetIdToNull();
        source.setProfile("MyProfile");
        validator.validatePaymentProfile(source, errors);

        mockery.assertIsSatisfied();
        assert "paymentProfileAlreadyExists".equals(errors.getFieldError("profile").getCode());
    }

    @Test(groups = { "validatePaymentProfile" })
    public void testExistingPaymentProfileForExistingPaymentSource() throws Exception {
        source.setId(new Long(1));
        source.setProfile("MyProfile");
        validator.validatePaymentProfile(source, errors);

        mockery.assertIsSatisfied();
        assert errors.hasFieldErrors() == false;
    }

	@Test(groups = { "validateCreditCardTypeNumbers" })
	public void testValidateCreditCardVisaValid() throws Exception {
	    source.setCreditCardType(PaymentSource.VISA);
		source.setCreditCardNumber("4111111111111111");
	    validator.validateCreditCard(source, errors);
	    Assert.assertFalse(errors.hasFieldErrors());
	}

	@Test(groups = { "validateCreditCardTypeNumbers" })
	public void testValidateCreditCardVisaInvalid() throws Exception {
	    source.setCreditCardType(PaymentSource.VISA);
		source.setCreditCardNumber("555555555555444");
	    validator.validateCreditCard(source, errors);
	    Assert.assertEquals(errors.getFieldError("creditCardNumber").getCode(), "invalidCreditCardNumber");
	}

	@Test(groups = { "validateCreditCardTypeNumbers" })
	public void testValidateCreditCardDiscoverValid() throws Exception {
	    source.setCreditCardType(PaymentSource.DISCOVER);
		source.setCreditCardNumber("6011000995500000");
	    validator.validateCreditCard(source, errors);
	    Assert.assertFalse(errors.hasFieldErrors());
	}

	@Test(groups = { "validateCreditCardTypeNumbers" })
	public void testValidateCreditCardDiscoverInvalid() throws Exception {
	    source.setCreditCardType(PaymentSource.DISCOVER);
		source.setCreditCardNumber("4111111111111111");
	    validator.validateCreditCard(source, errors);
	    Assert.assertEquals(errors.getFieldError("creditCardNumber").getCode(), "invalidCreditCardNumber");
	}

	@Test(groups = { "validateCreditCardTypeNumbers" })
	public void testValidateCreditCardMasterCardValid() throws Exception {
	    source.setCreditCardType(PaymentSource.MASTER_CARD);
		source.setCreditCardNumber("5454545454545454");
	    validator.validateCreditCard(source, errors);
	    Assert.assertFalse(errors.hasFieldErrors());
	}

	@Test(groups = { "validateCreditCardTypeNumbers" })
	public void testValidateCreditCardMasterCardInvalid() throws Exception {
	    source.setCreditCardType(PaymentSource.MASTER_CARD);
		source.setCreditCardNumber("371449635398431");
	    validator.validateCreditCard(source, errors);
	    Assert.assertEquals(errors.getFieldError("creditCardNumber").getCode(), "invalidCreditCardNumber");
	}

	@Test(groups = { "validateCreditCardTypeNumbers" })
	public void testValidateCreditCardAmExValid() throws Exception {
	    source.setCreditCardType(PaymentSource.AMERICAN_EXPRESS);
		source.setCreditCardNumber("371449635398431");
	    validator.validateCreditCard(source, errors);
	    Assert.assertFalse(errors.hasFieldErrors());
	}

	@Test(groups = { "validateCreditCardTypeNumbers" })
	public void testValidateCreditCardAmExInvalid() throws Exception {
	    source.setCreditCardType(PaymentSource.AMERICAN_EXPRESS);
		source.setCreditCardNumber("6011000995500000");
	    validator.validateCreditCard(source, errors);
	    Assert.assertEquals(errors.getFieldError("creditCardNumber").getCode(), "invalidCreditCardNumber");
	}
}
