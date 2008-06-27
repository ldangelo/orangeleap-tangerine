package com.mpower.service;

import java.util.List;
import java.util.Date;

import com.mpower.domain.Gift;
import com.mpower.domain.Person;

public interface GiftService {

    public Gift maintainGift(Gift gift);

    public Gift readGiftById(Long giftId);

    public List<Gift> readGifts(Person person);

    public Gift createDefaultGift(Long siteId);
    
    public double analyzeMajorDonor(Long personId, String beginDate, String currentDate);
}
