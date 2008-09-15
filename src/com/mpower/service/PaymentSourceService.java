package com.mpower.service;

import java.util.List;

import com.mpower.domain.PaymentSource;

public interface PaymentSourceService {

    public PaymentSource savePaymentSource(PaymentSource paymentSource);

    public List<PaymentSource> readPaymentSources(Long personId);

    public void deletePaymentSource(PaymentSource paymentSource);

    public void setAuditService(AuditService auditService);
}
