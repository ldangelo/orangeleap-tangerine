package com.mpower.dao;

import java.util.List;

import com.mpower.domain.entity.Gift;

public interface GiftDao {

    public Gift maintainGift(Gift gift);

    public Gift readGift(Long giftId);

    public List<Gift> readGifts(Long personId);
}
