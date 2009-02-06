package com.mpower.service.payments;

import com.mpower.domain.Gift;

public interface PaymentGateway {
	void Authorize(Gift gift);
	void AuthorizeAndCapture(Gift gift);
	void Capture(Gift gift);
	void Refund(Gift gift);
}
