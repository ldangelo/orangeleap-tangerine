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
import java.util.GregorianCalendar;
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
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.NightlyBatchService;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.service.ReminderService;
import com.orangeleap.tangerine.service.rollup.RollupService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.util.TaskStack;

// Does not extend base class to avoid getting a transactional attribute
@Service("nightlyBatchService")
public class NightlyBatchServiceImpl implements NightlyBatchService 
{

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

    @Resource(name = "constituentService")
    private ConstituentService constituentService;

    @Resource(name = "rollupService")
    private RollupService rollupService;

    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;
    
    private String getSiteName() {
    	return tangerineUserHelper.lookupUserSiteName();
    }
    
    protected Calendar getToday() {
        Calendar now = Calendar.getInstance();
        return new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
    }


    
    // Non-transactional method
    @Override
    public synchronized void processRecurringGifts() {
    	
    	((OLLogger)logger).logFreeMemory();
    	
		int totalContituentCount = constituentService.getConstituentCountBySite();
		logger.info("Total number of constituents for " + this.getSiteName() + " = " +totalContituentCount);

    	
    	internalProcessRecurringGifts();
        processPledges();
        processReminders();
        
        // May move this to a separate quartz task
        processRollups();
        
    }
    
    private void processRollups() {
    	
    	try {
    		
    		logger.info("Processing rollups for "+getSiteName());
	    	long t = System.currentTimeMillis();

	    	
	    	rollupService.updateAllRollupsForSite();  // updating all stats for now until any legacy data is accounted for
	    	//rollupService.updateSummaryRollupsForSite();  // This would only do the summary totals
	    	
	        
	    	logger.info("Rollup processing took " + (System.currentTimeMillis() - t)/1000.0f + " sec.");
	    	
    	} catch (Exception e) {
        	e.printStackTrace();
        	logger.error("Error in processRollups()", e);
        }
    	
    }
    	
    // Non-transactional method
    private void internalProcessRecurringGifts() {
    	
        try {
        
            if (logger.isTraceEnabled()) {
                logger.trace("processRecurringGifts:");
            }

	        Calendar cal = getToday();
	        Date today = cal.getTime();
	
	        // Note: This loops thru Recurring gifts since previous data may not have schedules set up.
	        cal.add(Calendar.MONTH, -1); // Process missed payments up to a month after end date.
	        cal.add(Calendar.DATE, -1); 
	        List<String> statusList = Arrays.asList(new String[]{Commitment.STATUS_PENDING, Commitment.STATUS_IN_PROGRESS });
	        
	        long count = recurringGiftDao.readRecurringGiftsCount(cal.getTime(), statusList);
	        int limit = 100;
	
	        logger.info("Processing "+count+" recurring gifts.");
	    	long t = System.currentTimeMillis();
	    	
	        for (long start = 0; start < count; start += limit) {
	        
	        	List<RecurringGift> recurringGifts = recurringGiftDao.readRecurringGifts(cal.getTime(), statusList, start, limit);
	
		        if (recurringGifts != null) {
		        	
		            for (RecurringGift recurringGift : recurringGifts) {
		            	
	            		TaskStack.clear();
	            		
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
		            
		        }
	        
	        }
	        
	        logger.info("Recurring gift processing took " + (System.currentTimeMillis() - t)/1000.0f + " sec.");
        
        } catch (Exception e) {
        	e.printStackTrace();
        	logger.error("Error in internalProcessRecurringGifts()", e);
        }
        
    }
    
    // Non-transactional method
    // Currently all this does is extend the implied payment schedules (which pledge reminders are based on)
    @Override
    public synchronized void processPledges() {
    	
    	try {
    	
	        if (logger.isTraceEnabled()) {
	            logger.trace("processPledges:");
	        }
	
	        Calendar cal = getToday();
	
	        // Note: This is looping thru pledges instead of just calling ScheduledItemService.getNextItemsReadyToProcess() due to legacy data not having schedules set up necessarily.
	        cal.add(Calendar.MONTH, -1); // Process missed payments up to a month after end date.
	        cal.add(Calendar.DATE, -1); 
	        
	        List<String> statusList = Arrays.asList(new String[]{Commitment.STATUS_PENDING, Commitment.STATUS_IN_PROGRESS });
	        
	        long count = pledgeDao.readPledgesCount(cal.getTime(), statusList);
	        int limit = 100;
	
	    	logger.info("Processing "+count+" pledges.");
	    	long t = System.currentTimeMillis();
	    	
	        for (long start = 0; start < count; start += limit) {
	        	
		        List<Pledge> pledges = pledgeDao.readPledges(cal.getTime(), statusList, start, limit);
		
		        if (pledges != null) {
		        	
		        	
		            for (Pledge pledge : pledges) {
		            	
	            		TaskStack.clear();
	            		
		            	try {
		            	
			            	pledgeService.extendPaymentSchedule(pledge);
		                
				            try {
				                TaskStack.execute();
				            } catch (Exception e) {
				                logger.error(e.getMessage());
				            }
				            
		            	} catch (Exception e) {
		            		logger.error("Error processing pledge "+pledge.getId(), e);
		            		TaskStack.clear();
		            	}
		                
		            }
		            
		            
		        }
	        
	        }
	        
	        logger.info("Pledge processing took " + (System.currentTimeMillis() - t)/1000.0f + " sec.");
        
    	} catch (Exception e) {
    		e.printStackTrace();
    		logger.error("Error in processPledges()", e);
    	}
        
    }
    
    // Non-transactional method
    @Override
    public synchronized void processReminders() {
    	
    	try {
    	
	        if (logger.isTraceEnabled()) {
	            logger.trace("processReminders:");
	        }
	
	        Calendar cal = getToday();
	        Date today = cal.getTime();
	
	        List<ScheduledItem> reminders = reminderService.getRemindersToProcess(today);
	
	    	logger.info("Processing "+reminders.size()+" reminders.");        	
	    	long t = System.currentTimeMillis();
	
	    	for (ScheduledItem reminder : reminders) {
	        	
	    		TaskStack.clear();
	    		
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
        
    	} catch (Exception e) {
    		e.printStackTrace();
    		logger.error("Error in processReminders()", e);
    	}

    }            
    

}
