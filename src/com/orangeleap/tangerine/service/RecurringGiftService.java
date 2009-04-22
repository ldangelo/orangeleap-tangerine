package com.orangeleap.tangerine.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

public interface RecurringGiftService extends CommitmentService<RecurringGift> {
    
    public RecurringGift readRecurringGiftById(Long recurringGiftId);
    
    public RecurringGift readRecurringGiftByIdCreateIfNull(String recurringGiftId, Person constituent);
    
    public RecurringGift createDefaultRecurringGift(Person constituent);

    public RecurringGift maintainRecurringGift(RecurringGift recurringGift);

    public RecurringGift editRecurringGift(RecurringGift recurringGift);
    
    public List<RecurringGift> readRecurringGiftsForConstituent(Person constituent);
    
    public List<RecurringGift> readRecurringGiftsForConstituent(Long constituentId);
    
	public PaginatedResult readPaginatedRecurringGiftsByConstituentId(Long constituentId, SortInfo sortinfo);
    
    public List<RecurringGift> readRecurringGiftsByDateStatuses(Date date, List<String> statuses);

    public void processRecurringGifts();
    
    public List<RecurringGift> searchRecurringGifts(Map<String, Object> params);
    
    public Map<String, List<RecurringGift>> findGiftAppliableRecurringGiftsForConstituent(Long constituentId, String selectedRecurringGiftIds);
    
    public List<RecurringGift> filterApplicableRecurringGiftsForConstituent(List<RecurringGift> gifts, Date nowDt);
    
    public List<DistributionLine> findDistributionLinesForRecurringGifts(Set<String> recurringGiftIds);

    public boolean canApplyPayment(RecurringGift recurringGift); 
}
