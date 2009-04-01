package com.orangeleap.tangerine.service.payments;

import com.orangeleap.tangerine.domain.paymentInfo.Gift;

public interface ACHPaymentGateway {
	public void Process(Gift g);
	
	public void Refund(Gift g);
}
