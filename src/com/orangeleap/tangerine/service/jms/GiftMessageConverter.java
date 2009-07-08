/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.service.jms;

import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

public class GiftMessageConverter implements MessageConverter {

    @Override
    public Object fromMessage(Message message) throws JMSException,
            MessageConversionException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Message toMessage(Object object, Session session)
            throws JMSException, MessageConversionException {

        if (!(object instanceof Gift)) {
            throw new MessageConversionException("Object isn't a Gift!");
        }

        Gift gift = (Gift) object;
        MapMessage message = session.createMapMessage();
        message.setString("lastName", gift.getConstituent().getLastName());
        message.setString("firstName", gift.getConstituent().getFirstName());
        message.setString("gift amount", gift.getAmount().toString());
        message.setString("merchant acct", gift.getSite().getMerchantNumber());

        return message;
    }

}
