package com.orangeleap.tangerine.service.payments;

import org.springframework.context.ApplicationContextAware;

import com.orangeleap.tangerine.domain.paymentInfo.Gift;

public interface ACHPaymentGateway extends ApplicationContextAware {
	public void Process(Gift g);
	
	public void Refund(Gift g);
}
