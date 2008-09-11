package com.mpower.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mpower.domain.Gift;
import com.mpower.domain.Person;

public interface GiftService {

    public Gift maintainGift(Gift gift);

    public Gift readGiftById(Long giftId);

    public List<Gift> readGifts(Person person);

    public List<Gift> readGifts(Long personId);

    public List<Gift> readGifts(String siteName, Map<String, Object> params);

    public Gift createDefaultGift(Person person);

    public double analyzeMajorDonor(Long personId, Date beginDate, Date currentDate);

    public Gift refundGift(Long giftId);

    public List<Gift> readGiftsByPersonId(Long personId);

    public List<Gift> readAllGifts();

    public void setAuditService(AuditService auditService);
}
