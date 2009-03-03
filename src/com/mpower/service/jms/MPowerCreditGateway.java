package com.mpower.service.jms;

import com.mpower.domain.model.paymentInfo.Gift;

public interface MPowerCreditGateway {

	public void sendGiftTransaction(Gift gift);
}
