package com.mpower.test.controller.validator;

import java.util.List;

import com.mpower.domain.PaymentSource;
import com.mpower.service.AuditService;
import com.mpower.service.PaymentSourceService;

public class PaymentSourceValidatorTestMockService implements PaymentSourceService {

    @Override
    public PaymentSource readPaymentSource(Long paymentSourceId) {
        return null;
    }

    @Override
    public List<PaymentSource> readPaymentSources(Long personId) {
        return null;
    }

    @Override
    public PaymentSource savePaymentSource(PaymentSource paymentSource) {
        return null;
    }

    @Override
    public void setAuditService(AuditService auditService) {
    }

    public PaymentSource findPaymentSourceProfile(Long personId, String profile) {
        if (personId == 1L && profile.equals("MyProfile")) {
            return new PaymentSource();
        }
        return null;
    }
}
