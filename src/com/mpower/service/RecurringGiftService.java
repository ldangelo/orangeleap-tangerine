package com.mpower.service;

import java.util.Date;
import java.util.List;

import com.mpower.domain.model.paymentInfo.Commitment;
import com.mpower.domain.model.paymentInfo.RecurringGift;

public interface RecurringGiftService {

    public RecurringGift maintainRecurringGift(Commitment commitment);

    public List<RecurringGift> readRecurringGifts(Date date, List<String> statuses);

    public void processRecurringGifts();
}
