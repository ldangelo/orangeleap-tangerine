package com.orangeleap.tangerine.test.dao.ibatis;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.dao.PledgeDao;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.util.StringConstants;

public class IBatisPledgeDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private PledgeDao pledgeDao;

    @BeforeMethod
    public void setup() {
        pledgeDao = (PledgeDao)super.applicationContext.getBean("pledgeDAO");
    }
    
    private void setupDistributionLines(Pledge pledge) {
        List<DistributionLine> lines = new ArrayList<DistributionLine>();
        
        DistributionLine line = new DistributionLine();
        line.setAmount(new BigDecimal(100));
        line.setProjectCode("foo");
        line.setPledgeId(pledge.getId());
        lines.add(line);
        
        line = new DistributionLine();
        line.setAmount(new BigDecimal(50));
        line.setMotivationCode("bar");
        line.setPledgeId(pledge.getId());
        lines.add(line);
        
        pledge.setDistributionLines(lines);
    }
    
    @Test(groups = { "testMaintainPledge" }, dependsOnGroups = { "testReadPledge" })
    public void testMaintainPledge() throws Exception {
        // Insert
    	Pledge pledge = new Pledge();
    	pledge.setComments("comments");
    	pledge.setCurrencyCode(StringConstants.USD);
    	pledge.setAmountTotal(new BigDecimal(150));
    	pledge.setRecurring(false);
        Site site = new Site("company1");
    	Constituent constituent = new Constituent();
        constituent.setId(100L);
        constituent.setSite(site);
        pledge.setConstituent(constituent);
        setupDistributionLines(pledge);
        
        pledge = pledgeDao.maintainPledge(pledge);
        assert pledge.getId() > 0;
        
        Pledge readPledge = pledgeDao.readPledgeById(pledge.getId());
        assert readPledge != null;
        assert pledge.getId().equals(readPledge.getId());
        assert "comments".equals(readPledge.getComments());
        assert StringConstants.USD.equals(readPledge.getCurrencyCode());
        assert 150 == readPledge.getAmountTotal().intValue();
        
        assert readPledge.getConstituent() != null && readPledge.getConstituent().getId() == 100L;
        assert readPledge.getDistributionLines() != null && readPledge.getDistributionLines().size() == 2;
        for (DistributionLine line : readPledge.getDistributionLines()) {
            assert readPledge.getId().equals(line.getPledgeId());
            assert line.getConstituent().getId() == 100L;
            if (100 == line.getAmount().floatValue()) {
                assert "foo".equals(line.getProjectCode());
                assert line.getMotivationCode() == null;
                assert line.getOther_motivationCode() == null;
                assert line.getGiftId() == null;
            }
            else if (50 == line.getAmount().floatValue()) {
                assert "bar".equals(line.getMotivationCode());
                assert line.getProjectCode() == null;
                assert line.getOther_motivationCode() == null;
                assert line.getGiftId() == null;
            }
            else {
                Assert.assertTrue("Invalid ID = " + line.getId(), false);
            }
        }

        assert readPledge.getAmountPerGift() == null;
        assert readPledge.getStartDate() == null;
        assert readPledge.getEndDate() == null;
        assert readPledge.getPledgeDate() != null;
        assert readPledge.getPledgeCancelDate() == null;
        assert readPledge.getPledgeCancelReason() == null;
        assert Pledge.STATUS_PENDING.equals(readPledge.getPledgeStatus());
        assert readPledge.getCreateDate() != null;
        assert readPledge.getUpdateDate() != null;
        assert readPledge.getFrequency() == null;
        assert readPledge.isRecurring() == false;
        assert readPledge.getProjectedDate() == null;
        
        // Update
        pledge = readPledge;
        pledge.setRecurring(false);
        pledge.setAmountTotal(null);
        pledge.setAmountPerGift(new BigDecimal(12));
        pledge.setRecurring(true);
        pledge.setPhone(null);
        
        pledge = pledgeDao.maintainPledge(pledge);
        readPledge = pledgeDao.readPledgeById(pledge.getId());
        assert readPledge != null;
        assert pledge.getId().equals(readPledge.getId());
        assert "comments".equals(readPledge.getComments());
        assert 12 == readPledge.getAmountPerGift().intValue();
        assert StringConstants.USD.equals(readPledge.getCurrencyCode());
        assert readPledge.getAmountTotal() == null;
        assert readPledge.isRecurring();

        assert readPledge.getConstituent() != null && readPledge.getConstituent().getId() == 100L;
        assert readPledge.getDistributionLines() != null && readPledge.getDistributionLines().size() == 2;
        assert readPledge.getStartDate() == null;
        assert readPledge.getEndDate() == null;
        assert readPledge.getPledgeDate() != null;
        assert readPledge.getPledgeCancelDate() == null;
        assert readPledge.getPledgeCancelReason() == null;
        assert Pledge.STATUS_PENDING.equals(readPledge.getPledgeStatus());
        assert readPledge.getCreateDate() != null;
        assert readPledge.getUpdateDate() != null;
        assert readPledge.getFrequency() == null;
        assert readPledge.getProjectedDate() == null;
        assert readPledge.getPaymentType() == null;
        assert readPledge.getCheckNumber() == null;
    } 
    
    public static void testId200L(Pledge pledge) {
        assert pledge.getId() == 200L;
        assert "Thank you for your pledge".equals(pledge.getComments());
        assert 25 == pledge.getAmountTotal().intValue();
        assert "Pending".equals(pledge.getPledgeStatus());
        assert pledge.isRecurring();
        
        assert pledge.getConstituent() != null && pledge.getConstituent().getId() == 300L;
        assert "Howdy Doody Inc".equals(pledge.getConstituent().getOrganizationName());
        assert "Doody".equals(pledge.getConstituent().getLastName());
        assert "Howdy".equals(pledge.getConstituent().getFirstName());

        assert pledge.getDistributionLines() != null && pledge.getDistributionLines().size() == 2;
        for (DistributionLine line : pledge.getDistributionLines()) {
            assert line.getId() == 500L || line.getId() == 600L;
            switch (line.getId().intValue()) {
                case 500:
                    assert 20 == line.getAmount().intValue();
                    assert "hi mom".equals(line.getOther_motivationCode());
                    assert 200L == line.getPledgeId();
                    assert line.getGiftId() == null;
                    assert line.getMotivationCode() == null;
                    assert line.getProjectCode() == null;
                    assert line.getConstituent().getId() == 300L;
                    break;
                case 600:
                    assert 5 == line.getAmount().intValue();
                    assert 200L == line.getPledgeId();
                    assert line.getOther_motivationCode() == null;
                    assert line.getGiftId() == null;
                    assert line.getMotivationCode() == null;
                    assert line.getProjectCode() == null;
                    assert line.getConstituent().getId() == 300L;
                    break;
                default:
                    Assert.assertTrue("Invalid ID = " + line.getId(), false);
            }
        }

        assert pledge.getAmountPerGift() == null;
        assert pledge.getStartDate() == null;
        assert pledge.getEndDate() == null;
        assert pledge.getPledgeDate() == null;
        assert pledge.getPledgeCancelDate() == null;
        assert pledge.getPledgeCancelReason() == null;
        assert pledge.getCreateDate() == null;
        assert pledge.getUpdateDate() == null;
        assert pledge.getFrequency() == null;
        assert pledge.getProjectedDate() == null;
        assert StringConstants.USD.equals(pledge.getCurrencyCode());
    }
    
    @Test(groups = { "testReadPledge" })
    public void testReadPledgeById() throws Exception {
        Pledge pledge = pledgeDao.readPledgeById(0L);
        assert pledge == null;
        
        pledge = pledgeDao.readPledgeById(200L);
        testId200L(pledge);
    }
    
    @Test(groups = { "testReadPledge" })
    public void testReadPledgesByConstituentIdType() throws Exception {
        List<Pledge> pledges = pledgeDao.readPledgesByConstituentId(0L);
        assert pledges != null && pledges.isEmpty();
        
        pledges = pledgeDao.readPledgesByConstituentId(300L);
        assert pledges != null && pledges.size() == 1;
        Pledge pledge = pledges.get(0);
        testId200L(pledge);
    }
    
    @Test(groups = { "testReadPledge" })
    public void testFindNotCancelledPledges() throws Exception {
        List<Pledge> pledges = pledgeDao.findNotCancelledPledges(0L);
        assert pledges != null && pledges.isEmpty();
        
        pledges = pledgeDao.findNotCancelledPledges(200L);
        assert pledges != null && pledges.size() == 4;
        
        for (Pledge pledge : pledges) {
            assert pledge.getId() == 600L || pledge.getId() == 700L || pledge.getId() == 800L || pledge.getId() == 900L;
            testPledge(pledge);
        }
    }
    
    @Test(groups = { "testReadPledge" })
    public void testFindDistributionLinesForPledges() throws Exception {
        List<String> pledgeIds = new ArrayList<String>();
        pledgeIds.add("0");
        List<DistributionLine> lines = pledgeDao.findDistributionLinesForPledges(pledgeIds);
        assert lines != null && lines.isEmpty();
        
        pledgeIds.add("800");
        lines = pledgeDao.findDistributionLinesForPledges(pledgeIds);
        assert lines != null && lines.size() == 1;
        assert lines.get(0).getId() == 900L;
        assert lines.get(0).getAmount().intValue() == 99;
        assert lines.get(0).getPledgeId() == 800L;
        assert lines.get(0).getConstituent().getId() == 200L;
        
        pledgeIds.add("700");
        lines = pledgeDao.findDistributionLinesForPledges(pledgeIds);
        assert lines != null && lines.size() == 3;
        for (DistributionLine line : lines) {
            assert line.getConstituent().getId() == 200L;
            switch (line.getId().intValue()) {
                case 700:
                    assert 1.99f == line.getAmount().floatValue();
                    assert line.getPledgeId() == 700L;
                    break;
                case 800:
                    assert 2 == line.getAmount().intValue();
                    assert line.getPledgeId() == 700L;
                    break;
                case 900:
                    assert 99 == line.getAmount().intValue();
                    assert line.getPledgeId() == 800L;
                    break;
                default:
                    Assert.assertTrue("UnexpectedId = " + line.getId(), false);
            }
        }
    }
    
    @Test(groups = { "testReadPledge" })
    public void testReadAssociatedGiftIdsForPledge() throws Exception {
        Pledge pledge = pledgeDao.readPledgeById(200L);
        testId200L(pledge);
        Assert.assertNotNull("Expected associatedGiftIds to be not null", pledge.getAssociatedGiftIds());
        Assert.assertTrue("Expected associatedGiftIds to be empty", pledge.getAssociatedGiftIds().isEmpty());
        
        pledge = pledgeDao.readPledgeById(700L);
        Assert.assertNotNull("Expected associatedGiftIds to be not null", pledge.getAssociatedGiftIds());
        Assert.assertTrue("Expected associatedGiftIds to be size = 2, not " + pledge.getAssociatedGiftIds().size(), pledge.getAssociatedGiftIds().size() == 2);
        for (Long giftId : pledge.getAssociatedGiftIds()) {
            assert giftId == 300L || giftId == 400L;
        }
        
        pledge = pledgeDao.readPledgeById(500L);
        Assert.assertNotNull("Expected associatedGiftIds to be not null", pledge.getAssociatedGiftIds());
        Assert.assertTrue("Expected associatedGiftIds to be size = 1, not " + pledge.getAssociatedGiftIds().size(), pledge.getAssociatedGiftIds().size() == 1);
        for (Long giftId : pledge.getAssociatedGiftIds()) {
            assert giftId == 400L;
        }
    }
    
    @Test(groups = { "testSearchPledges" })
    public void testSearchPledges() throws Exception {
    	Map<String, Object> params = new HashMap<String, Object>();
        params.put("firstName", "Pablo");
        params.put("accountNumber", new Long(200));
        params.put("phoneMap[home].number", "214-113-2542");
        params.put("addressMap[home].addressLine1", "ACORN");
        params.put("amountTotal", new BigDecimal("3.99"));
    	
        List<Pledge> pledges = pledgeDao.searchPledges(params);
        Assert.assertNotNull("pledges are null", pledges);
        Assert.assertEquals("pledges size = " + pledges.size(), 1, pledges.size());
        for (Pledge pledge : pledges) {
            assert pledge.getConstituent().getFirstName().equals("Pablo");
            assert pledge.getAmountTotal().compareTo(new BigDecimal("3.99")) == 0;
        }
    }   
    
    private void testPledge(Pledge pledge) {
        switch (pledge.getId().intValue()) {
            case 600:
                assert 16 == pledge.getAmountTotal().intValue();
                assert "Pending".equals(pledge.getPledgeStatus());
                break;
            case 700:
                assert 3.99f == pledge.getAmountTotal().floatValue();
                assert "In Progress".equals(pledge.getPledgeStatus());
                break;
            case 800:
                assert 99 == pledge.getAmountTotal().intValue();
                assert "Fulfilled".equals(pledge.getPledgeStatus());
                break;
            case 900:
                assert 2.25f == pledge.getAmountTotal().floatValue();
                assert "Fulfilled".equals(pledge.getPledgeStatus());
                break;
            default:
                Assert.assertEquals("UnexpectedId = " + pledge.getId(), true, false);
        }
    }
}
