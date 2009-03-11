package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.PaymentHistory;

public interface PaymentHistoryDao {
	
	public PaymentHistory addPaymentHistory(PaymentHistory paymentHistory);
	
	public List<PaymentHistory> readPaymentHistoryByConstituentId(Long personId);
	
	public List<PaymentHistory> readPaymentHistoryBySite();

}
