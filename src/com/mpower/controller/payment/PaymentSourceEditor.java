package com.mpower.controller.payment;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.PaymentSource;
import com.mpower.service.PaymentSourceService;

public class PaymentSourceEditor extends PropertyEditorSupport {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PaymentSourceService paymentSourceService;

    public PaymentSourceEditor() {
        super();
    }

    public PaymentSourceEditor(PaymentSourceService paymentSourceService) {
        super();
        setPaymentSourceService(paymentSourceService);
    }

    public void setPaymentSourceService(PaymentSourceService paymentSourceService) {
        this.paymentSourceService = paymentSourceService;
    }

    public void setAsText(String text) throws IllegalArgumentException {
        if (NumberUtils.isDigits(text)) {
            Long paymentSourceId = NumberUtils.createLong(text);
            PaymentSource ps = paymentSourceService.readPaymentSource(paymentSourceId);
            setValue(ps);
        } else {
            setValue(new PaymentSource());
        }
    }
}
