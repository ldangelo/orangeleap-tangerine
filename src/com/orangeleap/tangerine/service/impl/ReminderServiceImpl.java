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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.Schedulable;
import com.orangeleap.tangerine.domain.ScheduledItem;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.service.ReminderService;
import com.orangeleap.tangerine.service.ScheduledItemService;
import com.orangeleap.tangerine.util.OLLogger;

import edu.emory.mathcs.backport.java.util.Collections;

@Service("reminderService")
@Transactional(propagation = Propagation.REQUIRED)
public class ReminderServiceImpl extends AbstractTangerineService implements ReminderService {
	

    protected final Log logger = OLLogger.getLog(getClass());
    
    public final static String REMINDER = "reminder";

    @Resource(name = "scheduledItemService")
    private ScheduledItemService scheduledItemService;
    
    @Resource(name = "recurringGiftService")
    private RecurringGiftService recurringGiftService;
    
    @Resource(name = "pledgeService")
    private PledgeService pledgeService;
    
	@Override
	public List<ScheduledItem> listReminders(RecurringGift recurringGift, Date scheduledPaymentDate) {
		ScheduledItem scheduledPayment = locateScheduledItemByDate(recurringGift, scheduledPaymentDate);
		return scheduledItemService.readSchedule(scheduledPayment);
	}
	
	@Override
	public void addReminder(RecurringGift recurringGift, Date scheduledPaymentDate, Date reminderDate) {
		ScheduledItem scheduledPayment = locateScheduledItemByDate(recurringGift, scheduledPaymentDate);
		ScheduledItem reminder = getReminder(scheduledPayment, reminderDate);
		scheduledItemService.maintainScheduledItem(reminder);
	}

	@Override
	public void deleteReminder(RecurringGift recurringGift, Date scheduledPaymentDate, Date reminderDate) {
		ScheduledItem scheduledPayment = locateScheduledItemByDate(recurringGift, scheduledPaymentDate);
		ScheduledItem reminder = locateScheduledItemByDate(scheduledPayment, reminderDate);
		scheduledItemService.deleteScheduledItem(reminder);
	}
	
	@Override
	public void deleteReminders(RecurringGift recurringGift) {
		List<ScheduledItem> scheduledPayments = scheduledItemService.readSchedule(recurringGift);
		for (ScheduledItem scheduledPayment: scheduledPayments) {
			scheduledItemService.deleteSchedule(scheduledPayment);
		}
	}

	// Locate by date. Should not have two for same date for scheduled payments and reminders.
	private ScheduledItem locateScheduledItemByDate(Schedulable schedulable, Date date) {
		List<ScheduledItem> list = scheduledItemService.readSchedule(schedulable);
		for (ScheduledItem item : list) {
			if (item.getActualScheduledDate().equals(date)) return item;
		}
		throw new RuntimeException("No scheduled item exists for "+date);
	}

	@Override
	public void generateDefaultReminders(RecurringGift recurringGift) {
		ReminderInfo ri = new ReminderInfo(recurringGift);
	    if (ri.isGenerateReminders()) generateDefaultReminders(recurringGift, ri.getInitialReminder(), ri.getMaximumReminders(), ri.getReminderIntervalDays());
	}

	@Override
	public void generateDefaultReminders(RecurringGift recurringGift, Date scheduledPaymentDate) {
		ReminderInfo ri = new ReminderInfo(recurringGift);
	    if (ri.isGenerateReminders()) generateDefaultReminders(recurringGift, scheduledPaymentDate, ri.getInitialReminder(), ri.getMaximumReminders(), ri.getReminderIntervalDays());
	}

	private void generateDefaultReminders(RecurringGift recurringGift, int initialReminderDays, int maximumReminders, int reminderIntervalDays) {
		List<ScheduledItem> scheduledPayments = scheduledItemService.readSchedule(recurringGift);
		for (ScheduledItem scheduledPayment : scheduledPayments) {
			generateDefaultReminders(scheduledPayment, initialReminderDays, maximumReminders, reminderIntervalDays);
		}
	}
	
	private void generateDefaultReminders(RecurringGift recurringGift, Date scheduledPaymentDate, int initialReminderDays, int maximumReminders, int reminderIntervalDays) {
		ScheduledItem scheduledPayment = locateScheduledItemByDate(recurringGift, scheduledPaymentDate);
		generateDefaultReminders(scheduledPayment, initialReminderDays, maximumReminders, reminderIntervalDays);
	}
	
    private final static Date PAST_DATE = new Date(0);
	
	private void generateDefaultReminders(ScheduledItem scheduledPayment, int initialReminderDays, int maximumReminders, int reminderIntervalDays) {

		if (maximumReminders > 100) maximumReminders = 0;

		List<ScheduledItem> existingReminders = scheduledItemService.readSchedule(scheduledPayment);
		
    	// Get last reminder date used
    	Date afterdate = PAST_DATE;
    	for (ScheduledItem item : existingReminders) {
    		if (item.getActualScheduledDate() != null && item.getActualScheduledDate().after(afterdate)) afterdate = item.getActualScheduledDate();
    		if (item.getOriginalScheduledDate() != null && item.getOriginalScheduledDate().after(afterdate)) afterdate = item.getOriginalScheduledDate();
    	}
		
		List<Date> datelist = getDateList(scheduledPayment, initialReminderDays, maximumReminders, reminderIntervalDays);
		
		for (Date reminderDate : datelist) {
			if (reminderDate.after(afterdate)) {
				ScheduledItem reminder = getReminder(scheduledPayment, reminderDate);
				scheduledItemService.maintainScheduledItem(reminder);
			}
		}
	}
	
