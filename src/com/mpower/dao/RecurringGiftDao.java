package com.mpower.dao;

import java.util.Date;
import java.util.List;

import com.mpower.domain.RecurringGift;

public interface RecurringGiftDao {

    public List<RecurringGift> readRecurringGifts(Date date);

    public RecurringGift maintain(RecurringGift rg);

    public void remove(RecurringGift rg);
}
