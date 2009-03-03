package com.mpower.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.domain.model.paymentInfo.Gift;
import com.mpower.service.CreditCardService;

@Service("creditCardService")
@Transactional(propagation = Propagation.REQUIRED)
public class CreditCardServiceImpl implements CreditCardService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	
    @Override
    public Gift processCreditCard(Gift gift) {
        // TODO: implement call to processing system and get a confirmation
        // local validation should already be done to minimize charges
        String confirmation = null;
        gift.setAuthCode(confirmation);
        return gift;
    }
}
