package com.orangeleap.tangerine.service.impl;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.CreditCardService;

@Service("creditCardService")
@Transactional(propagation = Propagation.REQUIRED)
public class CreditCardServiceImpl extends AbstractTangerineService implements CreditCardService {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

	
    @Override
    public Gift processCreditCard(Gift gift) {
        // TODO: implement call to processing system and get a confirmation
        // local validation should already be done to minimize charges
        String confirmation = null;
        gift.setAuthCode(confirmation);
        return gift;
    }
}
