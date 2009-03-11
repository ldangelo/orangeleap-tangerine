package com.mpower.controller.payment;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com.mpower.controller.TangerineConstituentAttributesFormController;
import com.mpower.domain.model.AbstractEntity;
import com.mpower.domain.model.PaymentSource;
import com.mpower.util.StringConstants;

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
    
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        paymentSourceService.maintainPaymentSource((PaymentSource)command);
        return super.onSubmit(request, response, command, errors);
    }
}
