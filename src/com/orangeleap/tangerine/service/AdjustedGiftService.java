package com.orangeleap.tangerine.service;

import java.math.BigDecimal;
import java.util.List;

import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;

public interface AdjustedGiftService {

    public AdjustedGift createdDefaultAdjustedGift(Long originalGiftId);
    
    public AdjustedGift readAdjustedGiftById(Long adjustedGiftId);
    
    public List<AdjustedGift> readAdjustedGiftsForOriginalGiftId(Long originalGiftId);
    
    public AdjustedGift maintainAdjustedGift(AdjustedGift adjustedGift);
    
    public BigDecimal findCurrentTotalAdjustedAmount(Long originalGiftId);
}
