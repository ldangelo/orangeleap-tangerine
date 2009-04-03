package com.orangeleap.tangerine.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;

public interface RecurringGiftService extends CommitmentService<RecurringGift> {
    
    public RecurringGift readRecurringGiftById(Long recurringGiftId);
    
    public RecurringGift readRecurringGiftByIdCreateIfNull(String recurringGiftId, Person constituent);
    
    public RecurringGift createDefaultRecurringGift(Person constituent);

    public RecurringGift maintainRecurringGift(RecurringGift recurringGift);

    public RecurringGift editRecurringGift(RecurringGift recurringGift);
    
    public List<RecurringGift> readRecurringGiftsForConstituent(Person constituent);
    
    public List<RecurringGift> readRecurringGiftsForConstituent(Long constituentId);
    
    public List<RecurringGift> readRecurringGiftsByDateStatuses(Date date, List<String> statuses);

    public void processRecurringGifts();
    
    public List<RecurringGift> searchRecurringGifts(Map<String, Object> params);
}
