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

import com.orangeleap.tangerine.dao.PledgeDao;
import com.orangeleap.tangerine.dao.RecurringGiftDao;
import com.orangeleap.tangerine.domain.ScheduledItem;
import com.orangeleap.tangerine.domain.paymentInfo.Commitment;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.service.NightlyBatchService;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.service.ReminderService;
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

    @Resource(name = "pledgeDAO")
    private PledgeDao pledgeDao;

    @Resource(name = "pledgeService")
    private PledgeService pledgeService;

    @Resource(name = "reminderService")
    private ReminderService reminderService;

    
    // Non-transactional method
    @Override
    public synchronized void processRecurringGifts() {
    	
        if (logger.isTraceEnabled()) {
            logger.trace("processRecurringGifts:");
        }

        Calendar cal = getToday();
        Date today = cal.getTime();

        // Note: This is looping thru Recurring gifts instead of just calling ScheduledItemService.getNextItemsReadyToProcess() due to legacy data not having schedules set up necessarily.
        cal.add(Calendar.MONTH, -1); // Process missed payments up to a month after end date.
        cal.add(Calendar.DATE, -1); 
        List<RecurringGift> recurringGifts = recurringGiftDao.readRecurringGifts(cal.getTime(), Arrays.asList(new String[]{Commitment.STATUS_PENDING, Commitment.STATUS_IN_PROGRESS }));

        if (recurringGifts != null) {
        	
        	logger.info("Processing "+recurringGifts.size()+" recurring gifts.");
        	long t = System.currentTimeMillis();
        	
            for (RecurringGift recurringGift : recurringGifts) {
            	
            	try {
            	
	            	if (!recurringGift.isActivate()) continue;
	
	            	recurringGiftService.extendPaymentSchedule(recurringGift);
	            	
	            	ScheduledItem item = recurringGiftService.getNextPaymentToRun(recurringGift);
	            	if (item == null || item.getActualScheduledDate().after(today)) continue;  
	            	
	            	logger.debug("processRecurringGifts: id =" + recurringGift.getId() + ", actualScheduledDate =" + item.getActualScheduledDate());
	
	            	recurringGiftService.processRecurringGift(recurringGift, item);
	            	
	                try {
	                    TaskStack.execute();
	                } catch (Exception e) {
	                    logger.error(e.getMessage());
	                }
                
            	} catch (Exception e) {
            		logger.error("Error processing recurring gift "+recurringGift.getId(), e);
            		TaskStack.clear();
            	}
                
            }
            
            logger.info("Recurring gift processing took " + (System.currentTimeMillis() - t)/1000.0f + " sec.");
            
        }
        
        // These calls could be moved to quartz to allow scheduling independently.
        recurringGifts = null; // if calling processReminders() from here, dont hold all this data on the stack
        processPledges();
        processReminders();
        
    }
    
    // Non-transactional method
    // Currently all this does is extend the implied payment schedules (which pledge reminders are based on)
    @Override
    public synchronized void processPledges() {
    	
        if (logger.isTraceEnabled()) {
            logger.trace("processPledges:");
        }

        Calendar cal = getToday();

        // Note: This is looping thru pledges instead of just calling ScheduledItemService.getNextItemsReadyToProcess() due to legacy data not having schedules set up necessarily.
        cal.add(Calendar.MONTH, -1); // Process missed payments up to a month after end date.
        cal.add(Calendar.DATE, -1); 
        List<Pledge> pledges = pledgeDao.readPledges(cal.getTime(), Arrays.asList(new String[]{Commitment.STATUS_PENDING, Commitment.STATUS_IN_PROGRESS }));

        if (pledges != null) {
        	
        	logger.info("Processing "+pledges.size()+" pledges.");
        	long t = System.currentTimeMillis();
        	
            for (Pledge pledge : pledges) {
            	
            	try {
            	
	            	pledgeService.extendPaymentSchedule(pledge);
                
            	} catch (Exception e) {
            		logger.error("Error processing pledge "+pledge.getId(), e);
            		TaskStack.clear();
            	}
                
            }
            
            logger.info("Pledge processing took " + (System.currentTimeMillis() - t)/1000.0f + " sec.");
            
        }
        
    }
    
    // Non-transactional method
    @Override
    public synchronized void processReminders() {
    	
        if (logger.isTraceEnabled()) {
            logger.trace("processReminders:");
        }

        Calendar cal = getToday();
        Date today = cal.getTime();

        List<ScheduledItem> reminders = reminderService.getRemindersToProcess(today);

    	logger.info("Processing "+reminders.size()+" reminders.");        	
    	long t = System.currentTimeMillis();

    	for (ScheduledItem reminder : reminders) {
        	
        	try {
	        	
        		reminderService.processReminder(reminder);
	        	
	            try {
	                TaskStack.execute();
	            } catch (Exception e) {
	                logger.error(e.getMessage());
	            }
	            
        	} catch (Exception e) {
        		logger.error("Error processing reminder "+reminder.getId(), e);
        		TaskStack.clear();
        	}
        	
        }        
                
        logger.info("Reminder processing took " + (System.currentTimeMillis() - t)/1000.0f + " sec.");

    }            
    

}
