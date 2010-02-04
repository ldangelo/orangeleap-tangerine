package com.orangeleap.tangerine.test.service.validator;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.service.PaymentSourceService;
import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.util.StringConstants;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.springframework.validation.BindException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PaymentSourceValidatorTest extends BaseTest {

    private com.orangeleap.tangerine.service.validator.PaymentSourceValidator validator;
    private PaymentSource source;
    private BindException errors;
    private Mockery mockery;
    private final Long CONSTITUENT_ID = 1L;
	private final PaymentSource ccSrc = new PaymentSource();
	private final PaymentSource achSrc = new PaymentSource();
	private final PaymentSource checkSrc = new PaymentSource();
	private final PaymentSource validNewSrc = new PaymentSource();
	private final PaymentSource validExistingSrc = new PaymentSource();
	private final PaymentSource existingDateSrc = new PaymentSource();
	private final PaymentSource savedDateSrc = new PaymentSource();

    @BeforeMethod
    public void setupMocks() throws BindException {
        mockery = new Mockery();
        validator = new com.orangeleap.tangerine.service.validator.PaymentSourceValidator();

        source = new PaymentSource();
        final Constituent constituent = new Constituent();
        constituent.setId(CONSTITUENT_ID);
        source.setConstituent(constituent);

        errors = new BindException(source, "paymentSource");

        final PaymentSourceService service = mockery.mock(PaymentSourceService.class);
        validator.setPaymentSourceService(service);

	    final Map<String, Object> existingSourceMap = new HashMap<String, Object>();
	    PaymentSource src = new PaymentSource();
	    src.setProfile("abc");
	    existingSourceMap.put("existingSource", src);

	    final Map<String, Object> namesSourceMap = new HashMap<String, Object>();
	    Set<String> names = new HashSet<String>();
	    names.add("bub");
	    namesSourceMap.put("names", names);

	    final Map<String, Object> datesSourceMap = new HashMap<String, Object>();
	    savedDateSrc.setId(9876L);
	    savedDateSrc.setProfile("123");
	    try {
	        savedDateSrc.setCreditCardExpiration(new SimpleDateFormat(StringConstants.MM_DD_YYYY_FORMAT).parse("01/01/2001"));
	    }
	    catch (Exception ex) {
		    // ignore
		}
	    List<PaymentSource> dateSources = new ArrayList<PaymentSource>();
	    dateSources.add(savedDateSrc);
	    datesSourceMap.put("dates", dateSources);

	    ccSrc.setProfile("myCreditCard");
	    achSrc.setProfile("myAch");
	    checkSrc.setProfile("myCheck");
	    validNewSrc.setProfile("validNewSrc");
	    validExistingSrc.setProfile("validExistingSrc");
	    existingDateSrc.setProfile("existingDateSrc");
	    existingDateSrc.setId(1234L);
	    try {
	        existingDateSrc.setCreditCardExpiration(new SimpleDateFormat(StringConstants.MM_DD_YYYY_FORMAT).parse("11/11/2011"));
	    }
	    catch (Exception ex) {
		    // ignore
		}

	    final Map<String, Object> validSourceMap = new HashMap<String, Object>();

        mockery.checking(new Expectations() {{
            allowing (service).findPaymentSourceProfile(CONSTITUENT_ID, "MyProfile"); will(returnValue(new PaymentSource()));
	        allowing (service).checkForSameConflictingPaymentSources(achSrc); will(returnValue(existingSourceMap));
	        allowing (service).checkForSameConflictingPaymentSources(checkSrc); will(returnValue(namesSourceMap));
	        allowing (service).checkForSameConflictingPaymentSources(ccSrc); will(returnValue(datesSourceMap));
	        allowing (service).checkForSameConflictingPaymentSources(validNewSrc); will(returnValue(validSourceMap));
	        allowing (service).checkForSameConflictingPaymentSources(validExistingSrc); will(returnValue(validSourceMap));
	        allowing (service).checkForSameConflictingPaymentSources(existingDateSrc); will(returnValue(datesSourceMap));
	        allowing (service).maintainPaymentSource(savedDateSrc); will(returnValue(savedDateSrc));
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

		mockery.assertIsSatisfied();
	    Assert.assertFalse(errors.hasFieldErrors());
	}

	@Test(groups = { "validateCreditCardTypeNumbers" })
	public void testValidateCreditCardVisaInvalid() throws Exception {
	    source.setCreditCardType(PaymentSource.VISA);
		source.setCreditCardNumber("555555555555444");
	    validator.validateCreditCard(source, errors);

		mockery.assertIsSatisfied();
	    Assert.assertEquals(errors.getFieldError("creditCardNumber").getCode(), "invalidCreditCardNumberForType");
	}

	@Test(groups = { "validateCreditCardTypeNumbers" })
	public void testValidateCreditCardDiscoverValid() throws Exception {
	    source.setCreditCardType(PaymentSource.DISCOVER);
		source.setCreditCardNumber("6011000995500000");
	    validator.validateCreditCard(source, errors);

		mockery.assertIsSatisfied();
	    Assert.assertFalse(errors.hasFieldErrors());
	}

	@Test(groups = { "validateCreditCardTypeNumbers" })
	public void testValidateCreditCardDiscoverInvalid() throws Exception {
	    source.setCreditCardType(PaymentSource.DISCOVER);
		source.setCreditCardNumber("4111111111111111");
	    validator.validateCreditCard(source, errors);

		mockery.assertIsSatisfied();
	    Assert.assertEquals(errors.getFieldError("creditCardNumber").getCode(), "invalidCreditCardNumberForType");
	}

	@Test(groups = { "validateCreditCardTypeNumbers" })
	public void testValidateCreditCardMasterCardValid() throws Exception {
	    source.setCreditCardType(PaymentSource.MASTER_CARD);
		source.setCreditCardNumber("5454545454545454");
	    validator.validateCreditCard(source, errors);

		mockery.assertIsSatisfied();
	    Assert.assertFalse(errors.hasFieldErrors());
	}

	@Test(groups = { "validateCreditCardTypeNumbers" })
	public void testValidateCreditCardMasterCardInvalid() throws Exception {
	    source.setCreditCardType(PaymentSource.MASTER_CARD);
		source.setCreditCardNumber("371449635398431");
	    validator.validateCreditCard(source, errors);

		mockery.assertIsSatisfied();
	    Assert.assertEquals(errors.getFieldError("creditCardNumber").getCode(), "invalidCreditCardNumberForType");
	}

	@Test(groups = { "validateCreditCardTypeNumbers" })
	public void testValidateCreditCardAmExValid() throws Exception {
	    source.setCreditCardType(PaymentSource.AMERICAN_EXPRESS);
		source.setCreditCardNumber("371449635398431");
	    validator.validateCreditCard(source, errors);

		mockery.assertIsSatisfied();
	    Assert.assertFalse(errors.hasFieldErrors());
	}

	@Test(groups = { "validateCreditCardTypeNumbers" })
	public void testValidateCreditCardAmExInvalid() throws Exception {
	    source.setCreditCardType(PaymentSource.AMERICAN_EXPRESS);
		source.setCreditCardNumber("6011000995500000");
	    validator.validateCreditCard(source, errors);

		mockery.assertIsSatisfied();
	    Assert.assertEquals(errors.getFieldError("creditCardNumber").getCode(), "invalidCreditCardNumberForType");
	}

	@Test(groups = { "validateCreditCardTypeNumbers" })
	public void testValidateCreditCardOtherInvalid() throws Exception {
	    source.setCreditCardType("Diners Club");
		source.setCreditCardNumber("6011000995500000");
	    validator.validateCreditCard(source, errors);

		mockery.assertIsSatisfied();
	    Assert.assertEquals(errors.getFieldError("creditCardType").getCode(), "unsupportedCreditCardType");
	}

	@Test(groups = { "validatePaymentSourceUnique" })
	public void testValidatePaymentSourceInvalidExistingSource() throws Exception {
		validator.validatePaymentSourceUnique(achSrc, errors);

		mockery.assertIsSatisfied();
		Assert.assertEquals(errors.getGlobalError().getDefaultMessage(), "The entered payment information already exists for the profile 'abc'");
	}

	@Test(groups = { "validatePaymentSourceUnique" })
	public void testValidatePaymentSourceInvalidConflictingName() throws Exception {
		validator.validatePaymentSourceUnique(checkSrc, errors);

		mockery.assertIsSatisfied();
		Assert.assertEquals(errors.getGlobalError().getDefaultMessage(), "A Payment Source Exists With a Conflicting Name");
	}

	@Test(groups = { "validatePaymentSourceUnique" })
	public void testValidatePaymentSourceInvalidCreditCardDates() throws Exception {
		validator.validatePaymentSourceUnique(ccSrc, errors);

		mockery.assertIsSatisfied();
		Assert.assertEquals(errors.getGlobalError().getDefaultMessage(), "The entered credit card information already exists for the profile '123' but with an expiration date of '01/2001'");
	}

	@Test(groups = { "validatePaymentSourceUnique" })
	public void testValidatePaymentSourceAllValidNew() throws Exception {
		validator.validatePaymentSourceUnique(validNewSrc, errors);

		mockery.assertIsSatisfied();
		Assert.assertFalse(errors.hasGlobalErrors());
	}

	@Test(groups = { "validatePaymentSourceUnique" })
	public void testValidatePaymentSourceAllValidExisting() throws Exception {
		validator.validatePaymentSourceUnique(validExistingSrc, errors);

		mockery.assertIsSatisfied();
		Assert.assertFalse(errors.hasGlobalErrors());
	}

	@Test(groups = { "validatePaymentSourceUnique" })
	public void testValidatePaymentSourceUpdateExistingDates() throws Exception {
		validator.validatePaymentSourceUnique(existingDateSrc, errors);

		mockery.assertIsSatisfied();
		Assert.assertFalse(errors.hasGlobalErrors());
		Assert.assertEquals(existingDateSrc.getId(), new Long(9876L));
		Assert.assertEquals(savedDateSrc.getCreditCardExpirationMonthText(), "11");
		Assert.assertEquals(savedDateSrc.getCreditCardExpirationYear(), new Integer(2011));
	}
}
