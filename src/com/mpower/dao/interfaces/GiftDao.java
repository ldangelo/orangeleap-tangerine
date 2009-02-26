package com.mpower.dao.interfaces;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mpower.domain.model.Gift;

public interface GiftDao {

    public Gift maintainGift(Gift gift);

    public Gift readGift(Long giftId);

    public List<Gift> readGifts(Long personId);

    public List<Gift> readGifts(String siteName, Map<String, Object> params);

    public double analyzeMajorDonor(Long personId, Date beginDate, Date currentDate);

    public List<Gift> readGiftsByPersonId(Long personId);

    public List<Gift> readAllGifts();

    public List<Gift> readGiftsByCommitmentId(Long commitmentId);

    public BigDecimal readGiftsReceivedSumByCommitmentId(Long commitmentId);

	public List<Gift> readAllGiftsBySite();
}
