package com.mpower.service;

import java.util.Date;
import java.util.List;

import com.mpower.domain.Commitment;
import com.mpower.domain.RecurringGift;

public interface RecurringGiftService {

    public RecurringGift maintainRecurringGift(Commitment commitment);

    public List<RecurringGift> readRecurringGifts(Date date, List<String> statuses);

    public void processRecurringGifts();
}
