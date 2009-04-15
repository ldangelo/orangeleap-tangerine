package com.orangeleap.tangerine.controller.payment;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com.orangeleap.tangerine.controller.TangerineConstituentAttributesFormController;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.util.StringConstants;

public class PaymentManagerFormController extends TangerineConstituentAttributesFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @SuppressWarnings("unchecked")
    @Override
    protected void refDataPaymentSources(HttpServletRequest request, Object command, Errors errors, Map refData) {
        List<PaymentSource> paymentSources = paymentSourceService.readAllPaymentSourcesACHCreditCard(this.getConstituentId(request));
        refData.put(StringConstants.PAYMENT_SOURCES, paymentSources);
    }

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        return paymentSourceService.readPaymentSourceCreateIfNull(request.getParameter("paymentSourceId"), super.getConstituent(request));
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        PaymentSource source = (PaymentSource) command;
        if (source.getId() == null) {
            Map<String, Object> sources = paymentSourceService.checkForSameConflictingPaymentSources(source);

            PaymentSource existingSource = (PaymentSource) sources.get("existingSource");
            Set<String> names = (Set<String>) sources.get("names");
            List<PaymentSource> dateSources = (List<PaymentSource>) sources.get("dates");

            if (existingSource != null) {
                ModelAndView mav = showForm(request, response, errors);
                errors.reject("errorPaymentSourceExists", new String[] { existingSource.getProfile() }, "The entered payment information already exists for the profile '" + existingSource.getProfile() + "'");
                return mav;
            }
            else if (names != null && names.isEmpty() == false && "true".equals(request.getParameter("useConflictingName")) == false) {
                ModelAndView mav = showForm(request, response, errors);
                mav.addObject("conflictingNames", names);
                return mav;
            }
            else if (dateSources != null && dateSources.isEmpty() == false) {
                existingSource = dateSources.get(0); // should only be 1
                ModelAndView mav = showForm(request, response, errors);
                errors.reject("errorCreditCardDateExists", new String[] { existingSource.getProfile(), 
                        existingSource.getCreditCardExpirationMonthText() + "/" + existingSource.getCreditCardExpirationYear() }, 
                        "The entered credit card information already exists for the profile '" + existingSource.getProfile() + "' but with an expiration date of '" + 
                        existingSource.getCreditCardExpirationMonthText() + "/" + existingSource.getCreditCardExpirationYear() + "'");
                return mav;
            }
        }
        paymentSourceService.maintainPaymentSource(source);
        return super.onSubmit(request, response, command, errors);
    }
}
