package com.mpower.dao.interfaces;

import java.util.List;

import com.mpower.domain.model.PaymentSource;

public interface PaymentSourceDao {

    public PaymentSource maintainPaymentSource(PaymentSource paymentSource);

    public PaymentSource readPaymentSourceByProfile(Long constituentId, String profile);

    public List<PaymentSource> readActivePaymentSources(Long constituentId);

    public List<PaymentSource> readActivePaymentSourcesByTypes(Long constituentId, List<String> paymentTypes);

    public PaymentSource readPaymentSourceById(Long paymentSourceId);
}
