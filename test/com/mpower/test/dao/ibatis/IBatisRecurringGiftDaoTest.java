package com.mpower.test.dao.ibatis;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mpower.dao.interfaces.CommitmentDao;
import com.mpower.dao.interfaces.RecurringGiftDao;
import com.mpower.domain.model.paymentInfo.Commitment;
import com.mpower.domain.model.paymentInfo.RecurringGift;

public class IBatisRecurringGiftDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private RecurringGiftDao recurringGiftDao;
    private CommitmentDao commitmentDao;

    @BeforeMethod
    public void setupMocks() {
    	recurringGiftDao = (RecurringGiftDao)super.applicationContext.getBean("recurringGiftDAO");
    	commitmentDao = (CommitmentDao)super.applicationContext.getBean("commitmentDAO");
    }
    
    private final static String STATUS1 = "new";
    private final static String STATUS2 = "pending";
    
    @Test(groups = { "testCreateRecurringGift" })
    public void testCreateRecurringGift() throws Exception {

    	Calendar cal = Calendar.getInstance();
    	RecurringGift recurringGift;

    	
    	Commitment commitment1 = new Commitment();
    	commitment1.setStatus(STATUS1);
    	commitmentDao.maintainCommitment(commitment1);
    	
    	recurringGift = new RecurringGift();
    	recurringGift.setCommitment(commitment1);
    	cal.set(Calendar.YEAR, 2000);
    	recurringGift.setNextRunDate(cal.getTime());
    	recurringGiftDao.maintain(recurringGift);

    	Commitment commitment2 = new Commitment();
    	commitment2.setStatus(STATUS1);
    	commitmentDao.maintainCommitment(commitment2);

    	recurringGift = new RecurringGift();
    	recurringGift.setCommitment(commitment2);
    	cal.set(Calendar.YEAR, 3000);
    	recurringGift.setNextRunDate(cal.getTime());
    	recurringGiftDao.maintain(recurringGift);

    	
    	Commitment commitment3 = new Commitment();
    	commitment3.setStatus(STATUS2);
    	commitmentDao.maintainCommitment(commitment3);
    	
    	recurringGift = new RecurringGift();
    	recurringGift.setCommitment(commitment3);
    	cal.set(Calendar.YEAR, 2000);
    	recurringGift.setNextRunDate(cal.getTime());
    	recurringGiftDao.maintain(recurringGift);

    	Commitment commitment4 = new Commitment();
    	commitment4.setStatus(STATUS2);
    	commitmentDao.maintainCommitment(commitment4);

    	recurringGift = new RecurringGift();
    	recurringGift.setCommitment(commitment4);
    	cal.set(Calendar.YEAR, 3000);
    	recurringGift.setNextRunDate(cal.getTime());
    	recurringGiftDao.maintain(recurringGift);

    } 
/*
    @Test(groups = { "testReadRecurringGifts" }, dependsOnGroups = { "testCreateRecurringGift" })
    public void testReadRecurringGifts() throws Exception {
    	List<String> statuses = new ArrayList<String>();
    	statuses.add(STATUS1);
    	List<RecurringGift> rgs = recurringGiftDao.readRecurringGifts(new java.util.Date(), statuses);
    	assert rgs.size() == 1;
    }
    
    @Test(groups = { "testDeleteRecurringGifts" }, dependsOnGroups = { "testReadRecurringGifts" })
    public void testDeleteRecurringGifts() throws Exception {
    	List<String> statuses = new ArrayList<String>();
    	statuses.add(STATUS1);
    	List<RecurringGift> rgs = recurringGiftDao.readRecurringGifts(new java.util.Date(), statuses);
    	RecurringGift recurringGift = rgs.get(0);
    	recurringGiftDao.remove(recurringGift);
    	rgs = recurringGiftDao.readRecurringGifts(new java.util.Date(), statuses);
    	assert rgs.size() == 0;
    }
    */
}
