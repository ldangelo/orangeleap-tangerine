package com.mpower.service;

import java.util.Date;
import java.util.List;

import com.mpower.domain.RecurringGift;

public interface RecurringGiftService {

    public List<RecurringGift> readRecurringGifts(Date date);
}
