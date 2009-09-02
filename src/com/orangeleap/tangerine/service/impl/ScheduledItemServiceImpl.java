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
import java.text.SimpleDateFormat;
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
    public void deleteSchedule(Schedulable schedulable) {
        scheduledItemDao.deleteSchedule(schedulable.getType(), schedulable.getId());
    }

    @Override
    public ScheduledItem readScheduledItemById(Long scheduledItemId) {
    	return scheduledItemDao.readScheduledItemById(scheduledItemId);
    }

    @Override
    public List<ScheduledItem> readSchedule(Schedulable schedulable) {
    	List<ScheduledItem> list = scheduledItemDao.readScheduledItemsBySourceEntityId(schedulable.getType(), schedulable.getId());
    	// Populate custom fields
    	for (int i = 0; i < list.size(); i++) {
    		list.set(i, scheduledItemDao.readScheduledItemById(list.get(i).getId()));
    	}
    	return list;
    }

    // Returns null if no items left to run.
    @Override
    public ScheduledItem getNextItemToRun(Schedulable schedulable) {
    	return scheduledItemDao.getNextItemToRun(schedulable.getType(), schedulable.getId());
    }
    
    // Returns all uncompleted items up to and including processingDate for all entities
    @Override
    public List<ScheduledItem> getAllItemsReadyToProcess(String sourceEntity, Date processingDate) {
    	return scheduledItemDao.getItemsReadyToProcess(sourceEntity, processingDate);
    }
    
    // Returns all uncompleted items up to and including processingDate for all entities
    @Override
    public List<ScheduledItem> getAllItemsReadyToProcess(String sourceEntity, String scheduledItemType, Date processingDate) {
    	return scheduledItemDao.getItemsReadyToProcess(sourceEntity, scheduledItemType, processingDate);
    }

    // Returns oldest uncompleted item up to and including processingDate for all entities
    @Override
    public List<ScheduledItem> getNextItemsReadyToProcess(String sourceEntity, Date processingDate) {
    	List<ScheduledItem> list = scheduledItemDao.getItemsReadyToProcess(sourceEntity, processingDate);
    	filterForOldest(list);
    	return list;
    }
    
	// Remove duplicates for id; save only first one for same id. Items must already be in SourceEntityId, ActualScheduledDate order.
    private void filterForOldest(List<ScheduledItem> list) {
    	long lastid = -1;
    	Iterator<ScheduledItem> it = list.iterator();
    	while (it.hasNext()) {
    		ScheduledItem item = it.next();
    		long id = item.getSourceEntityId();
    		if (id == lastid) {
    			it.remove();
    		}
    		lastid = id;
    	}
    }
    
    // ResultEntity is a Gift if the schedulable was a RecurringGift, for example.
    // Completion status is for reference only, for example, Gift Payment Status
    @Override
    public ScheduledItem completeItem(ScheduledItem scheduledItem, AbstractEntity resultEntity, String completionStatus) {
    	scheduledItem.setCompletionDate(new Date());
    	scheduledItem.setCompletionStatus(completionStatus);
    	if (resultEntity != null) {
    		scheduledItem.setResultEntity(resultEntity.getType());
        	scheduledItem.setResultEntityId(resultEntity.getId());
    	}
    	scheduledItemDao.maintainScheduledItem(scheduledItem); // note: this may be the first time this item has been saved
    	return scheduledItem;
    }
    
    @Override
    public ScheduledItem getDefaultScheduledItem(Schedulable schedulable, Date d) {
    	ScheduledItem item = new ScheduledItem();
    	item.setSourceEntity(schedulable.getType());
    	item.setSourceEntityId(schedulable.getId());
    	item.setOriginalScheduledDate(d);
    	item.setActualScheduledDate(d);
    	item.setScheduledItemAmount(schedulable.getSchedulingAmount());
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
    	
    	if (schedulable.getEndDate() != null) toDate = schedulable.getEndDate();  // can only extend at most to end date, if one exists
    	
    	internalExtend(schedulable, toDate);
    	
    }
    
    private Date getNextYear() {
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.YEAR, 1);
    	return cal.getTime();
    }

    @Override
    public void regenerateSchedule(Schedulable schedulable) {
    	regenerateSchedule(schedulable, getNextYear());
    }

    // Deletes uncompleted items and regenerates schedule after last completed item.
    @Override
    public void regenerateSchedule(Schedulable schedulable, Date toDate) {
    	
    	List<ScheduledItem> existingitems = readSchedule(schedulable);
    	
    	// Delete all uncompleted items.
    	Iterator<ScheduledItem> it = existingitems.iterator();
    	while (it.hasNext()) {
    		ScheduledItem item = it.next();
    		if (!item.isCompleted()) {
    			it.remove();
    			scheduledItemDao.deleteScheduledItemById(item.getId());
    		}
    	}
    	
    	internalExtend(schedulable, toDate);
    	
    }
    
    private final static Date PAST_DATE = new Date(0);

    private void internalExtend(Schedulable schedulable, Date toDate) {
    	
    	List<ScheduledItem> existingitems = readSchedule(schedulable);
    	
    	// Get last date used
    	Date afterdate = PAST_DATE;
    	for (ScheduledItem item : existingitems) {
    		if (item.getActualScheduledDate() != null && item.getActualScheduledDate().after(afterdate)) afterdate = item.getActualScheduledDate();
    		if (item.getOriginalScheduledDate() != null && item.getOriginalScheduledDate().after(afterdate)) afterdate = item.getOriginalScheduledDate();
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
    	if (schedulable.getStartDate() == null) return result;
    	
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(schedulable.getStartDate());
    	
    	Date enddate = schedulable.getEndDate();
    	if (enddate == null) enddate = toDate;
    	if (enddate == null) throw new RuntimeException("No end date specified.");
    	
    	while (!cal.getTime().after(enddate)) {
    		
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
    
	private String formatDate(Date d) {
		return new SimpleDateFormat("MM/dd/yyyy").format(d);
	}
	
    private BigDecimal ZERO = BigDecimal.ZERO;

    
    // Prevent double posting a gift.  Checks if gift is already referenced in payment schedule's completed items.
    private boolean wasEntityAlreadyApplied(List<ScheduledItem> schedule, AbstractEntity resultEntity) {
    	
    	if (resultEntity == null || resultEntity.getId() == null) return false;
    	
		for (ScheduledItem scheduledPayment : schedule) {
			if (
					resultEntity.getType().equals(scheduledPayment.getResultEntity())
					&& resultEntity.getId().equals(scheduledPayment.getResultEntityId()) 
				) return true;
		}
		
		return false;
		
    }
    
	@Override
	public void applyPaymentToSchedule(Schedulable schedulable, BigDecimal amount, AbstractEntity resultEntity) {
		
		try {
		
			if (schedulable == null || amount == null) return;

			List<ScheduledItem> schedule = readSchedule(schedulable);

			if (wasEntityAlreadyApplied(schedule, resultEntity)) {
				logger.info("Entity" + resultEntity.getType()+" "+resultEntity.getId()+" was already applied to payment schedule for "+schedulable.getType()+" "+schedulable.getId()+", skipping reapplication.");
				return; 
			}
			
			Date now = new java.util.Date();
		
			
			// Decrement amounts and/or complete remaining scheduled payments.
			
			for (ScheduledItem scheduledPayment : schedule) {
				
				if (amount.compareTo(ZERO) <= 0) break; 
	
				if (canApplyPayment(scheduledPayment)) {
					
					BigDecimal scheduledAmount = scheduledPayment.getScheduledItemAmount();
					
					if (scheduledAmount.compareTo(amount) > 0) {
						
						// Remaining amount to be applied is insufficient to cover the next scheduled payment.  Decrement scheduled amount and stop.
						scheduledPayment.setScheduledItemAmount(scheduledAmount.subtract(amount));
						amount = ZERO;
						maintainScheduledItem(scheduledPayment);
				
					} else {
						
						// Remaining amount to be applied is greater than or matches this scheduled amount.  Complete the scheduled payment.
						completeItem(scheduledPayment, resultEntity, "Applied Payment " + formatDate(now));
						amount = amount.subtract(scheduledAmount);
						
					}
					
				}
				
			}
			
			if (amount.compareTo(ZERO) > 0) {
				// Create a completed payment entry for this payment for today for any unapplied amount remaining.
				// This puts them over the total amount of the commitment, but still allow it.
				createScheduledPayment(schedulable, amount, resultEntity, now, true);
			} else if (amount.compareTo(ZERO) < 0) {
				// An Adjusted Gift has a negative amount.
				// Create a completed payment entry for this negative payment.
				createScheduledPayment(schedulable, amount, resultEntity, now, true);
				// Create a new scheduled payment entry for the reverse of this negative payment.
				// This assumes the adjusted gift amount still needs to be repaid in order to fulfill original commitment amount.
				createScheduledPayment(schedulable, amount.negate(), resultEntity, now, false);
			}
			
			
			
			logger.debug("Excess amount unapplied to pre-existing scheduled payments: " + amount);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Unable to apply payment for " + amount + " to " + schedulable, e);
		}
 		
		
	}
	
	private boolean canApplyPayment(ScheduledItem scheduledPayment){
		return 
		!scheduledPayment.isCompleted() 
		&& scheduledPayment.getActualScheduledDate() != null 
		&& scheduledPayment.getScheduledItemAmount() != null
		&& scheduledPayment.getScheduledItemAmount().compareTo(ZERO) > 0
		;
	}
	
	private ScheduledItem createScheduledPayment(Schedulable schedulable, BigDecimal amount, AbstractEntity resultEntity, Date now, boolean complete) {
		ScheduledItem thisPayment = getDefaultScheduledItem(schedulable, now);
		thisPayment.setScheduledItemAmount(amount);
		if (resultEntity == null) {
			logger.debug("Gift or resultEntity == null for "+schedulable.getType() + " " + schedulable.getId() + ", amount " + amount);
		}
		if (complete) {
			completeItem(thisPayment, resultEntity, "Applied Payment " + formatDate(now));
		} else {
			maintainScheduledItem(thisPayment);
		}
		return thisPayment;
	}

	
}
