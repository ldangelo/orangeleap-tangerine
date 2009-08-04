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

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;

import com.orangeleap.tangerine.dao.RecurringGiftDao;
import com.orangeleap.tangerine.domain.ScheduledItem;
import com.orangeleap.tangerine.domain.paymentInfo.Commitment;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.service.NightlyBatchService;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.service.ScheduledItemService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TaskStack;

@Service("nightlyBatchService")
public class NightlyBatchServiceImpl extends AbstractCommitmentService<RecurringGift> implements NightlyBatchService {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "recurringGiftDAO")
    private RecurringGiftDao recurringGiftDao;

    @Resource(name = "recurringGiftService")
    private RecurringGiftService recurringGiftService;

    @Resource(name = "scheduledItemService")
    private ScheduledItemService scheduledItemService;

    // Non-transactional method
    @Override
    public synchronized void processRecurringGifts() {
        if (logger.isTraceEnabled()) {
            logger.trace("processRecurringGifts:");
        }

        Calendar cal = getToday();
        Date today = cal.getTime();

        cal.add(Calendar.YEAR, -1); // Process missed payments up to a year after end date.
        List<RecurringGift> recurringGifts = recurringGiftDao.readRecurringGifts(cal.getTime(), Arrays.asList(new String[]{Commitment.STATUS_PENDING, Commitment.STATUS_IN_PROGRESS /*, Commitment.STATUS_FULFILLED*/}));

        if (recurringGifts != null) {
            for (RecurringGift recurringGift : recurringGifts) {
            	
            	if (!recurringGift.isActivate()) continue;

            	scheduledItemService.extendSchedule(recurringGift);
            	
            	ScheduledItem item = scheduledItemService.getNextItemToRun(recurringGift);
            	if (item == null || item.getActualScheduledDate().after(today)) continue;  
            	
            	logger.debug("processRecurringGifts: id =" + recurringGift.getId() + ", actualScheduledDate =" + item.getActualScheduledDate());

            	recurringGiftService.processRecurringGift(recurringGift, item);
            	
                try {
                    TaskStack.execute();
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
                
                
            }
        }
    }
    

}
