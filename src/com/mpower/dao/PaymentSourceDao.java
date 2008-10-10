package com.mpower.dao;

import java.util.List;

import com.mpower.domain.PaymentSource;

public interface PaymentSourceDao {

	public PaymentSource maintainPaymentSource(PaymentSource paymentSource);

	public List<PaymentSource> readPaymentSources(Long personId);

	public void deletePaymentSource(PaymentSource paymentSource);

	public List<PaymentSource> readActivePaymentSources(Long personId);

	public void inactivatePaymentSource(Long paymentSourceId);

	public void removePaymentSource(Long paymentSourceId);

	public PaymentSource readPaymentSource(Long paymentSourceId);

}
