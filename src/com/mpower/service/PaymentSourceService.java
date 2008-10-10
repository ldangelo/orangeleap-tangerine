package com.mpower.service;

import java.util.List;

import com.mpower.domain.PaymentSource;

public interface PaymentSourceService {

	public PaymentSource savePaymentSource(PaymentSource paymentSource);

	public List<PaymentSource> readPaymentSources(Long personId);

	public List<PaymentSource> readActivePaymentSources(Long personId);

	public void setAuditService(AuditService auditService);

	public void removePaymentSource(Long paymentSourceId);

	public void inactivatePaymentSource(Long paymentSourceId);

	public void deletePaymentSource(PaymentSource paymentSource);

	public PaymentSource readPaymentSource(Long paymentSourceId);

}
