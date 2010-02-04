/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.service.validator;

import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.PaymentSourceAware;
import com.orangeleap.tangerine.service.PaymentSourceService;
import com.orangeleap.tangerine.util.CalendarUtils;
import com.orangeleap.tangerine.util.OLLogger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.validator.CreditCardValidator;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PaymentSourceValidator implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    private PaymentSourceService paymentSourceService;

    public void setPaymentSourceService(PaymentSourceService paymentSourceService) {
        this.paymentSourceService = paymentSourceService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return PaymentSource.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.trace("in PaymentSourceValidator");

        validatePaymentProfile(target, errors);
        validatePaymentSource(target, errors);
    }

    public void validatePaymentSource(Object target, Errors errors) {
        PaymentSource source = null;
        String inPath = errors.getNestedPath();
        if (target instanceof PaymentSource) {
            source = (PaymentSource) target;
        }
        else if (target instanceof PaymentSourceAware) {
            source = ((PaymentSourceAware) target).getPaymentSource();
            errors.setNestedPath("paymentSource");
        }

        if (source != null) {
	        if ( ! source.isBypassUniqueValidation()) {
	            validatePaymentSourceUnique(source, errors);
	        }
	        
	        if (PaymentSource.CREDIT_CARD.equals(source.getPaymentType())) {
		        validateCreditCard(source, errors);
		        
				Date expirationDate = source.getCreditCardExpiration();
				Calendar today = CalendarUtils.getToday(false);
				if (expirationDate == null || today.getTime().after(expirationDate)) {
					errors.rejectValue("creditCardExpiration", "invalidCreditCardExpiration");
				}
	        }
	        else if (PaymentSource.CHECK.equals(source.getPaymentType())) {
				if (StringUtils.hasText(source.getCheckRoutingNumber()) && ! StringUtils.hasText(source.getCheckAccountNumber())) {
					errors.rejectValue("checkAccountNumber", "invalidCheckAccountNumber");
				}
				else if (StringUtils.hasText(source.getCheckAccountNumber()) && ! StringUtils.hasText(source.getCheckRoutingNumber())) {
					errors.rejectValue("checkRoutingNumber", "invalidCheckRoutingNumber");
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
                if (!StringUtils.hasText(source.getProfile())) {
                    errors.rejectValue("profile", "blankPaymentProfile");
                }
                else if (source.getId() == null){
                    PaymentSource existingPaymentProfile = paymentSourceService.findPaymentSourceProfile(source.getConstituent().getId(), source.getProfile());

                    // a payment source with this profile name already exists; reject
                    if (existingPaymentProfile != null) {
                        errors.rejectValue("profile", "paymentProfileAlreadyExists");
                    }
                }
            }
        }
    }

	public void validateCreditCard(PaymentSource paymentSource, Errors errors) {
		int creditType = CreditCardValidator.NONE; // all other credit cards except Visa, Amex, Discover, and Master Card not currently supported
		if (PaymentSource.VISA.equals(paymentSource.getCreditCardType())) {
			creditType = CreditCardValidator.VISA;
		}
		else if (PaymentSource.AMERICAN_EXPRESS.equals(paymentSource.getCreditCardType())) {
			creditType = CreditCardValidator.AMEX;
		}
		else if (PaymentSource.DISCOVER.equals(paymentSource.getCreditCardType())) {
			creditType = CreditCardValidator.DISCOVER;
		}
		else if (PaymentSource.MASTER_CARD.equals(paymentSource.getCreditCardType())) {
			creditType = CreditCardValidator.MASTERCARD;
		}
		if (creditType == CreditCardValidator.NONE) {
			errors.rejectValue("creditCardType", "unsupportedCreditCardType");
		}
		else {
			final CreditCardValidator validator = new CreditCardValidator(creditType);
			if ( ! validator.isValid(paymentSource.getCreditCardNumber())) {
				errors.rejectValue("creditCardNumber", "invalidCreditCardNumberForType", new String[] { paymentSource.getCreditCardType() }, "Invalid Credit Card Number for Type");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void validatePaymentSourceUnique(PaymentSource paymentSource, Errors errors) {
		// check for duplicate payment sources...  If a duplicate is found then throw a BindException
		Map<String, Object> sources = paymentSourceService.checkForSameConflictingPaymentSources(paymentSource);
		if (paymentSource.isNew()) {
			PaymentSource existingSource = (PaymentSource) sources.get("existingSource");
			Set<String> names = (Set<String>) sources.get("names");
			List<PaymentSource> dateSources = (List<PaymentSource>) sources.get("dates");

			if (existingSource != null) {
					// throw a bind exception for payment information already exists
				errors.reject("paymentsource.exists", "The entered payment information already exists for the profile '" + existingSource.getProfile() + "'");
			}
			else if (names != null && !names.isEmpty()) {
				// throw a bindexception for conflicting names
				errors.reject("paymentsource.conflictingname", "A Payment Source Exists With a Conflicting Name");
			}
			else if (dateSources != null && !dateSources.isEmpty()) {
				// throw a bindexception for credit card exists
				existingSource = dateSources.get(0); // should only be 1
				errors.reject("paymentsource.creditcarddateexists", "The entered credit card information already exists for the profile '" + existingSource.getProfile() +
						"' but with an expiration date of '" +	existingSource.getCreditCardExpirationMonthText() + "/" + existingSource.getCreditCardExpirationYear() + "'");
			}
		}
		else {
			// For existing sources, update the Credit Card expiration date/month
			List<PaymentSource> dateSources = (List<PaymentSource>) sources.get("dates");
			if (dateSources != null && !dateSources.isEmpty()) {
				PaymentSource existingPaymentSource = dateSources.get(0); // should be only 1
				existingPaymentSource.setCreditCardExpirationMonth(paymentSource.getCreditCardExpirationMonth());
				existingPaymentSource.setCreditCardExpirationYear(paymentSource.getCreditCardExpirationYear());
				try {
					existingPaymentSource = paymentSourceService.maintainPaymentSource(existingPaymentSource);
				}
				catch (Exception ex) {
					logger.warn("validatePaymentSourceUnique: could not update paymentSource.id = " + existingPaymentSource.getId(), ex);
				}
				paymentSource.setId(existingPaymentSource.getId()); // change the ID to the existing PaymentSource ID
			}
		}
	}
}