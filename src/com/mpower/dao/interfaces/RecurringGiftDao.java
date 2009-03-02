package com.mpower.dao.interfaces;

import java.util.Date;
import java.util.List;

import com.mpower.domain.model.paymentInfo.RecurringGift;

public interface RecurringGiftDao {

    public List<RecurringGift> readRecurringGifts(Date date, List<String> statuses);

    public RecurringGift maintainRecurringGift(RecurringGift rg);

    public void removeRecurringGift(RecurringGift rg);
}
