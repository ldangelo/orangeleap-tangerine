package com.mpower.dao;

import java.util.List;

import com.mpower.domain.PaymentSource;

@Deprecated
public interface PaymentSourceDao {

    public PaymentSource maintainPaymentSource(PaymentSource paymentSource);

    public PaymentSource findPaymentSourceProfile(Long personId, String profile);

    public List<PaymentSource> readActivePaymentSources(Long personId);

    public List<PaymentSource> readActivePaymentSourcesByTypes(Long personId, List<String> paymentTypes);

    public PaymentSource readPaymentSource(Long paymentSourceId);
}
