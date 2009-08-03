/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.service.impl;

import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.Commitment;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service("commitmentService")
@Transactional(propagation = Propagation.REQUIRED)
public abstract class AbstractCommitmentService<T extends Commitment> extends AbstractPaymentService {

    /**
     * Logger for this class and subclasses
     */
    protected final static Log logger = OLLogger.getLog(AbstractCommitmentService.class);

    @Resource(name = "giftService")
    protected GiftService giftService;

    @Resource(name = "fieldDAO")
    private FieldDao fieldDao;

    public void createDefault(Constituent constituent, T commitment, EntityType entityType, String lineIdProperty) {
        if (logger.isTraceEnabled()) {
            logger.trace("createDefaultCommitment: constituent = " + (constituent == null ? null : constituent.getId()));
        }
        List<DistributionLine> lines = new ArrayList<DistributionLine>(1);
        DistributionLine line = new DistributionLine(constituent);
        BeanWrapper lineWrapper = PropertyAccessorFactory.forBeanPropertyAccess(line);
        lineWrapper.setPropertyValue(lineIdProperty, commitment.getId());
        lines.add(line);
        commitment.setDistributionLines(lines);
        commitment.setConstituent(constituent);
    }

    protected Date getNextGiftDate(T commitment) {
        Date nextGiftDate = new Date();
        if (commitment.getEndDate() != null && commitment.getEndDate().before(Calendar.getInstance().getTime())) {
            nextGiftDate = null;
        } else if (commitment instanceof RecurringGift && ((RecurringGift) commitment).isActivate()) {
            nextGiftDate = calculateNextRunDate(commitment);
        } else {
            nextGiftDate = null;
        }

        if (nextGiftDate != null && logger.isDebugEnabled()) {
            logger.debug("it is currently, " + Calendar.getInstance().getTime() + ", running again at " + nextGiftDate);
        }
        return nextGiftDate;
    }

    protected Date calculateNextRunDate(T commitment) {
        Calendar nextRun = new GregorianCalendar();
        nextRun.setTimeInMillis(((RecurringGift) commitment).getNextRunDate().getTime());
        logger.debug("start date = " + nextRun.getTime() + " millis = " + nextRun.getTimeInMillis());
        Calendar today = getToday();


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

//        if (nextRun == null || (commitment.getEndDate() != null && nextRun.getTime().after(commitment.getEndDate()))) {
//            nextRun = null;
//            logger.debug("no next run scheduled");
//            return null;
//        }
        return nextRun.getTime();
    }

    protected void setCommitmentStatus(T commitment, String statusPropertyName) {
        if (commitment.getAmountPaid() != null) {
            BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(commitment);
            if (bw.isWritableProperty(statusPropertyName)) {
                if (commitment.getAmountTotal() != null) {
                    if (commitment.getAmountPaid().compareTo(commitment.getAmountTotal()) == 0 || commitment.getAmountPaid().compareTo(commitment.getAmountTotal()) == 1) {
                        bw.setPropertyValue(statusPropertyName, Commitment.STATUS_FULFILLED);
                    } else if (bw.isReadableProperty(statusPropertyName) &&
                            Commitment.STATUS_FULFILLED.equals(bw.getPropertyValue(statusPropertyName)) &&
                            commitment.getAmountPaid().compareTo(commitment.getAmountTotal()) == -1) {
                        bw.setPropertyValue(statusPropertyName, Commitment.STATUS_IN_PROGRESS);
                    } else if (bw.isReadableProperty(statusPropertyName) &&
                            Commitment.STATUS_PENDING.equals(bw.getPropertyValue(statusPropertyName)) &&
                            commitment.getAmountPaid().compareTo(BigDecimal.ZERO) == 1) {
                        bw.setPropertyValue(statusPropertyName, Commitment.STATUS_IN_PROGRESS);
                    }
                } else if (bw.isReadableProperty(statusPropertyName) &&
                        Commitment.STATUS_PENDING.equals(bw.getPropertyValue(statusPropertyName)) &&
                        commitment.getAmountPaid().compareTo(BigDecimal.ZERO) == 1) {
                    bw.setPropertyValue(statusPropertyName, Commitment.STATUS_IN_PROGRESS);
                }
            }
        }
    }

    protected Calendar getToday() {
        Calendar now = Calendar.getInstance();
        Calendar today = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        logger.trace("getToday() = " + today.getTime() + " millis=" + today.getTimeInMillis());
        return today;
    }
}
