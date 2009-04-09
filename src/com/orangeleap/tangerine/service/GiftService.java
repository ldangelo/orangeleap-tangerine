package com.orangeleap.tangerine.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.paymentInfo.Commitment;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.type.GiftEntryType;
import com.orangeleap.tangerine.type.GiftType;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

public interface GiftService {

    public Gift maintainGift(Gift gift);
    
    public Gift editGift(Gift gift);
    
    public Gift readGiftById(Long giftId);

    public Gift readGiftByIdCreateIfNull(Person constituent, String giftId, String recurringGiftId, String pledgeId);

    public List<Gift> readMonetaryGifts(Person constituent);

    public List<Gift> readMonetaryGifts(Long constituentId);

    public List<Gift> searchGifts(Map<String, Object> params);

    public Gift createDefaultGift(Person constituent);

    public double analyzeMajorDonor(Long constituentId, Date beginDate, Date currentDate);

    public Gift refundGift(Long giftId);

    public List<Gift> readMonetaryGiftsByConstituentId(Long constituentId);

    public List<DistributionLine> removeDefaultDistributionLine(List<DistributionLine> giftDistributionLines, BigDecimal amount);

    public Gift createGift(Commitment commitment, GiftType giftType, GiftEntryType giftEntryType);

    public List<Gift> readGiftsByRecurringGiftId(RecurringGift recurringGift);
    
    public List<Gift> readGiftsByPledgeId(Pledge pledge);

	public List<Gift> readAllGiftsBySiteName();
	
	public List<Gift> readAllGiftsByDateRange(Date fromDate, Date toDate);

	public PaginatedResult readPaginatedMonetaryGifts(Long constituentId, SortInfo sortinfo);
	
	public List<DistributionLine> combineGiftPledgeDistributionLines(List<DistributionLine> giftDistributionLines, List<DistributionLine> pledgeLines, BigDecimal amount, int numPledges);
	
	public void initGiftAmountDistributionLinesFromPledge(Gift gift, String selectedPledgeId);
	
	public void checkAssociatedPledgeIds(Gift gift);
}
