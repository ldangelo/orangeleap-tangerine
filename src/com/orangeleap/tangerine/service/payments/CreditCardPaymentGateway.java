package com.orangeleap.tangerine.service.payments;

import com.orangeleap.tangerine.domain.paymentInfo.Gift;

public interface CreditCardPaymentGateway {
	void Authorize(Gift gift);
	void AuthorizeAndCapture(Gift gift);
	void Capture(Gift gift);
	void Refund(Gift gift);
}
