package com.mpower.controller.payment;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.controller.constituent.RequiresConstituentEditor;
import com.mpower.domain.model.PaymentSource;
import com.mpower.service.PaymentSourceService;

public class PaymentSourceEditor extends RequiresConstituentEditor {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PaymentSourceService paymentSourceService;

    public PaymentSourceEditor() {
        super();
    }

    public PaymentSourceEditor(PaymentSourceService paymentSourceService, String constituentId) {
        super(constituentId);
        this.paymentSourceService = paymentSourceService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (NumberUtils.isDigits(text)) {
            Long paymentSourceId = NumberUtils.createLong(text);
            PaymentSource ps = paymentSourceService.readPaymentSource(paymentSourceId);
            setValue(ps);
        }
//        else if (StringConstants.NEW.equals(text)) {
//            PaymentSource ps = new PaymentSource(super.getPerson());
//            ps.setUserCreated(true);
//            setValue(ps);
//        }
    }
}
