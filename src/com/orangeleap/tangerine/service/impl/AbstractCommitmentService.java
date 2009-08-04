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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.Commitment;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.util.OLLogger;

@Service("commitmentService")
@Transactional(propagation = Propagation.REQUIRED)
public abstract class AbstractCommitmentService<T extends Commitment> extends AbstractPaymentService {

    /**
     * Logger for this class and subclasses
     */
    protected final static Log logger = OLLogger.getLog(AbstractCommitmentService.class);

    @Resource(name = "giftService")
    protected GiftService giftService;

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
