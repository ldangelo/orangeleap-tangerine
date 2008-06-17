package com.mpower.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mpower.dao.GiftDao;
import com.mpower.domain.entity.Gift;
import com.mpower.domain.entity.Person;

@Service("giftService")
public class GiftServiceImpl implements GiftService {

    @Resource(name = "giftDao")
    private GiftDao giftDao;

    @Override
    public Gift maintainGift(Gift gift) {
        return giftDao.maintainGift(gift);
    }

    @Override
    public Gift readGiftById(Long giftId) {
        return giftDao.readGift(giftId);
    }

    @Override
    public List<Gift> readGifts(Person person) {
        return giftDao.readGifts(person.getId());
    }
}
