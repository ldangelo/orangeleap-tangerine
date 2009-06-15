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
import com.orangeleap.tangerine.domain.PaymentHistory;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.AdjustedGiftService;
import com.orangeleap.tangerine.service.PaymentHistoryService;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.type.PaymentHistoryType;
import com.orangeleap.tangerine.util.StringConstants;

@Service("adjustedGiftService")
@Transactional(propagation = Propagation.REQUIRED)
public class AdjustedGiftServiceImpl extends AbstractPaymentService implements AdjustedGiftService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "adjustedGiftDAO")
    private AdjustedGiftDao adjustedGiftDao;
    
    @Resource(name = "giftDAO")
    private GiftDao giftDao;

    @Resource(name = "paymentHistoryService")
    private PaymentHistoryService paymentHistoryService;

    @Resource(name = "pledgeService")
    private PledgeService pledgeService;

    @Resource(name = "recurringGiftService")
    private RecurringGiftService recurringGiftService;
    
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
        maintainEntityChildren(adjustedGift, adjustedGift.getConstituent());
        adjustedGift = adjustedGiftDao.maintainAdjustedGift(adjustedGift);
        pledgeService.updatePledgeForAdjustedGift(adjustedGift);
        recurringGiftService.updateRecurringGiftForAdjustedGift(adjustedGift);
        if (adjustedGift.isAdjustedPaymentRequired()) {
            paymentHistoryService.addPaymentHistory(createPaymentHistoryForAdjustedGift(adjustedGift));
        }
        auditService.auditObject(adjustedGift, adjustedGift.getConstituent());
        return adjustedGift;
    }

    private PaymentHistory createPaymentHistoryForAdjustedGift(AdjustedGift adjustedGift) {
        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setAmount(adjustedGift.getAdjustedAmount());
        paymentHistory.setCurrencyCode(adjustedGift.getCurrencyCode());
        paymentHistory.setAdjustedGiftId(adjustedGift.getId());
        paymentHistory.setConstituent(adjustedGift.getConstituent());
        paymentHistory.setPaymentHistoryType(PaymentHistoryType.ADJUSTED_GIFT);
        paymentHistory.setPaymentType(adjustedGift.getPaymentType());
        paymentHistory.setPaymentStatus(adjustedGift.getPaymentStatus());
        paymentHistory.setTransactionDate(adjustedGift.getAdjustedTransactionDate());
        paymentHistory.setTransactionId(StringConstants.EMPTY);
        paymentHistory.setDescription(getPaymentDescription(adjustedGift));
        return paymentHistory;
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
        return findCurrentTotalAdjustedAmount(readAdjustedGiftsForOriginalGiftId(originalGiftId));
    }
    
    @Override
    public BigDecimal findCurrentTotalAdjustedAmount(List<AdjustedGift> adjustedGifts) {
        if (logger.isTraceEnabled()) {
            logger.trace("findCurrentTotalAdjustedAmount: ");
        }
        BigDecimal existingAmount = BigDecimal.ZERO;
        if (adjustedGifts != null) {
            for (AdjustedGift aAdjGift : adjustedGifts) {
                if (aAdjGift.getAdjustedAmount() != null) {
                    existingAmount = existingAmount.add(aAdjGift.getAdjustedAmount());
                }
            }
        }
        return existingAmount;
    }
    
    @Override
    public boolean isAdjustedAmountEqualGiftAmount(Gift gift) {
        if (logger.isTraceEnabled()) {
            logger.trace("isAdjustedAmountEqualGiftAmount: gift.amount = " + gift.getAmount());
        }
        
        BigDecimal originalAmount = gift.getAmount();
        BigDecimal adjustedAmount = findCurrentTotalAdjustedAmount(gift.getAdjustedGifts());
        return originalAmount.add(adjustedAmount).floatValue() == 0;
    }

    @Override
    public boolean isAdjustedAmountEqualGiftAmount(AdjustedGift adjustedGift) {
        if (logger.isTraceEnabled()) {
            logger.trace("isAdjustedAmountEqualGiftAmount: adjustedGift.adjustedAmount = " + adjustedGift.getAdjustedAmount());
        }
        
        BigDecimal originalAmount = adjustedGift.getOriginalAmount();
        BigDecimal adjustedAmount = adjustedGift.getCurrentTotalAdjustedAmount();
        return originalAmount.add(adjustedAmount).floatValue() == 0;
    }
}
