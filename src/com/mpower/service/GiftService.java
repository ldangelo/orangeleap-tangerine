package com.mpower.service;

import java.util.List;

import com.mpower.entity.Gift;
import com.mpower.entity.Person;

public interface GiftService {

    public Gift maintainGift(Gift gift);

    public Gift readGiftById(Long giftId);

    public List<Gift> readGifts(Person person);

    public Gift createDefaultGift(Long siteId);
}
