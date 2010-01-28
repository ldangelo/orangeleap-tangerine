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

package com.orangeleap.tangerine.controller.payment;

import com.orangeleap.tangerine.controller.TangerineConstituentAttributesFormController;
import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PaymentManagerFormController extends TangerineConstituentAttributesFormController {

	/**
	 * Logger for this class and subclasses
	 */
	protected final Log logger = OLLogger.getLog(getClass());

	@SuppressWarnings("unchecked")
	@Override
	protected void refDataPaymentSources(HttpServletRequest request, Object command, Errors errors, Map refData) {
		List<PaymentSource> paymentSources = paymentSourceService.readAllPaymentSourcesACHCreditCardCheck(this.getConstituentId(request));
		refData.put(StringConstants.PAYMENT_SOURCES, paymentSources);
	}

	@Override
	protected AbstractEntity findEntity(HttpServletRequest request) {
		PaymentSource paymentSource = paymentSourceService.readPaymentSourceCreateIfNull(request.getParameter("paymentSourceId"), super.getConstituent(request));
        clearAddressFields(paymentSource);
        clearPhoneFields(paymentSource);
        return paymentSource;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException formErrors) throws Exception {
		TangerineForm form = (TangerineForm) command;
		PaymentSource source = (PaymentSource) form.getDomainObject();
		if (source.getId() == null) {
			Map<String, Object> sources = paymentSourceService.checkForSameConflictingPaymentSources(source);

			PaymentSource existingSource = (PaymentSource) sources.get("existingSource");
			Set<String> names = (Set<String>) sources.get("names");
			List<PaymentSource> dateSources = (List<PaymentSource>) sources.get("dates");

			if (existingSource != null) {
				ModelAndView mav = showForm(request, response, formErrors);
				formErrors.reject("errorPaymentSourceExists", new String[]{existingSource.getProfile()}, "The entered payment information already exists for the profile '" + existingSource.getProfile() + "'");
				return mav;
			}
			else if (names != null && ! names.isEmpty() && ! Boolean.TRUE.toString().toLowerCase().equals(request.getParameter("useConflictingName"))) {
				ModelAndView mav = showForm(request, response, formErrors);
				mav.addObject("conflictingNames", names);
				return mav;
			}
			else if (dateSources != null && !dateSources.isEmpty()) {
				existingSource = dateSources.get(0); // should only be 1
				ModelAndView mav = showForm(request, response, formErrors);
				formErrors.reject("errorCreditCardDateExists", new String[]{existingSource.getProfile(),
						existingSource.getCreditCardExpirationMonthText() + "/" + existingSource.getCreditCardExpirationYear()},
						"The entered credit card information already exists for the profile '" + existingSource.getProfile() + "' but with an expiration date of '" +
								existingSource.getCreditCardExpirationMonthText() + "/" + existingSource.getCreditCardExpirationYear() + "'");
				return mav;
			}
		}
		if (Boolean.TRUE.toString().toLowerCase().equals(request.getParameter("useConflictingName"))) {
		    source.setBypassUniqueValidation(true);
	    }

		ModelAndView mav;
		try {
			source = paymentSourceService.maintainPaymentSource(source);
			mav = new ModelAndView(super.appendSaved(getSuccessView() + "?" + StringConstants.PAYMENT_SOURCE_ID + "=" + source.getId() +
					"&" + StringConstants.CONSTITUENT_ID + "=" + super.getConstituentId(request)));
		}
		catch (BindException domainErrors) {
			bindDomainErrorsToForm(request, formErrors, domainErrors, form, source);
			mav = showForm(request, formErrors, getFormView());
		}
		return mav;
	}
}
