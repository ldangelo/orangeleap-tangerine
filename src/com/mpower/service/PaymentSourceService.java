package com.mpower.service;

import java.util.List;

import com.mpower.domain.PaymentSource;
import com.mpower.domain.Person;

public interface PaymentSourceService {

    public PaymentSource maintainPaymentSource(PaymentSource paymentSource);

    public List<PaymentSource> readPaymentSources(Long personId);

    public List<PaymentSource> filterValidPaymentSources(Long personId);

    public void setAuditService(AuditService auditService);

    public PaymentSource readPaymentSource(Long paymentSourceId);

    public PaymentSource readPaymentSourceCreateIfNull(String paymentSourceId, Person person, boolean setDefaultHolder);

    public PaymentSource findPaymentSourceProfile(Long personId, String profile);

    public List<PaymentSource> readActivePaymentSourcesACHCreditCard(Long personId);

    public List<PaymentSource> readActivePaymentSourcesByTypes(Long personId, List<String> paymentTypes);
}
