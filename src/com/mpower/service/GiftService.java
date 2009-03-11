package com.mpower.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mpower.domain.model.Person;
import com.mpower.domain.model.paymentInfo.Commitment;
import com.mpower.domain.model.paymentInfo.Gift;
import com.mpower.type.GiftEntryType;

public interface GiftService {

    public Gift maintainGift(Gift gift);
    
    public Gift editGift(Gift gift);
    
    public Gift readGiftById(Long giftId);

    public Gift readGiftByIdCreateIfNull(String giftId, String commitmentId, Person constituent);

    public List<Gift> readGifts(Person constituent);

    public List<Gift> readGifts(Long constituentId);

    public List<Gift> searchGifts(Map<String, Object> params);

    public Gift createDefaultGift(Person constituent);

    public double analyzeMajorDonor(Long constituentId, Date beginDate, Date currentDate);

    public Gift refundGift(Long giftId);

    public List<Gift> readGiftsByConstituentId(Long constituentId);

//    public List<Gift> readAllGifts();

    public Gift createGift(Commitment commitment, GiftEntryType giftEntryType);

    public List<Gift> readGiftsByCommitment(Commitment commitment);

	public List<Gift> readAllGiftsBySiteName();
}
