package com.mpower.dao;

import java.util.List;

import com.mpower.domain.PaymentSource;

public interface PaymentSourceDao {

    public PaymentSource maintainPaymentSource(PaymentSource paymentSource);

    public List<PaymentSource> readPaymentSources(Long personId);

    public void deletePaymentSource(PaymentSource paymentSource);
}
