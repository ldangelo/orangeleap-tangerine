package com.mpower.service;

import org.springframework.stereotype.Service;

import com.mpower.domain.Gift;

@Service("creditCardService")
public class CreditCardServiceImpl implements CreditCardService {

    @Override
    public Gift processCreditCard(Gift gift) {
        // TODO: implement call to processing system and get a confirmation
        // local validation should already be done to minimize charges
        String confirmation = null;
        gift.setAuthCode(confirmation);
        return gift;
    }
}
