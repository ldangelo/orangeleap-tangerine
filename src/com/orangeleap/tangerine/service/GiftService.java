package com.orangeleap.tangerine.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindException;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

public interface GiftService {

    public Gift maintainGift(Gift gift) throws BindException;
    
    public Gift editGift(Gift gift);
    
    public Gift readGiftById(Long giftId);

    public Gift readGiftByIdCreateIfNull(Person constituent, String giftId);

    public List<Gift> readMonetaryGifts(Person constituent);

    public List<Gift> readMonetaryGifts(Long constituentId);

    public List<Gift> searchGifts(Map<String, Object> params);

    public Gift createDefaultGift(Person constituent);

    public double analyzeMajorDonor(Long constituentId, Date beginDate, Date currentDate);

    public List<Gift> readMonetaryGiftsByConstituentId(Long constituentId);

    public List<DistributionLine> removeDefaultDistributionLine(List<DistributionLine> giftDistributionLines, BigDecimal amount, Person constituent);

	public List<Gift> readAllGiftsBySiteName();
	
	public List<Gift> readAllGiftsByDateRange(Date fromDate, Date toDate);

	public PaginatedResult readPaginatedGiftList(Long constituentId, SortInfo sortinfo);
	
	public List<DistributionLine> combineGiftCommitmentDistributionLines(List<DistributionLine> giftDistributionLines, List<DistributionLine> commitmentLines, BigDecimal amount, int numCommitments, Person constituent, boolean isPledge);
	
	public void checkAssociatedPledgeIds(Gift gift);
	
	public void checkAssociatedRecurringGiftIds(Gift gift);
}
