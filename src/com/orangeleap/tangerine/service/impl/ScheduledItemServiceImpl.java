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
import org.springframework.validation.BindException;

import com.orangeleap.tangerine.dao.ScheduledItemDao;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.Schedulable;
import com.orangeleap.tangerine.domain.ScheduledItem;
import com.orangeleap.tangerine.domain.paymentInfo.Commitment;
import com.orangeleap.tangerine.service.ScheduledItemService;
import com.orangeleap.tangerine.util.OLLogger;

@Service("scheduledItemService")
@Transactional(propagation = Propagation.REQUIRED)
public class ScheduledItemServiceImpl extends AbstractTangerineService implements ScheduledItemService {
	

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "scheduledItemDAO")
    private ScheduledItemDao scheduledItemDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {BindException.class})
    public ScheduledItem maintainScheduledItem(ScheduledItem scheduledItem) {
    	return scheduledItemDao.maintainScheduledItem(scheduledItem);
    }

    @Override
    public void deleteScheduledItem(ScheduledItem scheduledItem) {
        scheduledItemDao.deleteScheduledItemById(scheduledItem.getId());
    }

    @Override
    public ScheduledItem readScheduledItemById(Long scheduledItemId) {
    	return scheduledItemDao.readScheduledItemById(scheduledItemId);
    }

    @Override
    public List<ScheduledItem> readScheduledItemsBySourceEntityId(Schedulable scheduleable) {
    	return scheduledItemDao.readScheduledItemsBySourceEntityId(scheduleable.getType(), scheduleable.getId());
    }

    @Override
    public ScheduledItem getNextItemToRun(Schedulable scheduleable) {
    	List<ScheduledItem> items = scheduledItemDao.readScheduledItemsBySourceEntityId(scheduleable.getType(), scheduleable.getId());
    	// Items are in order of actual scheduled date
    	for (ScheduledItem item : items) {
    		if (item.getCompletionDate() == null && item.getActualScheduledDate() != null) return item;
    	}
    	return null;
    }
    
    // ResultEntity is a Gift if the schedulable was a RecurringGift, for example.
    // Completion status is for reference only, for example, Gift Payment Status
    @Override
    public ScheduledItem completeItem(ScheduledItem scheduledItem, AbstractEntity resultEntity, String completionStatus) {
    	scheduledItem.setCompletionDate(new Date());
    	scheduledItem.setCompletionStatus(completionStatus);
    	scheduledItem.setResultEntity(resultEntity.getType());
    	scheduledItem.setResultEntityId(resultEntity.getId());
    	scheduledItemDao.maintainScheduledItem(scheduledItem);
    	return scheduledItem;
    }
    
    @Override
    public ScheduledItem getDefaultScheduledItem(Schedulable schedulable, Date d) {
    	ScheduledItem item = new ScheduledItem();
    	item.setSourceEntity(schedulable.getType());
    	item.setSourceEntityId(schedulable.getId());
    	item.setOriginalScheduledDate(d);
    	item.setActualScheduledDate(d);
    	return item;
    }
    
    // Preserve schedule edits for uncompleted items.  
    // Extends schedule to toDate for schedulables with no (indefinite) end date one year.
    @Override
    public void extendSchedule(Schedulable schedulable) {
    	extendSchedule(schedulable, getNextYear());
    }
    
    // Preserve schedule edits for uncompleted items.  
    // Extends schedule to toDate for schedulables with no (indefinite) end date to specified date.
    @Override
    public void extendSchedule(Schedulable schedulable, Date toDate) {
    	
    	if (schedulable.getEndDate() != null) return;  // can only extend if no end date
    	
    	internalExtend(schedulable, toDate);
    	
    }
    
    private Date getNextYear() {
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.YEAR, 1);
    	return cal.getTime();
    }

    // Deletes uncompleted items and regenerates schedule after last completed item.
    @Override
    public void regenerateSchedule(Schedulable schedulable, Date toDate) {
    	
    	List<ScheduledItem> existingitems = readScheduledItemsBySourceEntityId(schedulable);
    	
    	// Delete all uncompleted items.
    	Iterator<ScheduledItem> it = existingitems.iterator();
    	while (it.hasNext()) {
    		ScheduledItem item = it.next();
    		if (item.getCompletionDate() == null) {
    			it.remove();
    			scheduledItemDao.deleteScheduledItemById(item.getId());
    		}
    	}
    	
    	internalExtend(schedulable, toDate);
    	
    }
    
    private final static Date PAST_DATE = new Date(0);

    private void internalExtend(Schedulable schedulable, Date toDate) {
    	
    	List<ScheduledItem> existingitems = readScheduledItemsBySourceEntityId(schedulable);
    	
    	Date afterdate = PAST_DATE;
    	if (existingitems.size() > 0) {
    		afterdate = existingitems.get(existingitems.size() - 1).getActualScheduledDate();
    	}
    	
    	List<Date> newdates = getDateList(schedulable, toDate);
    	
    	for (Date d : newdates) {
    		if (d.after(afterdate)) {
    			ScheduledItem item = getDefaultScheduledItem(schedulable, d);
    			scheduledItemDao.maintainScheduledItem(item);
    		}
    	}
    	
    }
    
    private List<Date> getDateList(Schedulable schedulable, Date toDate) {
    	
    	List<Date> result = new ArrayList<Date>();
    	if (schedulable == null) return result;
    
    	String frequency = schedulable.getFrequency();
    	if (Commitment.FREQUENCY_UNSPECIFIED.equals(frequency)) return result;
    	
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(schedulable.getStartDate());
    	
    	Date enddate = schedulable.getEndDate();
    	if (enddate == null) enddate = toDate;
    	if (enddate == null) throw new RuntimeException("No end date specified.");
    	
    	while (!cal.after(enddate)) {
    		
    		result.add(cal.getTime());
    		
	        if (Commitment.FREQUENCY_WEEKLY.equals(frequency)) {
	            cal.add(Calendar.WEEK_OF_MONTH, 1);
	        } else if (Commitment.FREQUENCY_MONTHLY.equals(frequency)) {
	            cal.add(Calendar.MONTH, 1);
	        } else if (Commitment.FREQUENCY_QUARTERLY.equals(frequency)) {
	            cal.add(Calendar.MONTH, 3);
	        } else if (Commitment.FREQUENCY_TWICE_ANNUALLY.equals(frequency)) {
	            cal.add(Calendar.MONTH, 6);
	        } else if (Commitment.FREQUENCY_ANNUALLY.equals(frequency)) {
	            cal.add(Calendar.YEAR, 1);
	        } else if (Commitment.FREQUENCY_ONE_TIME.equals(frequency)) {
	        	break;
	        } else {
	        	break;
	        }
        
    	}
    	
    	return result;
        
    }

}
