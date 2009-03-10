package com.mpower.test.dao.ibatis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mpower.dao.interfaces.RecurringGiftDao;
import com.mpower.domain.model.Person;
import com.mpower.domain.model.paymentInfo.Commitment;
import com.mpower.domain.model.paymentInfo.RecurringGift;
import com.mpower.type.CommitmentType;

public class IBatisRecurringGiftDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private RecurringGiftDao recurringGiftDao;

    @BeforeMethod
    public void setup() {
    	recurringGiftDao = (RecurringGiftDao)super.applicationContext.getBean("recurringGiftDAO");
    }
    
    @Test(groups = { "testMaintainRecurringGift" }, dependsOnGroups = { "testReadRecurringGifts" })
    public void testMaintainRecurringGift() throws Exception {
        // Insert
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy kk:mm");
        Commitment commitment = new Commitment();
        commitment.setId(400L);
        RecurringGift recurringGift = new RecurringGift(commitment, sdf.parse("01/01/2009 12:00"));

        recurringGift = recurringGiftDao.maintainRecurringGift(recurringGift);
        assert recurringGift.getId() > 0;
        List<String> statuses = new ArrayList<String>(1);
        statuses.add("cancelled");
        List<RecurringGift> readRecurringGifts = recurringGiftDao.readRecurringGifts(sdf.parse("01/02/2009 00:00"), statuses);
        assert readRecurringGifts.size() == 1;
        RecurringGift readRecurringGift = readRecurringGifts.get(0);
        
        assert recurringGift.getId().equals(readRecurringGift.getId());
        assert sdf.parse("01/01/2009 12:00").equals(readRecurringGift.getNextRunDate());
        assert readRecurringGift.getCommitment() != null && readRecurringGift.getCommitment().getId() == 400L;
        assert CommitmentType.RECURRING_GIFT.equals(readRecurringGift.getCommitment().getCommitmentType());
        assert 2 == readRecurringGift.getCommitment().getAmountPerGift().intValue();
        assert readRecurringGift.getCommitment().isRecurring();
        assert "cancelled".equals(readRecurringGift.getCommitment().getStatus());
        assert readRecurringGift.getCommitment().getPerson() != null && readRecurringGift.getCommitment().getPerson().getId() == 300L;
        Person constituent = readRecurringGift.getCommitment().getPerson();
        IBatisConstituentDaoTest.testConstituentId300(constituent);
        
        // Update
        recurringGift.setNextRunDate(sdf.parse("10/01/1970 08:00"));
        recurringGift = recurringGiftDao.maintainRecurringGift(recurringGift);

        readRecurringGifts = recurringGiftDao.readRecurringGifts(sdf.parse("10/02/1970 00:00"), statuses);
        assert readRecurringGifts.size() == 1;
        readRecurringGift = readRecurringGifts.get(0);
        assert recurringGift.getId().equals(readRecurringGift.getId());
        assert sdf.parse("10/01/1970 08:00").equals(readRecurringGift.getNextRunDate());
        assert readRecurringGift.getCommitment() != null && readRecurringGift.getCommitment().getId() == 400L;
        assert CommitmentType.RECURRING_GIFT.equals(readRecurringGift.getCommitment().getCommitmentType());
        assert 2 == readRecurringGift.getCommitment().getAmountPerGift().intValue();
        assert readRecurringGift.getCommitment().isRecurring();
        assert "cancelled".equals(readRecurringGift.getCommitment().getStatus());
        assert readRecurringGift.getCommitment().getPerson() != null && readRecurringGift.getCommitment().getPerson().getId() == 300L;
        constituent = readRecurringGift.getCommitment().getPerson();
        IBatisConstituentDaoTest.testConstituentId300(constituent);
    } 
    
    @Test(groups = { "testReadRecurringGifts" })
    public void testReadRecurringGifts() throws Exception {
    	List<String> statuses = new ArrayList<String>();
    	statuses.add(Commitment.STATUS_ACTIVE);
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    	List<RecurringGift> rgs = recurringGiftDao.readRecurringGifts(sdf.parse("01/01/1980"), statuses);
    	assert rgs != null && rgs.isEmpty();
    	
    	rgs = recurringGiftDao.readRecurringGifts(sdf.parse("01/01/2008"), statuses);
    	assert rgs.size() == 1;
    	RecurringGift rGift = rgs.get(0);
    	assert 100L == rGift.getId();
    	assert sdf.parse("12/25/2007").equals(rGift.getNextRunDate());
    	assert rGift.getCommitment() != null && rGift.getCommitment().getId() == 100L;
    	
    	IBatisCommitmentDaoTest.testId100L(rGift.getCommitment());

        rgs = recurringGiftDao.readRecurringGifts(sdf.parse("02/15/2008"), statuses);
        assert rgs.size() == 2;
        for (RecurringGift recurringGift : rgs) {
            assert recurringGift.getId() == 100L || recurringGift.getId() == 200L;
            switch (recurringGift.getId().intValue()) {
                case 100:
                    assert sdf.parse("12/25/2007").equals(recurringGift.getNextRunDate());
                    assert recurringGift.getCommitment() != null && recurringGift.getCommitment().getId() == 100L;
                    IBatisCommitmentDaoTest.testId100L(recurringGift.getCommitment());
                    break;
                case 200:
                    assert sdf.parse("02/14/2008").equals(recurringGift.getNextRunDate());
                    assert recurringGift.getCommitment() != null && recurringGift.getCommitment().getId() == 300L;
                    Commitment commitment = recurringGift.getCommitment();
                    assert CommitmentType.RECURRING_GIFT.equals(commitment.getCommitmentType());
                    assert 10 == commitment.getAmountPerGift().intValue();
                    assert commitment.isRecurring();
                    assert Commitment.STATUS_ACTIVE.equals(commitment.getStatus());
                    assert commitment.getSelectedAddress() != null && commitment.getSelectedAddress().getId() == null;
                    assert commitment.getSelectedPhone() != null && commitment.getSelectedPhone().getId() == null;
                    assert commitment.getSelectedEmail() != null && commitment.getSelectedEmail().getId() == null;
                    assert commitment.getSelectedPaymentSource() != null && commitment.getSelectedPaymentSource().getId() == null;
                    assert commitment.getPerson() != null && commitment.getPerson().getId() == 200L;
                    IBatisConstituentDaoTest.testConstituentId200(commitment.getPerson());
                    break;
                default:
                    Assert.assertTrue("Invalid ID = " + recurringGift.getId(), false);
            }
        }
    }
    
    @Test(groups = { "testDeleteRecurringGift" }, dependsOnGroups = { "testMaintainRecurringGift" })
    public void testDeleteRecurringGift() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy kk:mm");
        List<String> statuses = new ArrayList<String>(1);
        statuses.add("cancelled");
        List<RecurringGift> readRecurringGifts = recurringGiftDao.readRecurringGifts(sdf.parse("10/02/1970 00:00"), statuses);
        assert readRecurringGifts.size() == 1;
        
        recurringGiftDao.removeRecurringGift(readRecurringGifts.get(0));

        readRecurringGifts = recurringGiftDao.readRecurringGifts(sdf.parse("10/02/1970 00:00"), statuses);
        assert readRecurringGifts.isEmpty();
    }
}
