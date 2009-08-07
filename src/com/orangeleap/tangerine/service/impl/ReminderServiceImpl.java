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
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.domain.Schedulable;
import com.orangeleap.tangerine.domain.ScheduledItem;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.service.ReminderService;
import com.orangeleap.tangerine.service.ScheduledItemService;
import com.orangeleap.tangerine.util.OLLogger;

@Service("reminderService")
@Transactional(propagation = Propagation.REQUIRED)
public class ReminderServiceImpl extends AbstractTangerineService implements ReminderService {
	

    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "scheduledItemService")
    private ScheduledItemService scheduledItemService;
    
	@Override
	public List<ScheduledItem> listReminders(RecurringGift recurringGift, Date scheduledPaymentDate) {
		ScheduledItem scheduledPayment = locateScheduledItemByDate(recurringGift, scheduledPaymentDate);
		return scheduledItemService.readSchedule(scheduledPayment);
	}
	
	@Override
	public void addReminder(RecurringGift recurringGift, Date scheduledPaymentDate, Date reminderDate) {
		ScheduledItem scheduledPayment = locateScheduledItemByDate(recurringGift, scheduledPaymentDate);
		ScheduledItem reminder = scheduledItemService.getDefaultScheduledItem(scheduledPayment, reminderDate);
		scheduledItemService.maintainScheduledItem(reminder);
	}

	@Override
	public void deleteReminder(RecurringGift recurringGift, Date scheduledPaymentDate, Date reminderDate) {
		ScheduledItem scheduledPayment = locateScheduledItemByDate(recurringGift, scheduledPaymentDate);
		ScheduledItem reminder = locateScheduledItemByDate(scheduledPayment, reminderDate);
		scheduledItemService.deleteScheduledItem(reminder);
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
	public void generateDefaultReminders(RecurringGift recurringGift, int initialReminderDays, int maximumReminders, int reminderIntervalDays) {
		List<ScheduledItem> scheduledPayments = scheduledItemService.readSchedule(recurringGift);
		for (ScheduledItem scheduledPayment : scheduledPayments) {
			generateDefaultReminders(scheduledPayment, initialReminderDays, maximumReminders, reminderIntervalDays);
		}
	}
	
	private void generateDefaultReminders(ScheduledItem scheduledPayment, int initialReminderDays, int maximumReminders, int reminderIntervalDays) {
		List<Date> datelist = getDateList(scheduledPayment, initialReminderDays, maximumReminders, reminderIntervalDays);
		for (Date reminderDate : datelist) {
			ScheduledItem reminder = scheduledItemService.getDefaultScheduledItem(scheduledPayment, reminderDate);
			scheduledItemService.maintainScheduledItem(reminder);
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

	@Override
	public Map<Schedulable, Map<ScheduledItem, ScheduledItem>> getRemindersToProcess(Date processingDate) {
		// TODO
		return null;
	}


}
