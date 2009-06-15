package com.orangeleap.tangerine.service;

import com.orangeleap.tangerine.domain.PaymentHistory;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

public interface PaymentHistoryService {

    public PaymentHistory addPaymentHistory(PaymentHistory paymentHistory);

    public PaginatedResult readPaymentHistory(Long constituentId, SortInfo sortinfo);

    public PaginatedResult readPaymentHistoryBySite(SortInfo sortinfo);

}
