package com.mpower.service.payments;

import com.mpower.domain.model.paymentInfo.Gift;

public interface CreditCardPaymentGateway {
	void Authorize(Gift gift);
	void AuthorizeAndCapture(Gift gift);
	void Capture(Gift gift);
	void Refund(Gift gift);
}
