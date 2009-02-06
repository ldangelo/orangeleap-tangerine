package com.mpower.integration;

import org.springframework.integration.annotation.Gateway;

import com.mpower.domain.Gift;

public interface NewGift {

    @Gateway(requestChannel="newGiftChannel")
    void routeGift(Gift gift);
}
