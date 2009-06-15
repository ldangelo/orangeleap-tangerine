package com.orangeleap.tangerine.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

public interface GiftDao {

    public Gift maintainGift(Gift gift);

    public Gift readGiftById(Long giftId);

    public List<Gift> readMonetaryGiftsByConstituentId(Long constituentId);

    public List<Gift> searchGifts(Map<String, Object> params);

	public List<Gift> readAllGiftsBySite();
	
	public List<Gift> readAllGiftsByDateRange(Date fromDate, Date toDate);

	public double analyzeMajorDonor(Long constituentId, Date beginDate, Date currentDate);

	public List<Constituent> analyzeLapsedDonor(Date beginDate, Date currentDate);

	public PaginatedResult readPaginatedGiftListByConstituentId(Long constituentId, SortInfo sortinfo);

	
}
