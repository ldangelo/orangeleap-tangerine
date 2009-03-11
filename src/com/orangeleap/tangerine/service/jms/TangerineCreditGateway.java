package com.orangeleap.tangerine.service.jms;

import com.orangeleap.tangerine.domain.paymentInfo.Gift;

public interface TangerineCreditGateway {

	public void sendGiftTransaction(Gift gift);
}
