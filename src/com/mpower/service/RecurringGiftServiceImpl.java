package com.mpower.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.RecurringGiftDao;
import com.mpower.domain.Gift;
import com.mpower.domain.RecurringGift;

@Service("recurringGiftService")
public class RecurringGiftServiceImpl implements RecurringGiftService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "giftService")
    private GiftService giftService;

    @Resource(name = "recurringGiftDao")
    private RecurringGiftDao recurringGiftDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<RecurringGift> readRecurringGifts(Date date, List<String> statuses) {
        return recurringGiftDao.readRecurringGifts(date, statuses);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void processRecurringGifts() {
        List<RecurringGift> recurringGifts = recurringGiftDao.readRecurringGifts(Calendar.getInstance().getTime(), Arrays.asList(new String[] { "Active", "Fulfilled" }));
        if (recurringGifts != null) {
            for (RecurringGift rg : recurringGifts) {
                logger.debug("recurring gift: id=" + rg.getId() + ", next run=" + rg.getNextRunDate());
                Gift gift = new Gift();
                gift.setPerson(rg.getCommitment().getPerson());
                gift.setCommitment(rg.getCommitment());
                gift.setComments(rg.getCommitment().getComments());
                gift.setDeductible(rg.getCommitment().isDeductible());
                gift.setValue(rg.getCommitment().getAmountPerGift());
                // TODO: change payment type to reflect commitment payment type
                gift.setPaymentType(rg.getCommitment().getPaymentSource().getType());
                gift = giftService.maintainGift(gift);
                if (rg.getCommitment().getNumberOfGifts() != null && rg.getCommitment().getGifts().size() >= rg.getCommitment().getNumberOfGifts()) {
                    logger.debug("commitment number of gifts met, so removing recurring gift");
                    recurringGiftDao.remove(rg);
                    rg.getCommitment().setRecurringGift(null);
                } else {
                    if (rg.getCommitment().getNumberOfGifts() != null) {
                        logger.debug("gift " + rg.getCommitment().getGifts().size() + " of " + rg.getCommitment().getNumberOfGifts());
                    } else {
                        logger.debug("gift does not specify a number of gifts");
                    }
                    Date previousNextRun = rg.getNextRunDate();
                    Calendar nextRun = Calendar.getInstance();
                    nextRun.setTime(previousNextRun);
                    if ("Weekly".equals(rg.getCommitment().getFrequency())) {
                        nextRun.add(Calendar.WEEK_OF_MONTH, 1);
                    } else if ("Monthly".equals(rg.getCommitment().getFrequency())) {
                        nextRun.add(Calendar.MONTH, 1);
                    } else if ("Quarterly".equals(rg.getCommitment().getFrequency())) {
                        nextRun.add(Calendar.MONTH, 3);
                    } else if ("Annually".equals(rg.getCommitment().getFrequency())) {
                        nextRun.add(Calendar.YEAR, 1);
                    } else {
                        // TODO: implement (need to cover "Other" case)
                    }
                    if (rg.getCommitment().getEndDate() != null && nextRun.after(rg.getCommitment().getEndDate())) {
                        logger.debug("next run date is after end date, so removing recurring gift");
                        recurringGiftDao.remove(rg);
                        rg.getCommitment().setRecurringGift(null);
                    } else {
                        logger.debug("frequency = " + rg.getCommitment().getFrequency() + ": previous gift was for, " + previousNextRun + ", setting next gift for " + nextRun.getTime());
                        // TODO: remove once done testing
                        Calendar testNextRun = Calendar.getInstance();
                        testNextRun.add(Calendar.MINUTE, 1);
                        rg.setNextRunDate(testNextRun.getTime());
                        logger.debug("it is currently, " + Calendar.getInstance().getTime() + ", running again at " + nextRun.getTime());
                        recurringGiftDao.maintain(rg);
                    }
                }
            }
        }
    }
}
