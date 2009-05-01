package com.orangeleap.tangerine.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.AdjustedGiftDao;
import com.orangeleap.tangerine.dao.GiftDao;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.AdjustedGiftService;

@Service("adjustedGiftService")
@Transactional(propagation = Propagation.REQUIRED)
public class AdjustedGiftServiceImpl extends AbstractPaymentService implements AdjustedGiftService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "adjustedGiftDAO")
    private AdjustedGiftDao adjustedGiftDao;
    
    @Resource(name = "giftDAO")
    private GiftDao giftDao;
    
    @Override
    public AdjustedGift createdDefaultAdjustedGift(Long originalGiftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("createdDefaultAdjustedGift: originalGiftId = " + originalGiftId);
        }
        Gift originalGift = giftDao.readGiftById(originalGiftId);
        if (originalGift == null) {
            throw new IllegalArgumentException("Gift for ID = " + originalGiftId + " was not found");
        }
        AdjustedGift aAdjustedGift = new AdjustedGift(originalGift);
        aAdjustedGift.setCurrentTotalAdjustedAmount(findCurrentTotalAdjustedAmount(originalGiftId));
        return aAdjustedGift;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AdjustedGift maintainAdjustedGift(AdjustedGift adjustedGift) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainAdjustedGift: adjustedGiftId = " + adjustedGift.getId());
        }
        return adjustedGiftDao.maintainAdjustedGift(adjustedGift);
    }

    @Override
    public AdjustedGift readAdjustedGiftById(Long adjustedGiftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAdjustedGiftById: adjustedGiftId = " + adjustedGiftId);
        }
        AdjustedGift aAdjustedGift = adjustedGiftDao.readAdjustedGiftById(adjustedGiftId);
        aAdjustedGift.setCurrentTotalAdjustedAmount(findCurrentTotalAdjustedAmount(aAdjustedGift.getOriginalGiftId()));
        return aAdjustedGift;
    }

    @Override
    public List<AdjustedGift> readAdjustedGiftsForOriginalGiftId(Long originalGiftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAdjustedGiftsForOriginalGiftId: originalGiftId = " + originalGiftId);
        }
        return adjustedGiftDao.readAdjustedGiftsForOriginalGiftId(originalGiftId);
    }
    
    @Override
    public BigDecimal findCurrentTotalAdjustedAmount(Long originalGiftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("findCurrentTotalAdjustedAmount: originalGiftId = " + originalGiftId);
        }
        BigDecimal existingAmount = BigDecimal.ZERO;
        List<AdjustedGift> adjustedGifts = readAdjustedGiftsForOriginalGiftId(originalGiftId);
        if (adjustedGifts != null) {
            for (AdjustedGift aAdjGift : adjustedGifts) {
                if (aAdjGift.getAdjustedAmount() != null) {
                    existingAmount = existingAmount.add(aAdjGift.getAdjustedAmount());
                }
            }
        }
        return existingAmount;
    }
}
