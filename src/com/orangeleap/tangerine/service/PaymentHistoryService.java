package com.orangeleap.tangerine.service;

import java.util.List;

import com.orangeleap.tangerine.domain.PaymentHistory;

public interface PaymentHistoryService {

    public PaymentHistory addPaymentHistory(PaymentHistory paymentHistory);

    public List<PaymentHistory> readPaymentHistory(Long personId);

    public List<PaymentHistory> readPaymentHistoryBySite();

}
