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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.Schedulable;
import com.orangeleap.tangerine.domain.ScheduledItem;

public interface ScheduledItemService {
	
    public ScheduledItem maintainScheduledItem(ScheduledItem scheduledItem);

    public void deleteScheduledItem(ScheduledItem scheduledItem);

    public void deleteSchedule(Schedulable schedulable);

    public ScheduledItem readScheduledItemById(Long scheduledItemId);

    public List<ScheduledItem> readSchedule(Schedulable schedulable);

    public ScheduledItem getDefaultScheduledItem(Schedulable schedulable, Date d);
    
    public ScheduledItem getNextItemToRun(Schedulable schedulable);
    
    public void regenerateSchedule(Schedulable schedulable);

    public void regenerateSchedule(Schedulable schedulable, Date toDate);
    
    public void extendSchedule(Schedulable schedulable);
    
    public void extendSchedule(Schedulable schedulable, Date toDate);
    
    public ScheduledItem completeItem(ScheduledItem scheduledItem, AbstractEntity resultEntity, String completionStatus);
    
    public List<ScheduledItem> getAllItemsReadyToProcess(String sourceEntity, Date processingDate);

    public List<ScheduledItem> getAllItemsReadyToProcess(String sourceEntity, String scheduledItemType, Date processingDate);

    public List<ScheduledItem> getNextItemsReadyToProcess(String sourceEntity, Date processingDate);
    
	void applyPaymentToSchedule(Schedulable schedulable, BigDecimal amount, AbstractEntity resultEntity);

}
