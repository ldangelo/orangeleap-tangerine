package com.orangeleap.tangerine.dao;

import java.util.Date;
import java.util.List;

import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;

public interface RecurringGiftDao {

    public List<RecurringGift> readRecurringGifts(Date date, List<String> statuses);

    public RecurringGift maintainRecurringGift(RecurringGift rg);

    public void removeRecurringGift(RecurringGift rg);
}
