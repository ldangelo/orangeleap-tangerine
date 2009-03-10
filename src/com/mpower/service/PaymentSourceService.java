package com.mpower.service;

import java.util.List;

import com.mpower.domain.model.PaymentSource;
import com.mpower.domain.model.Person;

public interface PaymentSourceService {

    public PaymentSource maintainPaymentSource(PaymentSource paymentSource);

    public List<PaymentSource> readPaymentSources(Long constituentId);

    public List<PaymentSource> filterValidPaymentSources(Long constituentId);

    public PaymentSource readPaymentSource(Long paymentSourceId);

    public PaymentSource readPaymentSourceCreateIfNull(String paymentSourceId, Person constituent);

    public PaymentSource findPaymentSourceProfile(Long constituentId, String profile);

    public List<PaymentSource> readAllPaymentSourcesACHCreditCard(Long constituentId);
    
    public List<PaymentSource> readActivePaymentSourcesACHCreditCard(Long constituentId);

    public List<PaymentSource> readActivePaymentSourcesByTypes(Long constituentId, List<String> paymentTypes);
}
