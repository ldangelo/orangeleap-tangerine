package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;

public interface AdjustedGiftDao {

    public AdjustedGift readAdjustedGiftById(Long adjustedGiftId);
    
    public List<AdjustedGift> readAdjustedGiftsForOriginalGiftId(Long originalGiftId);
    
    public AdjustedGift maintainAdjustedGift(AdjustedGift adjustedGift);
}
