package com.mpower.service.impl;

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
import com.mpower.service.GiftService;
import com.mpower.service.RecurringGiftService;
import com.mpower.type.GiftEntryType;

@Service("recurringGiftService")
@Transactional(propagation = Propagation.REQUIRED)
public class RecurringGiftServiceImpl implements RecurringGiftService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "giftService")
    private GiftService giftService;

    @Resource(name = "recurringGiftDao")
    private RecurringGiftDao recurringGiftDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public List<RecurringGift> readRecurringGifts(Date date, List<String> statuses) {
        return recurringGiftDao.readRecurringGifts(date, statuses);
    }

    @Transactional(propagation = Propagation.REQUIRED)
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
        Gift gift = giftService.createGift(commitment, GiftEntryType.AUTO);
        gift = giftService.maintainGift(gift);
        commitment.setLastEntryDate(gift.getTransactionDate());
    }

    private Date getNextRecurringGiftDate(Commitment commitment) {
        Date nextGiftDate = new Date();
        if (commitment.getEndDate() != null && commitment.getEndDate().before(Calendar.getInstance().getTime())) {
            nextGiftDate = null;
        } else if (Commitment.STATUS_ACTIVE.equals(commitment.getStatus())) {
            nextGiftDate = calculateNextRunDate(commitment);
        } else if (Commitment.STATUS_FULFILLED.equals(commitment.getStatus())) {
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
        Calendar nextRun = new GregorianCalendar();
        nextRun.setTimeInMillis(commitment.getStartDate().getTime());
        logger.debug("start date = " + nextRun.getTime() + " millis = " + nextRun.getTimeInMillis());
        Calendar today = getToday();
        if (Commitment.FREQUENCY_TWICE_MONTHLY.equals(commitment.getFrequency())) {
            Calendar startDateCal = new GregorianCalendar();
            startDateCal.setTimeInMillis(commitment.getStartDate().getTime());
            Calendar firstGiftCal = new GregorianCalendar(startDateCal.get(Calendar.YEAR), startDateCal.get(Calendar.MONTH), startDateCal.get(Calendar.DAY_OF_MONTH));
            boolean found = false;
            boolean pastEndDate = false;
            if (firstGiftCal.after(today)) {
                nextRun.setTimeInMillis(firstGiftCal.getTimeInMillis());
                pastEndDate = CommitmentServiceImpl.isPastEndDate(commitment, nextRun.getTime());
                found = !pastEndDate;
            }
            if (!found && !pastEndDate) {
                Calendar secondGiftCal = new GregorianCalendar();
                secondGiftCal.setTimeInMillis(firstGiftCal.getTimeInMillis() + (1000 * 60 * 60 * 24 * 15));
                if (secondGiftCal.after(today)) {
                    nextRun.setTimeInMillis(secondGiftCal.getTimeInMillis());
                    pastEndDate = CommitmentServiceImpl.isPastEndDate(commitment, nextRun.getTime());
                    found = !pastEndDate;
                }
                int i = 0;
                while (!found && !pastEndDate) {
                    i++;
                    Calendar payment1 = CommitmentServiceImpl.getBimonthlyCalendar(firstGiftCal.get(Calendar.YEAR), firstGiftCal.get(Calendar.MONTH) + i, firstGiftCal.get(Calendar.DAY_OF_MONTH));
                    if (payment1.after(today)) {
                        nextRun.setTimeInMillis(payment1.getTimeInMillis());
                        pastEndDate = CommitmentServiceImpl.isPastEndDate(commitment, nextRun.getTime());
                        found = !pastEndDate;
                    }
                    Calendar payment2 = CommitmentServiceImpl.getBimonthlyCalendar(secondGiftCal.get(Calendar.YEAR), secondGiftCal.get(Calendar.MONTH) + i, secondGiftCal.get(Calendar.DAY_OF_MONTH));
                    if (payment2.after(today)) {
                        nextRun.setTimeInMillis(payment2.getTimeInMillis());
                        pastEndDate = CommitmentServiceImpl.isPastEndDate(commitment, nextRun.getTime());
                        found = !pastEndDate;
                    }
                }
            }
        } else {
            while ((nextRun != null) && (nextRun.before(today) || (commitment.getLastEntryDate() != null && !nextRun.getTime().after(commitment.getLastEntryDate())))) {
                if (Commitment.FREQUENCY_WEEKLY.equals(commitment.getFrequency())) {
                    nextRun.add(Calendar.WEEK_OF_MONTH, 1);
                } else if (Commitment.FREQUENCY_MONTHLY.equals(commitment.getFrequency())) {
                    nextRun.add(Calendar.MONTH, 1);
                } else if (Commitment.FREQUENCY_QUARTERLY.equals(commitment.getFrequency())) {
                    nextRun.add(Calendar.MONTH, 3);
                } else if (Commitment.FREQUENCY_TWICE_ANNUALLY.equals(commitment.getFrequency())) {
                    nextRun.add(Calendar.MONTH, 6);
                } else if (Commitment.FREQUENCY_ANNUALLY.equals(commitment.getFrequency())) {
                    nextRun.add(Calendar.YEAR, 1);
                } else {
                    nextRun = null;
                }
            }
        }
        if (nextRun == null || (commitment.getLastEntryDate() != null && !nextRun.getTime().after(commitment.getLastEntryDate()))) {
            nextRun = null;
            logger.debug("no next run scheduled");
            return null;
        }
        return nextRun.getTime();
    }

    private Calendar getToday() {
        Calendar now = Calendar.getInstance();
        Calendar today = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        logger.debug("getToday() = " + today.getTime() + " millis=" + today.getTimeInMillis());
        return today;
    }
}
