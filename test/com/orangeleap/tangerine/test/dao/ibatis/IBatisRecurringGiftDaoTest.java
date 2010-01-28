package com.orangeleap.tangerine.test.dao.ibatis;

import com.orangeleap.tangerine.dao.RecurringGiftDao;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IBatisRecurringGiftDaoTest extends AbstractIBatisTest {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    private RecurringGiftDao recurringGiftDao;

    @BeforeMethod
    public void setup() {
    	recurringGiftDao = (RecurringGiftDao)super.applicationContext.getBean("recurringGiftDAO");
    }
    
    public static void testId100L(RecurringGift recurringGift) {
        assert recurringGift != null;
        assert recurringGift.getId() == 100L;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            assert sdf.parse("12/25/2007").equals(recurringGift.getNextRunDate());
        }
        catch (Exception e) {
            Assert.assertTrue("Exception occurred: " + e.getMessage(), false);
        }
        assert "Thank you for your recurring gift".equals(recurringGift.getComments());
        assert 550 == recurringGift.getAmountPerGift().intValue();
        assert Pledge.STATUS_PENDING.equals(recurringGift.getRecurringGiftStatus());

        assert recurringGift.getConstituent() != null && recurringGift.getConstituent().getId() == 100L;
        assert "Billy Graham Ministries".equals(recurringGift.getConstituent().getOrganizationName());
        assert "Graham".equals(recurringGift.getConstituent().getLastName());
        assert "Billy".equals(recurringGift.getConstituent().getFirstName());

        assert recurringGift.getStartDate() != null;
        assert recurringGift.getEndDate() == null;
        assert recurringGift.getCreateDate() == null;
        assert recurringGift.getUpdateDate() == null;
//        assert recurringGift.getFrequency() == null;
        assert recurringGift.getDistributionLines() != null && recurringGift.getDistributionLines().isEmpty();
    }
    
    public static void testId300L(RecurringGift recurringGift) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        assert sdf.parse("02/14/2008").equals(recurringGift.getNextRunDate());
        assert 10 == recurringGift.getAmountPerGift().intValue();
        assert RecurringGift.STATUS_PENDING.equals(recurringGift.getRecurringGiftStatus());
        assert recurringGift.getAddress() == null;
        assert recurringGift.getPhone() == null;
        assert recurringGift.getPaymentSource() == null;
        assert recurringGift.getConstituent() != null && recurringGift.getConstituent().getId() == 200L;
        IBatisConstituentDaoTest.testConstituentId200(recurringGift.getConstituent());
    }
    
    @Test(groups = { "testReadRecurringGifts" })
    public void testReadRecurringGiftById() throws Exception {
        RecurringGift recurringGift = recurringGiftDao.readRecurringGiftById(0L);
        assert recurringGift == null;
        
        recurringGift = recurringGiftDao.readRecurringGiftById(100L);
        testId100L(recurringGift);
    }

    
    @Test(groups = { "testMaintainRecurringGift" }, dependsOnGroups = { "testReadRecurringGifts" })
    public void testMaintainRecurringGift() throws Exception {
        // Insert
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy kk:mm");
        RecurringGift recurringGift = new RecurringGift(sdf.parse("01/01/2009 12:00"));
        recurringGift.setRecurringGiftStatus(RecurringGift.STATUS_EXPIRED);
        recurringGift.setAmountPerGift(new BigDecimal(2));
        recurringGift.setConstituent(new Constituent(300L, new Site("company1")));

        recurringGift = recurringGiftDao.maintainRecurringGift(recurringGift);
        assert recurringGift.getId() > 0;
        List<String> statuses = new ArrayList<String>(1);
        statuses.add(RecurringGift.STATUS_EXPIRED);
        List<RecurringGift> readRecurringGifts = recurringGiftDao.readRecurringGifts(sdf.parse("01/02/2009 00:00"), statuses,0,100);
        assert readRecurringGifts.size() == 1;
        RecurringGift readRecurringGift = readRecurringGifts.get(0);
        
        assert recurringGift.getId().equals(readRecurringGift.getId());
        assert sdf.parse("01/01/2009 12:00").equals(readRecurringGift.getNextRunDate());
        assert 2 == readRecurringGift.getAmountPerGift().intValue();
        assert RecurringGift.STATUS_EXPIRED.equals(readRecurringGift.getRecurringGiftStatus());
        assert readRecurringGift.getConstituent() != null && readRecurringGift.getConstituent().getId() == 300L;
        Constituent constituent = readRecurringGift.getConstituent();
        IBatisConstituentDaoTest.testConstituentId300(constituent);
        
        // Update
        recurringGift.setNextRunDate(sdf.parse("10/01/1970 08:00"));
        recurringGift = recurringGiftDao.maintainRecurringGift(recurringGift);

        readRecurringGifts = recurringGiftDao.readRecurringGifts(sdf.parse("10/02/1970 00:00"), statuses,0,100);
        assert readRecurringGifts.size() == 1;
        readRecurringGift = readRecurringGifts.get(0);
        assert recurringGift.getId().equals(readRecurringGift.getId());
        assert sdf.parse("10/01/1970 08:00").equals(readRecurringGift.getNextRunDate());
        assert 2 == readRecurringGift.getAmountPerGift().intValue();
        assert RecurringGift.STATUS_EXPIRED.equals(readRecurringGift.getRecurringGiftStatus());
        assert readRecurringGift.getConstituent() != null && readRecurringGift.getConstituent().getId() == 300L;
        constituent = readRecurringGift.getConstituent();
        IBatisConstituentDaoTest.testConstituentId300(constituent);
    } 
    
    
    @Test(groups = { "testDeleteRecurringGift" }, dependsOnGroups = { "testMaintainRecurringGift" })
    public void testDeleteRecurringGift() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy kk:mm");
        List<String> statuses = new ArrayList<String>(1);
        statuses.add(RecurringGift.STATUS_EXPIRED);
        List<RecurringGift> readRecurringGifts = recurringGiftDao.readRecurringGifts(sdf.parse("10/02/1970 00:00"), statuses,0,100);
        assert readRecurringGifts.size() == 1;
        
        recurringGiftDao.removeRecurringGift(readRecurringGifts.get(0));

        readRecurringGifts = recurringGiftDao.readRecurringGifts(sdf.parse("10/02/1970 00:00"), statuses,0,100);
        assert readRecurringGifts.isEmpty();
    }
    
    @Test(groups = { "testSearchRecurringGifts" })
    public void testSearchRecurringGifts() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("firstName", "Pablo");
        params.put("accountNumber", new Long(200));
        params.put("phoneMap[home].number", "214-113-2542");
        params.put("addressMap[home].addressLine1", "8457 ACORN");
        params.put("amountPerGift", new BigDecimal(10));
        
        List<RecurringGift> recurringGifts = recurringGiftDao.searchRecurringGifts(params);
        assert recurringGifts != null && recurringGifts.size() > 0;
        for (RecurringGift recurringGift : recurringGifts) {
            assert recurringGift.getConstituent().getFirstName().equals("Pablo");
            assert recurringGift.getAmountPerGift().compareTo(new BigDecimal(10)) == 0;
        }
    }   

    @Test(groups = { "testReadRecurringGift" })
    public void testReadAssociatedGiftIdsForRecurringGift() throws Exception {
        RecurringGift recurringGift = recurringGiftDao.readRecurringGiftById(300L);
        testId300L(recurringGift);
        Assert.assertNotNull("Expected associatedGiftIds to be not null", recurringGift.getAssociatedGiftIds());
        Assert.assertTrue("Expected associatedGiftIds to be empty", recurringGift.getAssociatedGiftIds().isEmpty());
        
        recurringGift = recurringGiftDao.readRecurringGiftById(100L);
        Assert.assertNotNull("Expected associatedGiftIds to be not null", recurringGift.getAssociatedGiftIds());
        Assert.assertTrue("Expected associatedGiftIds to be size = 2, not " + recurringGift.getAssociatedGiftIds().size(), recurringGift.getAssociatedGiftIds().size() == 1);
        for (Long giftId : recurringGift.getAssociatedGiftIds()) {
            assert giftId == 200L;
        }
        
        recurringGift = recurringGiftDao.readRecurringGiftById(400L);
        Assert.assertNotNull("Expected associatedGiftIds to be not null", recurringGift.getAssociatedGiftIds());
        Assert.assertTrue("Expected associatedGiftIds to be size = 1, not " + recurringGift.getAssociatedGiftIds().size(), recurringGift.getAssociatedGiftIds().size() == 1);
        for (Long giftId : recurringGift.getAssociatedGiftIds()) {
            assert giftId == 600L;
        }
    }

}
