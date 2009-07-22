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

public class ScheduledItemServiceImplTest extends BaseTest {
	
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
    	
    	List<ScheduledItem> items = scheduledItemService.readScheduledItemsBySourceEntity(schedulable);
    	assert items.size() == 0;
    	
    	scheduledItemService.extendSchedule(schedulable);
    	
    	items = scheduledItemService.readScheduledItemsBySourceEntity(schedulable);
    	
    	/*
    	assert items != null;
    	
    	assert items.size() == 6;
    	
    	assert items.get(0).getActualScheduledDate().equals(schedulable.getStartDate());
    	assert items.get(0).getOriginalScheduledDate().equals(schedulable.getStartDate());
    	assert items.get(0).getCompletionDate() == null;
    	assert items.get(0).getResultEntity() == null;
    	assert items.get(0).getResultEntityId() == null;
    	assert items.get(0).getSourceEntity() == RECURRING_GIFT_TYPE;
    	assert items.get(0).getSourceEntityId() == 1;

    	assert items.get(1).getActualScheduledDate().equals(new Date("2009/02/01"));
    	assert items.get(2).getActualScheduledDate().equals(new Date("2009/03/01"));
    	assert items.get(3).getActualScheduledDate().equals(new Date("2009/04/01"));
    	assert items.get(4).getActualScheduledDate().equals(new Date("2009/05/01"));

    	assert items.get(5).getActualScheduledDate().equals(schedulable.getEndDate());
    	*/
    	
    	for (ScheduledItem item : items) scheduledItemService.deleteScheduledItem(item);
    	
    	items = scheduledItemService.readScheduledItemsBySourceEntity(schedulable);
    	//assert items.size() == 0;
    	
    }
    
    

}
