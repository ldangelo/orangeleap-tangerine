package com.orangeleap.tangerine.dao;

import com.orangeleap.tangerine.domain.PaymentHistory;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

public interface PaymentHistoryDao {
	
	public PaymentHistory addPaymentHistory(PaymentHistory paymentHistory);
	
	public PaginatedResult readPaymentHistoryByConstituentId(Long personId, SortInfo sortinfo);
	
	public PaginatedResult readPaymentHistoryBySite(SortInfo sortinfo);

}
