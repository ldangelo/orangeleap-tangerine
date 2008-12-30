package com.mpower.controller.validator;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mpower.domain.PaymentSource;
import com.mpower.domain.PaymentSourceAware;
import com.mpower.service.PaymentSourceService;
import com.mpower.type.PageType;
import com.mpower.util.CalendarUtils;

public class PaymentSourceValidator implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    public static final String ACH = "ACH";
    public static final String CREDIT_CARD = "Credit Card";

    private PaymentSourceService paymentSourceService;

    public void setPaymentSourceService(PaymentSourceService paymentSourceService) {
        this.paymentSourceService = paymentSourceService;
    }

    @SuppressWarnings("unused")
    private PageType pageType;

    public void setPageType(PageType pageType) {
        this.pageType = pageType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return PaymentSource.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.debug("in PaymentSourceValidator");

        validatePaymentProfile(target, errors);
        validatePaymentSource(target, errors);
    }

    public static void validatePaymentSource(Object target, Errors errors) {
        PaymentSource source = null;
        String inPath = errors.getNestedPath();
        if (target instanceof PaymentSource) {
            source = (PaymentSource) target;
        }
        else if (target instanceof PaymentSourceAware) {
            source = ((PaymentSourceAware) target).getPaymentSource();
            errors.setNestedPath("paymentSource");
        }

        if (ACH.equals(source.getType())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "achAccountNumber", "invalidAchAccountNumber");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "achRoutingNumber", "invalidAchRoutingNumber");
        }
        else if (CREDIT_CARD.equals(source.getType())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditCardNumber", "invalidCreditCardNumber");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditCardExpiration", "invalidCreditCardExpiration");
            if (!errors.hasErrors()) {
                Date expirationDate = source.getCreditCardExpiration();
                Calendar today = CalendarUtils.getToday(false);
                if (expirationDate == null || today.getTime().after(expirationDate)) {
                    errors.rejectValue("creditCardExpiration", "invalidCreditCardExpiration");
                }
            }
        }
        errors.setNestedPath(inPath);
    }

    public void validatePaymentProfile(Object target, Errors errors) {
        PaymentSource source = null;
        if (target instanceof PaymentSource) {
            source = (PaymentSource) target;
            if (source.getProfile() != null) {
                if (StringUtils.hasText(source.getProfile()) == false) {
                    errors.rejectValue("profile", "blankPaymentProfile");
                }
                else {
                    PaymentSource existingPaymentProfile = paymentSourceService.findPaymentSourceProfile(source.getPerson().getId(), source.getProfile());

                    // a payment source with this profile name already exists; reject
                    if (existingPaymentProfile != null) {
                        errors.rejectValue("profile", "paymentProfileAlreadyExists");
                    }
                }
            }
        }
    }
}