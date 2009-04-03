package com.orangeleap.tangerine.service;

import java.util.List;
import java.util.Map;

import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.Person;

public interface PaymentSourceService {

    public PaymentSource maintainPaymentSource(PaymentSource paymentSource);

    public List<PaymentSource> readPaymentSources(Long constituentId);

    public List<PaymentSource> filterValidPaymentSources(Long constituentId);

    public PaymentSource readPaymentSource(Long paymentSourceId);

    public PaymentSource readPaymentSourceCreateIfNull(String paymentSourceId, Person constituent);

    public PaymentSource findPaymentSourceProfile(Long constituentId, String profile);

    public List<PaymentSource> readAllPaymentSourcesACHCreditCard(Long constituentId);
    
    public List<PaymentSource> readActivePaymentSourcesByTypes(Long constituentId, List<String> paymentTypes);

    Map<String, List<PaymentSource>> groupPaymentSources(Long constituentId, PaymentSource selectedPaymentSource);
}
