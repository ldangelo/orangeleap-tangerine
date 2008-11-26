package com.mpower.service.jms;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.mpower.domain.Gift;

public class MPowerCreditGatewayImpl implements MPowerCreditGateway {

    public MPowerCreditGatewayImpl() {
    }

    private JmsTemplate jmsTemplate;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void sendGiftInfo(final Gift gift) {
        jmsTemplate.send(
                new MessageCreator() {
                    public Message createMessage(Session session)
                    throws JMSException {
                        MapMessage message = session.createMapMessage();
                        message.setString("lastName", gift.getPerson().getLastName());
                        message.setString("firstName", gift.getPerson().getFirstName());
                        message.setString("gift amount", gift.getAmount().toString());

                        return message;
                    }
                }
        );

    }

}
