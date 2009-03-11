package com.orangeleap.tangerine.service.jms;

import org.springframework.jms.core.JmsTemplate;

import com.orangeleap.tangerine.domain.paymentInfo.Gift;

public class TangerineCreditGatewayImpl implements TangerineCreditGateway {

    public TangerineCreditGatewayImpl() {
    }

    private JmsTemplate jmsTemplate;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void sendGiftTransaction(final Gift gift) {
        jmsTemplate.convertAndSend(gift);
    }

}
