package com.orangeleap.tangerine.service.payments;

import org.springframework.context.ApplicationContextAware;

import com.orangeleap.tangerine.domain.paymentInfo.Gift;

public interface CreditCardPaymentGateway extends ApplicationContextAware {
	void Authorize(Gift gift);
	void AuthorizeAndCapture(Gift gift);
	void Capture(Gift gift);
	void Refund(Gift gift);
}
