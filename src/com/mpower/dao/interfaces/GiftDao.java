package com.mpower.dao.interfaces;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mpower.domain.model.Person;
import com.mpower.domain.model.paymentInfo.Gift;

public interface GiftDao {

    public Gift maintainGift(Gift gift);

    public Gift readGiftById(Long giftId);

    public List<Gift> readGiftsByConstituentId(Long constituentId);

    public List<Gift> readGifts(String siteName, Map<String, Object> params);

    public List<Gift> readGiftsByCommitmentId(Long commitmentId);

    public BigDecimal readGiftsReceivedSumByCommitmentId(Long commitmentId);

	public List<Gift> readAllGiftsBySite();

	public double analyzeMajorDonor(Long constituentId, Date beginDate, Date currentDate);

	public List<Person> analyzeLapsedDonor(Date beginDate, Date currentDate);
}
