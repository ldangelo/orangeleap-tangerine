package com.mpower.dao;

import java.util.List;

import com.mpower.domain.PaymentSource;

public interface PaymentSourceDao {

    public PaymentSource maintainPaymentSource(PaymentSource paymentSource);

    public PaymentSource findPaymentSourceProfile(Long personId, String profile);

    public List<PaymentSource> readActivePaymentSources(Long personId);

    public PaymentSource readPaymentSource(Long paymentSourceId);
}
