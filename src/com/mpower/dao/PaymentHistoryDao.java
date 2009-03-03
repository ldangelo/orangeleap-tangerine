package com.mpower.dao;

import java.util.List;

import com.mpower.domain.PaymentHistory;

@Deprecated
public interface PaymentHistoryDao {
	
	public PaymentHistory addPaymentHistory(PaymentHistory paymentHistory);
	
	public List<PaymentHistory> readPaymentHistory(Long personId);
	
	public List<PaymentHistory> readPaymentHistoryBySite(String siteId);

}
