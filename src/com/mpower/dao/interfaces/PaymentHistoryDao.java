package com.mpower.dao.interfaces;

import java.util.List;

import com.mpower.domain.model.PaymentHistory;

public interface PaymentHistoryDao {
	
	public PaymentHistory addPaymentHistory(PaymentHistory paymentHistory);
	
	public List<PaymentHistory> readPaymentHistoryByConstituentId(Long personId);
	
	public List<PaymentHistory> readPaymentHistoryBySite();

}
