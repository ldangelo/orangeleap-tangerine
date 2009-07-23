package com.orangeleap.tangerine.test.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.domain.Schedulable;
import com.orangeleap.tangerine.domain.ScheduledItem;
import com.orangeleap.tangerine.domain.paymentInfo.Commitment;
import com.orangeleap.tangerine.service.ScheduledItemService;
import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.test.SwitchTest;

public class ScheduledItemServiceImplTest 
//extends SwitchTest
extends BaseTest
{
	
	
    @Autowired
    private ScheduledItemService scheduledItemService;

    public final static String RECURRING_GIFT_TYPE = "recurringgift";
    public final static String PLEDGE_TYPE = "pledge";
    public final static String REMINDER = "reminder";

    
    public final static class TestSchedulableImpl implements Schedulable {

    	private Long id;
    	private String type;
    	private String frequency;
    	private Date startDate;
    	private Date endDate;
    	
		public void setId(Long id) {
			this.id = id;
		}
		public Long getId() {
			return id;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getType() {
			return type;
		}
		public void setFrequency(String frequency) {
			this.frequency = frequency;
		}
		public String getFrequency() {
			return frequency;
		}
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		public Date getStartDate() {
			return startDate;
		}
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
		public Date getEndDate() {
			return endDate;
		}
    	
	}
    
    
    @SuppressWarnings("deprecation")
	@Test
    public void testCreateAndDeleteSchedule() throws Exception {
      
    	TestSchedulableImpl schedulable = new TestSchedulableImpl();
    	schedulable.setType(RECURRING_GIFT_TYPE);
    	schedulable.setId(1L);
    	schedulable.setFrequency(Commitment.FREQUENCY_MONTHLY);
    	schedulable.setStartDate(new Date("2009/01/01"));
    	schedulable.setEndDate(new Date("2009/06/01"));
    	
    	scheduledItemService.deleteSchedule(schedulable);
    	
    	List<ScheduledItem> items = scheduledItemService.readSchedule(schedulable);
    	assert items.size() == 0;
    	
    	scheduledItemService.extendSchedule(schedulable);
    	
    	items = scheduledItemService.readSchedule(schedulable);
    	
    	assert items != null;
    	assert items.size() == 6;
    	
    	assert items.get(0).getActualScheduledDate().equals(schedulable.getStartDate());
    	assert items.get(0).getOriginalScheduledDate().equals(schedulable.getStartDate());
    	assert items.get(0).getCompletionDate() == null;
    	assert items.get(0).getResultEntity() == null;
    	assert items.get(0).getResultEntityId() == null;
    	assert items.get(0).getSourceEntity().equals(schedulable.getType());
    	assert items.get(0).getSourceEntityId().equals(schedulable.getId());

    	assert items.get(1).getActualScheduledDate().equals(new Date("2009/02/01"));
    	assert items.get(2).getActualScheduledDate().equals(new Date("2009/03/01"));
    	assert items.get(3).getActualScheduledDate().equals(new Date("2009/04/01"));
    	assert items.get(4).getActualScheduledDate().equals(new Date("2009/05/01"));
    	assert items.get(5).getActualScheduledDate().equals(schedulable.getEndDate());
    	
    	for (ScheduledItem item : items) scheduledItemService.deleteScheduledItem(item);
    	items = scheduledItemService.readSchedule(schedulable);
    	assert items.size() == 0;
    	
    }
    
    private static final String MYVALUE = "myvalue";
    private static final String AVALUE = "avalue";
    
    @SuppressWarnings("deprecation")
	@Test
    public void testModifySchedule() throws Exception {
      
    	TestSchedulableImpl schedulable = new TestSchedulableImpl();
    	schedulable.setType(RECURRING_GIFT_TYPE);
    	schedulable.setId(2L);
    	schedulable.setFrequency(Commitment.FREQUENCY_MONTHLY);
    	schedulable.setStartDate(new Date("2009/01/01"));
    	schedulable.setEndDate(new Date("2009/06/01"));
    	
    	scheduledItemService.deleteSchedule(schedulable);
    	scheduledItemService.extendSchedule(schedulable);
    	
    	// Save a custom field with completion date on one item.
    	List<ScheduledItem> items = scheduledItemService.readSchedule(schedulable);
    	items.get(0).setCompletionDate(new Date());
    	items.get(0).setCustomFieldValue(MYVALUE, AVALUE);
    	scheduledItemService.maintainScheduledItem(items.get(0));
    	items = scheduledItemService.readSchedule(schedulable);
    	assert items.get(0).getCompletionDate() != null;
    	assert items.get(0).getCustomFieldValue(MYVALUE).equals(AVALUE);
    	assert scheduledItemService.getNextItemToRun(schedulable).getActualScheduledDate().equals(new Date("2009/02/01"));
    	
    	// Reschedule remaining payments for second of month
    	schedulable.setStartDate(new Date("2009/02/02"));
    	schedulable.setEndDate(new Date("2009/06/02"));
    	
    	// Should reschedule remaining uncompleted items to second of month, out to end date only.
    	scheduledItemService.regenerateSchedule(schedulable, new Date("2020/12/01"));
    	items = scheduledItemService.readSchedule(schedulable);
    	
    	assert items.size() == 6;
    	assert items.get(0).getCompletionDate() != null;
    	assert items.get(0).getActualScheduledDate().equals(new Date("2009/01/01"));
    	assert items.get(1).getActualScheduledDate().equals(new Date("2009/02/02"));
    	assert items.get(2).getActualScheduledDate().equals(new Date("2009/03/02"));
    	assert items.get(3).getActualScheduledDate().equals(new Date("2009/04/02"));
    	assert items.get(4).getActualScheduledDate().equals(new Date("2009/05/02"));
    	assert items.get(5).getActualScheduledDate().equals(new Date("2009/06/02"));    	

    	// Use an indefinite end date.
    	schedulable.setEndDate(null);
    	scheduledItemService.extendSchedule(schedulable, new Date("2010/01/02"));
    	items = scheduledItemService.readSchedule(schedulable);
    	assert items.size() == 13;

    }
    
    private static void printSchedule(List<ScheduledItem> items) {
    	for (ScheduledItem item: items) System.out.println(""+item);
    }
    
}
