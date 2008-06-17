package com.mpower.service;

import java.util.List;

import com.mpower.domain.entity.Gift;
import com.mpower.domain.entity.Person;

public interface GiftService {

    public Gift createGift(Gift gift);

    public List<Gift> readGifts(Person person);
}
