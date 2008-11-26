package com.mpower.service.jms;

import org.springframework.jms.core.JmsTemplate;

import com.mpower.domain.Gift;

public class MPowerCreditGatewayImpl implements MPowerCreditGateway {

    public MPowerCreditGatewayImpl() {
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
