package com.orangeleap.tangerine.integration;

import org.springframework.integration.annotation.Gateway;

import com.orangeleap.tangerine.domain.paymentInfo.Gift;

public interface NewGift {

    @Gateway(requestChannel="newGiftChannel")
    void routeGift(Gift gift);
}
