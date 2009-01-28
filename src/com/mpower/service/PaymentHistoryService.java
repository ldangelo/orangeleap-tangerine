package com.mpower.service;

import java.util.List;

import com.mpower.domain.PaymentHistory;

public interface PaymentHistoryService {

    public PaymentHistory addPaymentHistory(PaymentHistory paymentHistory);

    public List<PaymentHistory> readPaymentHistory(Long personId);

    public List<PaymentHistory> readPaymentHistoryBySite(String siteId);

}
