package com.mpower.test.controller.validator;

import mockit.Mockit;

import org.springframework.validation.BindException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mpower.controller.validator.PaymentSourceValidator;
import com.mpower.domain.PaymentSource;
import com.mpower.domain.Person;
import com.mpower.service.PaymentSourceService;
import com.mpower.service.PaymentSourceServiceImpl;
import com.mpower.test.BaseTest;

public class PaymentSourceValidatorTest extends BaseTest {

    private PaymentSourceValidator validator;
    private PaymentSource source;
    private BindException errors;

    @BeforeMethod
    public void setupMocks() {
        validator = new PaymentSourceValidator();
        PaymentSourceService service = (PaymentSourceService)applicationContext.getBean("paymentSourceService");
        validator.setPaymentSourceService(service);

        source = new PaymentSource();
        Person person = new Person();
        person.setId(1L);
        source.setPerson(person);

        errors = new BindException(source, "paymentSource");
    }

    private void redefineMethods() {
        Mockit.redefineMethods(PaymentSourceServiceImpl.class, new PaymentSourceValidatorTestMockService());
    }

    @Test(groups = { "validatePaymentProfile" })
    public void testValidPaymentProfile() throws Exception {
        redefineMethods();
        source.setProfile(null);
        validator.validatePaymentProfile(source, errors);
        assert errors.hasFieldErrors() == false;
    }

    @Test(groups = { "validatePaymentProfile" })
    public void testBlankPaymentProfile() throws Exception {
        redefineMethods();
        source.setProfile("  ");
        validator.validatePaymentProfile(source, errors);
        assert "blankPaymentProfile".equals(errors.getFieldError("profile").getCode());
    }

    @Test(groups = { "validatePaymentProfile" })
    public void testExistingPaymentProfile() throws Exception {
        redefineMethods();
        source.setProfile("MyProfile");
        validator.validatePaymentProfile(source, errors);
        assert "paymentProfileAlreadyExists".equals(errors.getFieldError("profile").getCode());
    }
}
