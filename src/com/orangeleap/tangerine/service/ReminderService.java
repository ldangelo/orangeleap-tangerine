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

package com.orangeleap.tangerine.service;

import java.util.Date;
import java.util.List;

import com.orangeleap.tangerine.domain.Schedulable;
import com.orangeleap.tangerine.domain.ScheduledItem;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;

public interface ReminderService {
    
	public void generateDefaultReminders(RecurringGift recurringGift, int initialReminderDays, int maximumReminders, int reminderIntervalDays);

	public List<ScheduledItem> listReminders(RecurringGift recurringGift, Date scheduledPaymentDate);

	public void addReminder(RecurringGift recurringGift, Date scheduledPaymentDate, Date reminderDate);

	public void deleteReminder(RecurringGift recurringGift, Date scheduledPaymentDate, Date reminderDate);

	public List<ScheduledItem> getRemindersToProcess(Date processingDate);  
	
	public Schedulable getParent(ScheduledItem item);
	
}
