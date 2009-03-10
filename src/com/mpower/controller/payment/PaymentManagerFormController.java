package com.mpower.controller.payment;

import java.util.HashMap;
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
import com.mpower.domain.model.communication.Address;
import com.mpower.domain.model.communication.Phone;
import com.mpower.util.StringConstants;

public class PaymentManagerFormController extends TangerineConstituentAttributesFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
        Map refData = new HashMap();
        this.addConstituentToReferenceData(request, refData);

        List<PaymentSource> paymentSources = paymentSourceService.readAllPaymentSourcesACHCreditCard(this.getConstituentId(request));
        refData.put(StringConstants.PAYMENT_SOURCES, paymentSources);

        List<Address> addresses = addressService.filterValid(this.getConstituentId(request));
        refData.put(StringConstants.ADDRESSES, addresses);

        List<Phone> phones = phoneService.filterValid(this.getConstituentId(request));
        refData.put(StringConstants.PHONES, phones);

        return refData;
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
