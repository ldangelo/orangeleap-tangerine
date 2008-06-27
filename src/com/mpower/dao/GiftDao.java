package com.mpower.dao;

import java.util.List;
import java.util.Date;


import com.mpower.domain.Gift;

public interface GiftDao {

    public Gift maintainGift(Gift gift);

    public Gift readGift(Long giftId);

    public List<Gift> readGifts(Long personId);

    public double analyzeMajorDonor(Long personId, String beginDate, String currentDate);
}
