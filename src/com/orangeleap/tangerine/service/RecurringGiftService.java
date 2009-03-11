package com.orangeleap.tangerine.service;

import java.util.Date;
import java.util.List;

import com.orangeleap.tangerine.domain.paymentInfo.Commitment;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;

public interface RecurringGiftService {

    public RecurringGift maintainRecurringGift(Commitment commitment);

    public List<RecurringGift> readRecurringGifts(Date date, List<String> statuses);

    public void processRecurringGifts();
}
