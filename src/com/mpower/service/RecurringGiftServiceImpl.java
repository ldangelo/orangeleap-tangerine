package com.mpower.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
                    createAutoGift(rg.getCommitment());
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

    private void createAutoGift(Commitment commitment) {
        Gift gift = giftService.createGift(commitment);
        gift = giftService.maintainGift(gift);
        commitment.setLastEntryDate(gift.getTransactionDate());
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
        } else if (Commitment.STATUS_ACTIVE.equals(commitment.getStatus())) {
            nextGiftDate = calculateNextRunDate(commitment);
        } else if (Commitment.STATUS_FULFILLED.equals(commitment.getStatus())) {
            nextGiftDate = calculateNextRunDate(commitment);
        } else if (Commitment.STATUS_SUSPEND.equals(commitment.getStatus())) {
            nextGiftDate = calculateNextRunDate(commitment);
        } else {
            nextGiftDate = null;
        }

        if (nextGiftDate != null && logger.isDebugEnabled()) {
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
        logger.debug("next run = " + nextRun.getTime() + " millis=" + nextRun.getTimeInMillis());
        while (nextRun.before(getToday()) || commitment.isSuspended(nextRun.getTime()) || (commitment.getLastEntryDate() != null && !nextRun.getTime().after(commitment.getLastEntryDate()))) {
            if (commitment.isSuspended(nextRun.getTime())) {
                logger.debug("next run, " + nextRun.getTime() + " is during a suspended period so going to next");
            }
            if (Commitment.FREQUENCY_WEEKLY.equals(commitment.getFrequency())) {
                nextRun.add(Calendar.WEEK_OF_MONTH, 1);
            } else if (Commitment.FREQUENCY_MONTHLY.equals(commitment.getFrequency())) {
                nextRun.add(Calendar.MONTH, 1);
            } else if (Commitment.FREQUENCY_TWICE_MONTHLY.equals(commitment.getFrequency())) {
                Calendar today = getToday();
                Calendar startDateCal = new GregorianCalendar();
                startDateCal.setTimeInMillis(commitment.getStartDate().getTime());
                Calendar firstGiftCal = new GregorianCalendar(startDateCal.get(Calendar.YEAR), startDateCal.get(Calendar.MONTH), startDateCal.get(Calendar.DAY_OF_MONTH));
                boolean found = false;
                if (firstGiftCal.after(today)){
                    nextRun.setTimeInMillis(firstGiftCal.getTimeInMillis());
                    found = true;
                }
                Calendar secondGiftCal = new GregorianCalendar();
                secondGiftCal.setTimeInMillis(firstGiftCal.getTimeInMillis() + (1000 * 60 * 60 * 24 * 15));
                if (secondGiftCal.after(today)){
                    nextRun.setTimeInMillis(secondGiftCal.getTimeInMillis());
                    found = true;
                }
                int i = 1;
                while (!found){
                    Calendar payment1 = getBimonthlyCalendar(firstGiftCal.get(Calendar.YEAR), firstGiftCal.get(Calendar.MONTH) + i, firstGiftCal.get(Calendar.DAY_OF_MONTH));
                    if (payment1.after(today)){
                        nextRun.setTimeInMillis(payment1.getTimeInMillis());
                        found = true;
                    }
                    Calendar payment2 = getBimonthlyCalendar(secondGiftCal.get(Calendar.YEAR), secondGiftCal.get(Calendar.MONTH) + i, secondGiftCal.get(Calendar.DAY_OF_MONTH));
                    if (payment2.after(today)){
                        nextRun.setTimeInMillis(payment2.getTimeInMillis());
                        found = true;
                    }
                }
            } else if (Commitment.FREQUENCY_QUARTERLY.equals(commitment.getFrequency())) {
                nextRun.add(Calendar.MONTH, 3);
            } else if (Commitment.FREQUENCY_TWICE_ANNUALLY.equals(commitment.getFrequency())) {
                nextRun.add(Calendar.MONTH, 6);
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
        Calendar now = Calendar.getInstance();
        Calendar today = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        logger.debug("getToday() = " + today.getTime() + " millis=" + today.getTimeInMillis());
        return today;
    }

    private Calendar getBimonthlyCalendar(int year, int month, int day) {
        Calendar next = new GregorianCalendar();
        next.clear();
        if (month > 11) {
            next.set(year + 1, month - 12, 1);
        } else {
            next.set(year, month, 1);
        }
        int maxMonthDay = next.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (maxMonthDay >= day) {
            next.set(Calendar.DAY_OF_MONTH, day);
        } else {
            next.set(Calendar.DAY_OF_MONTH, maxMonthDay);
        }
        logger.debug("getBimonthlyCalendar() = " + next.getTime() + " millis=" + next.getTimeInMillis());
        return next;
    }
}
