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
import com.mpower.domain.Commitment;
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

    @Transactional(propagation = Propagation.SUPPORTS)
    public RecurringGift maintainRecurringGift(Commitment commitment) {
        RecurringGift rg = commitment.getRecurringGift();
        if (commitment.isAutoPay()) {
            if (rg == null) {
                rg = new RecurringGift(commitment);
            }
            rg.setNextRunDate(getNextRecurringGiftDate(commitment));
        } else {
            if (rg != null) {
                recurringGiftDao.remove(rg);
                rg = null;
            }
        }
        commitment.setRecurringGift(rg);
        return rg;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void processRecurringGifts() {
        List<RecurringGift> recurringGifts = recurringGiftDao.readRecurringGifts(Calendar.getInstance().getTime(), Arrays.asList(new String[] { "Active", "Fulfilled" }));
        if (recurringGifts != null) {
            for (RecurringGift rg : recurringGifts) {
                logger.debug("recurring gift: id=" + rg.getId() + ", next run=" + rg.getNextRunDate());
                Commitment commitment = rg.getCommitment();
                Date nextDate = null;
                if (commitment.getEndDate() == null || commitment.getEndDate().after(getToday().getTime())) {
                    createGift(rg.getCommitment());
                    nextDate = getNextRecurringGiftDate(rg.getCommitment());
                    if (nextDate != null) {
                        rg.setNextRunDate(nextDate);
                        recurringGiftDao.maintain(rg);
                    }
                }
                if (nextDate == null) {
                    recurringGiftDao.remove(rg);
                    commitment.setRecurringGift(null);
                }
            }
        }
    }

    private void createGift(Commitment commitment) {
        Gift gift = new Gift();
        gift.setPerson(commitment.getPerson());
        gift.setCommitment(commitment);
        gift.setComments(commitment.getComments());
        gift.setDeductible(commitment.isDeductible());
        gift.setValue(commitment.getAmountPerGift());
        // TODO: change payment type to reflect commitment payment type
        gift.setPaymentType(commitment.getPaymentSource().getType());
        gift.setPaymentSource(commitment.getPaymentSource());
        gift = giftService.maintainGift(gift);
    }

    private Date getNextRecurringGiftDate(Commitment commitment) {
        Date nextGiftDate = new Date();
        if (commitment.getNumberOfGifts() != null) {
            logger.debug("gift " + commitment.getGifts().size() + " of " + commitment.getNumberOfGifts());
        } else {
            logger.debug("gift does not specify a number of gifts");
        }

        if (commitment.getEndDate() != null && commitment.getEndDate().before(Calendar.getInstance().getTime())) {
            nextGiftDate = null;
        } else if (commitment.getNumberOfGifts() != null && commitment.getGifts().size() >= commitment.getNumberOfGifts()) {
            logger.debug("commitment number of gifts met, so removing recurring gift");
            nextGiftDate = null;
        } else if (Commitment.STATUS_ACTIVE.equals(commitment.getStatus())) {
            nextGiftDate = calculateNextRunDate(commitment);
        } else if (Commitment.STATUS_FULFILLED.equals(commitment.getStatus())) {
            nextGiftDate = calculateNextRunDate(commitment);
        } else if (Commitment.STATUS_SUSPEND.equals(commitment.getStatus())) {
            nextGiftDate = calculateNextRunDate(commitment);
        } else {
            nextGiftDate = null;
        }

        if (nextGiftDate != null) {
            Date prevDate = commitment.getRecurringGift() == null ? null : commitment.getRecurringGift().getNextRunDate();
            logger.debug("frequency = " + commitment.getFrequency() + ": previous gift was for, " + prevDate + ", setting next gift for " + nextGiftDate);

            // TODO: remove once done testing
            // Calendar testNextRun = Calendar.getInstance();
            // testNextRun.add(Calendar.MINUTE, 1);
            // nextGiftDate = testNextRun.getTime();
            logger.debug("it is currently, " + Calendar.getInstance().getTime() + ", running again at " + nextGiftDate);
        }
        return nextGiftDate;
    }

    private Date calculateNextRunDate(Commitment commitment) {
        Date previousNextRun = commitment.getRecurringGift() == null ? null : commitment.getRecurringGift().getNextRunDate();
        Calendar nextRun = Calendar.getInstance();
        if (previousNextRun != null) {
            nextRun.setTime(previousNextRun);
        } else {
            nextRun.setTime(commitment.getStartDate());
        }
        while (nextRun.before(getToday()) || commitment.isSuspended(nextRun.getTime())) {
            if (commitment.isSuspended(nextRun.getTime())) {
                logger.debug("next run, " + nextRun.getTime() + " is during a suspended period so going to next");
            }
            if (Commitment.FREQUENCY_WEEKLY.equals(commitment.getFrequency())) {
                nextRun.add(Calendar.WEEK_OF_MONTH, 1);
            } else if (Commitment.FREQUENCY_MONTHLY.equals(commitment.getFrequency())) {
                nextRun.add(Calendar.MONTH, 1);
            } else if (Commitment.FREQUENCY_QUARTERLY.equals(commitment.getFrequency())) {
                nextRun.add(Calendar.MONTH, 3);
            } else if (Commitment.FREQUENCY_ANNUALLY.equals(commitment.getFrequency())) {
                nextRun.add(Calendar.YEAR, 1);
            } else {
                // TODO: implement (need to cover "Other" case)
            }
        }
        if (nextRun.getTime().after(commitment.getEndDate())) {
            logger.debug("next run, " + nextRun.getTime() + " is after commitment end date");
            nextRun.setTime(commitment.getEndDate());
        }
        return nextRun.getTime();
    }

    private Calendar getToday() {
        Calendar today = Calendar.getInstance();
        today.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        logger.debug("getToday() = " + today.getTime());
        return today;
    }
}
