package com.mpower.controller.payment;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.controller.constituent.RequiresConstituentEditor;
import com.mpower.domain.PaymentSource;
import com.mpower.service.PaymentSourceService;
import com.mpower.service.PersonService;

public class PaymentSourceEditor extends RequiresConstituentEditor {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PaymentSourceService paymentSourceService;

    public PaymentSourceEditor() {
        super();
    }

    public PaymentSourceEditor(PaymentSourceService paymentSourceService, PersonService personService, String personId) {
        super(personService, personId);
        setPaymentSourceService(paymentSourceService);
    }

    public void setPaymentSourceService(PaymentSourceService paymentSourceService) {
        this.paymentSourceService = paymentSourceService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (NumberUtils.isDigits(text)) {
            Long paymentSourceId = NumberUtils.createLong(text);
            PaymentSource ps = paymentSourceService.readPaymentSource(paymentSourceId);
            setValue(ps);
        }
        else if ("new".equals(text)) {
            PaymentSource ps = new PaymentSource(super.getPerson());
            ps.setUserCreated(true);
            setValue(ps);
        }
    }
}