	private List<Date> getDateList(ScheduledItem scheduledItem, int initialReminderDays, int maximumReminders, int reminderIntervalDays) {
		
		List<Date> datelist = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(scheduledItem.getActualScheduledDate());
		Date paymentDate = cal.getTime();
		cal.add(Calendar.DATE, -initialReminderDays);
		for (int i = 0; i < maximumReminders; i++) {
			datelist.add(cal.getTime());
			cal.add(Calendar.DATE, reminderIntervalDays);
			if (!cal.getTime().before(paymentDate)) break;
		}
		return datelist;
	}

	private ScheduledItem getReminder(ScheduledItem scheduledPayment, Date reminderDate) {
		ScheduledItem reminder = scheduledItemService.getDefaultScheduledItem(scheduledPayment, reminderDate);
		reminder.setScheduledItemType(REMINDER);
		return reminder;
	}
	
	@Override
	public List<ScheduledItem> getRemindersToProcess(Date processingDate) {
		
		List<ScheduledItem> list = scheduledItemService.getAllItemsReadyToProcess("scheduleditem", REMINDER, processingDate);
		removeDuplicatesAndCompletedPayments(list);
		return list;
		
	}
	
	// Delete reminders if corresponding scheduled payment is completed, or more than one reminder for the same schedulePayment is ready to be sent.
	private void removeDuplicatesAndCompletedPayments(List<ScheduledItem> list) {
		
		Collections.reverse(list); // Save only the last dated reminder instead of the first one in the case of duplicates.
		Long lastid = new Long(-1);
		Iterator<ScheduledItem> it = list.iterator(); 
		while (it.hasNext()) {
			ScheduledItem item = it.next();
			ScheduledItem scheduledPayment = scheduledItemService.readScheduledItemById(item.getSourceEntityId());
			if (scheduledPayment == null || scheduledPayment.isCompleted() || scheduledPayment.getId().equals(lastid)) {
				scheduledItemService.deleteScheduledItem(item);
				it.remove();
			}
			lastid = scheduledPayment.getId();
		}
		Collections.reverse(list);
		
	}

	@Override
	public Schedulable getParent(ScheduledItem item) {
		String parentType = item.getSourceEntity();
		if (parentType.equals("scheduleditem")) return scheduledItemService.readScheduledItemById(item.getSourceEntityId());
		if (parentType.equals("recurringgift")) return recurringGiftService.readRecurringGiftById(item.getSourceEntityId());
		if (parentType.equals("pledge")) return pledgeService.readPledgeById(item.getSourceEntityId());
		return null;
	}
	
	// Parses custom fields on RecurringGift to determine if using reminders
	public static final class ReminderInfo {
    	
		private int initialReminder = 0;
    	private int maximumReminders = 0;
    	private int reminderIntervalDays = 0;
    	private boolean valid = true;

    	public ReminderInfo(AbstractCustomizableEntity entity) {
			try {
	    		String sinitialReminder = entity.getCustomFieldValue("initialReminder");
	    		if (sinitialReminder != null) {
	    			setInitialReminder(Integer.valueOf(sinitialReminder));
	    		}
	    		String smaximumReminders = entity.getCustomFieldValue("maximumReminders");
	    		if (smaximumReminders != null) {
	    			setMaximumReminders(Integer.valueOf(smaximumReminders));
	    		}
	    		String sreminderIntervalDays = entity.getCustomFieldValue("reminderIntervalDays");
	    		if (sreminderIntervalDays != null) {
	    			setReminderIntervalDays(Integer.valueOf(sreminderIntervalDays));
	    		}
	    	} catch (Exception e) {
	    		// Should already be validated as numeric
	    		valid = false;
	    	}
		}
    	
    	public boolean isGenerateReminders() {
    		return 
    		valid 
    		&& initialReminder != 0 
    		&& maximumReminders > 0 
    		&& ( reminderIntervalDays > 0 || (reminderIntervalDays == 0 && maximumReminders == 1));
    	}

		public void setInitialReminder(int initialReminder) {
			this.initialReminder = initialReminder;
		}

		public int getInitialReminder() {
			return initialReminder;
		}

		public void setMaximumReminders(int maximumReminders) {
			this.maximumReminders = maximumReminders;
		}

		public int getMaximumReminders() {
			return maximumReminders;
		}

		public void setReminderIntervalDays(int reminderIntervalDays) {
			this.reminderIntervalDays = reminderIntervalDays;
		}

		public int getReminderIntervalDays() {
			return reminderIntervalDays;
		}
		
	}

}
