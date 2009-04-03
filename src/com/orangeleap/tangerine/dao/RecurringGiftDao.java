package com.orangeleap.tangerine.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;

public interface RecurringGiftDao {

    public RecurringGift readRecurringGiftById(Long recurringGiftId);
    
    public List<RecurringGift> readRecurringGiftsByConstituentId(Long constituentId);
    
    public List<RecurringGift> readRecurringGifts(Date date, List<String> statuses);

    public RecurringGift maintainRecurringGift(RecurringGift rg);

    public void removeRecurringGift(RecurringGift rg);
    
    public List<RecurringGift> searchRecurringGifts(Map<String, Object> searchParams);
}
