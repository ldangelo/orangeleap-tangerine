package com.orangeleap.tangerine.test.dao.ibatis;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.dao.PledgeDao;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.communication.Email;
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
    	Email email = new Email();
    	email.setId(100L);
    	pledge.setSelectedEmail(email);
        Site site = new Site("company1");
    	Person person = new Person();
        person.setId(100L);
        person.setSite(site);
        pledge.setPerson(person);
        setupDistributionLines(pledge);
        
        pledge = pledgeDao.maintainPledge(pledge);
        assert pledge.getId() > 0;
        
        Pledge readPledge = pledgeDao.readPledgeById(pledge.getId());
        assert readPledge != null;
        assert pledge.getId().equals(readPledge.getId());
        assert "comments".equals(readPledge.getComments());
        assert StringConstants.USD.equals(readPledge.getCurrencyCode());
        assert 150 == readPledge.getAmountTotal().intValue();
        
        assert readPledge.getSelectedEmail() != null && readPledge.getSelectedEmail().getId() == 100L;
        assert readPledge.getPerson() != null && readPledge.getPerson().getId() == 100L;
        assert readPledge.getDistributionLines() != null && readPledge.getDistributionLines().size() == 2;
        for (DistributionLine line : readPledge.getDistributionLines()) {
            assert readPledge.getId().equals(line.getPledgeId());
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

        assert readPledge.getGifts() != null && readPledge.getGifts().isEmpty();
        assert BigDecimal.ZERO.equals(readPledge.getAmountPaid());
        assert 150 == readPledge.getAmountRemaining().intValue();
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
        assert readPledge.getLastEntryDate() == null;
        assert readPledge.isRecurring() == false;
        assert readPledge.getProjectedDate() == null;
        assert readPledge.isSendAcknowledgment() == false;
        assert readPledge.getAcknowledgmentDate() == null;
        
        // Update
        pledge = readPledge;
        pledge.setAcknowledgmentDate(new Date());
        pledge.setSendAcknowledgment(true);
        pledge.setRecurring(false);
        pledge.setAmountTotal(null);
        pledge.setAmountPerGift(new BigDecimal(12));
        pledge.setRecurring(true);
        pledge.setPhone(null);
        pledge.setSelectedEmail(null);
        
        pledge = pledgeDao.maintainPledge(pledge);
        readPledge = pledgeDao.readPledgeById(pledge.getId());
        assert readPledge != null;
        assert pledge.getId().equals(readPledge.getId());
        assert "comments".equals(readPledge.getComments());
        assert 12 == readPledge.getAmountPerGift().intValue();
        assert StringConstants.USD.equals(readPledge.getCurrencyCode());
        assert readPledge.getAmountTotal() == null;
        assert readPledge.isSendAcknowledgment();
        assert readPledge.getAcknowledgmentDate() != null;
        assert readPledge.isRecurring();

        assert readPledge.getSelectedEmail() != null && readPledge.getSelectedEmail().getId() == null;
        assert readPledge.getPerson() != null && readPledge.getPerson().getId() == 100L;
        assert readPledge.getDistributionLines() != null && readPledge.getDistributionLines().size() == 2;
        assert readPledge.getGifts() != null && readPledge.getGifts().isEmpty();
        assert BigDecimal.ZERO.equals(readPledge.getAmountPaid());
        assert readPledge.getAmountRemaining() == null;
        assert readPledge.getStartDate() == null;
        assert readPledge.getEndDate() == null;
        assert readPledge.getPledgeDate() != null;
        assert readPledge.getPledgeCancelDate() == null;
        assert readPledge.getPledgeCancelReason() == null;
        assert Pledge.STATUS_PENDING.equals(readPledge.getPledgeStatus());
        assert readPledge.getCreateDate() != null;
        assert readPledge.getUpdateDate() != null;
        assert readPledge.getFrequency() == null;
        assert readPledge.getLastEntryDate() == null;
        assert readPledge.getProjectedDate() == null;
        assert readPledge.getPaymentType() == null;
        assert readPledge.getCheckNumber() == null;
    } 
    
    public static void testId200L(Pledge pledge) {
        assert pledge.getId() == 200L;
        assert "Thank you for your pledge".equals(pledge.getComments());
        assert 25 == pledge.getAmountTotal().intValue();
        assert "pending".equals(pledge.getPledgeStatus());
        assert pledge.isSendAcknowledgment();
        assert pledge.isRecurring();
        
        assert pledge.getPerson() != null && pledge.getPerson().getId() == 300L;
        assert "Howdy Doody Inc".equals(pledge.getPerson().getOrganizationName());
        assert "Doody".equals(pledge.getPerson().getLastName());
        assert "Howdy".equals(pledge.getPerson().getFirstName());

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
                    break;
                case 600:
                    assert 5 == line.getAmount().intValue();
                    assert 200L == line.getPledgeId();
                    assert line.getOther_motivationCode() == null;
                    assert line.getGiftId() == null;
                    assert line.getMotivationCode() == null;
                    assert line.getProjectCode() == null;
                    break;
                default:
                    Assert.assertTrue("Invalid ID = " + line.getId(), false);
            }
        }

        assert pledge.getSelectedEmail() != null && pledge.getSelectedEmail().getId() == null;
        assert pledge.getGifts() != null && pledge.getGifts().isEmpty();
        assert pledge.getAmountPerGift() == null;
        assert BigDecimal.ZERO.equals(pledge.getAmountPaid());
        assert 25 == pledge.getAmountRemaining().intValue();
        assert pledge.getStartDate() == null;
        assert pledge.getEndDate() == null;
        assert pledge.getPledgeDate() == null;
        assert pledge.getPledgeCancelDate() == null;
        assert pledge.getPledgeCancelReason() == null;
        assert pledge.getCreateDate() == null;
        assert pledge.getUpdateDate() == null;
        assert pledge.getFrequency() == null;
        assert pledge.getLastEntryDate() == null;
        assert pledge.getProjectedDate() == null;
        assert StringConstants.USD.equals(pledge.getCurrencyCode());
        assert pledge.getAcknowledgmentDate() == null;
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
    public void testFindNotCancelledPledgesByGiftId() throws Exception {
        List<Pledge> pledges = pledgeDao.findNotCancelledPledgesByGiftId(0L, 200L);
        assert pledges != null && pledges.isEmpty();
        pledges = pledgeDao.findNotCancelledPledgesByGiftId(400L, 200L);
        assert pledges != null && pledges.size() == 3;
        for (Pledge pledge : pledges) {
            assert pledge.getId() == 600L || pledge.getId() == 700L || pledge.getId() == 800L;
            testPledge(pledge);
        }
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
    
    @Test(groups = { "testSearchPledges" })
    public void testSearchPledges() throws Exception {
    	Map<String, Object> params = new HashMap<String, Object>();
        params.put("firstName", "Pablo");
        params.put("accountNumber", new Long(200));
        params.put("phoneMap[home].number", "214-113-2542");
        params.put("addressMap[home].addressLine1", "ACORN");
        params.put("emailMap[home].email", "");
        params.put("amountTotal", new BigDecimal("3.99"));
    	
        List<Pledge> pledges = pledgeDao.searchPledges(params);
        Assert.assertNotNull("pledges are null", pledges);
        Assert.assertEquals("pledges size = " + pledges.size(), 1, pledges.size());
        for (Pledge pledge : pledges) {
            assert pledge.getPerson().getFirstName().equals("Pablo");
            assert pledge.getAmountTotal().compareTo(new BigDecimal("3.99")) == 0;
        }
    }   
    
    private void testPledge(Pledge pledge) {
        switch (pledge.getId().intValue()) {
            case 600:
                assert 16 == pledge.getAmountTotal().intValue();
                assert "pending".equals(pledge.getPledgeStatus());
                assert pledge.getDistributionLines() != null && pledge.getDistributionLines().isEmpty();
                break;
            case 700:
                assert 3.99f == pledge.getAmountTotal().floatValue();
                assert "inProgress".equals(pledge.getPledgeStatus());
                assert pledge.getDistributionLines() != null && pledge.getDistributionLines().size() == 2;
                for (DistributionLine line : pledge.getDistributionLines()) {
                    assert line.getAmount().floatValue() == 1.99f || line.getAmount().intValue() == 2;
                }
                break;
            case 800:
                assert 99 == pledge.getAmountTotal().intValue();
                assert "fulfilled".equals(pledge.getPledgeStatus());
                assert pledge.getDistributionLines() != null && pledge.getDistributionLines().size() == 1;
                assert pledge.getDistributionLines().get(0).getAmount().intValue() == 99;
                break;
            case 900:
                assert 2.25f == pledge.getAmountTotal().floatValue();
                assert "fulfilled".equals(pledge.getPledgeStatus());
                assert pledge.getDistributionLines() != null && pledge.getDistributionLines().isEmpty();
                break;
            default:
                Assert.assertEquals("UnexpectedId = " + pledge.getId(), true, false);
        }
    }
}
