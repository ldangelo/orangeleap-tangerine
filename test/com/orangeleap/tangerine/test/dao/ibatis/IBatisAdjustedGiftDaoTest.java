package com.orangeleap.tangerine.test.dao.ibatis;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.dao.AdjustedGiftDao;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.util.StringConstants;

public class IBatisAdjustedGiftDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private AdjustedGiftDao adjustedGiftDao;

    @BeforeMethod
    public void setup() {
        adjustedGiftDao = (AdjustedGiftDao)super.applicationContext.getBean("adjustedGiftDAO");
    }

    private void setupDistributionLines(AdjustedGift adjustedGift) {
        List<DistributionLine> lines = new ArrayList<DistributionLine>();
        
        DistributionLine line = new DistributionLine();
        line.setAmount(new BigDecimal("-9.99"));
        line.setProjectCode("foo");
        line.setAdjustedGiftId(adjustedGift.getId());
        lines.add(line);
        
        adjustedGift.setDistributionLines(lines);
    }

    @Test(groups = { "testMaintainAdjustedGift" }, dependsOnGroups = { "testReadAdjustedGift" })
    public void testMaintainAdjustedGift() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        AdjustedGift adjustedGift = new AdjustedGift();
        adjustedGift.setAdjustedAmount(new BigDecimal("9.99"));
        adjustedGift.setAdjustedPaymentRequired(false);
        adjustedGift.setAdjustedReason("No Reason");
        adjustedGift.setAdjustedStatus("Pending");
        adjustedGift.setAdjustedTransactionDate(sdf.parse("01/01/2010"));
        adjustedGift.setAdjustedType("foo");
        adjustedGift.setOriginalGiftId(600L);
        adjustedGift.setPerson(new Person(100L, new Site("company1")));
        Address addr = new Address();
        addr.setId(100L);
        adjustedGift.setSelectedAddress(addr);
        setupDistributionLines(adjustedGift);
        
        adjustedGift = adjustedGiftDao.maintainAdjustedGift(adjustedGift);
        AdjustedGift readAdjustedGift = adjustedGiftDao.readAdjustedGiftById(adjustedGift.getId());
        assert readAdjustedGift.getId().equals(adjustedGift.getId());
        assert new BigDecimal("9.99").floatValue() == readAdjustedGift.getAdjustedAmount().floatValue();
        assert readAdjustedGift.isAdjustedPaymentRequired() == false;
        assert "No Reason".equals(readAdjustedGift.getAdjustedReason());
        assert "Pending".equals(readAdjustedGift.getAdjustedStatus());
        assert "01/01/2010".equals(sdf.format(readAdjustedGift.getAdjustedTransactionDate()));
        assert "foo".equals(readAdjustedGift.getAdjustedType());
        assert 100L == readAdjustedGift.getPerson().getId();
        assert 100L == readAdjustedGift.getSelectedAddress().getId();
        assert readAdjustedGift.getDistributionLines().size() == 1;
        assert new BigDecimal("-9.99").floatValue() == readAdjustedGift.getDistributionLines().get(0).getAmount().floatValue();
        assert 600L == readAdjustedGift.getOriginalGiftId();
        
        assert readAdjustedGift.getSelectedPhone().getId() == null;
        assert StringConstants.EMPTY.equals(readAdjustedGift.getAuthCode());
        assert readAdjustedGift.getCheckNumber() == null;
        assert readAdjustedGift.getComments() == null;
        assert readAdjustedGift.getPaymentMessage() == null;
        assert StringConstants.EMPTY.equals(readAdjustedGift.getPaymentStatus());
        assert readAdjustedGift.getTxRefNum() == null;
        assert readAdjustedGift.getPaymentType() == null;
        assert readAdjustedGift.getSelectedPaymentSource().getId() == null;
        
        adjustedGift = readAdjustedGift;
        adjustedGift.setTxRefNum("012345");
        adjustedGift.setPaymentMessage("I have paid");
        adjustedGift.setAuthCode("xyz");
        
        adjustedGift = adjustedGiftDao.maintainAdjustedGift(adjustedGift);
        readAdjustedGift = adjustedGiftDao.readAdjustedGiftById(adjustedGift.getId());
        assert readAdjustedGift.getId().equals(adjustedGift.getId());
        assert new BigDecimal("9.99").floatValue() == readAdjustedGift.getAdjustedAmount().floatValue();
        assert readAdjustedGift.isAdjustedPaymentRequired() == false;
        assert "No Reason".equals(readAdjustedGift.getAdjustedReason());
        assert "Pending".equals(readAdjustedGift.getAdjustedStatus());
        assert "01/01/2010".equals(sdf.format(readAdjustedGift.getAdjustedTransactionDate()));
        assert "foo".equals(readAdjustedGift.getAdjustedType());
        assert 100L == readAdjustedGift.getPerson().getId();
        assert 100L == readAdjustedGift.getSelectedAddress().getId();
        assert readAdjustedGift.getDistributionLines().size() == 1;
        assert new BigDecimal("-9.99").floatValue() == readAdjustedGift.getDistributionLines().get(0).getAmount().floatValue();
        assert 600L == readAdjustedGift.getOriginalGiftId();
        assert "xyz".equals(readAdjustedGift.getAuthCode());
        assert "012345".equals(readAdjustedGift.getTxRefNum());
        assert "I have paid".equals(readAdjustedGift.getPaymentMessage());
        
        assert readAdjustedGift.getSelectedPhone().getId() == null;
        assert readAdjustedGift.getCheckNumber() == null;
        assert readAdjustedGift.getComments() == null;
        assert StringConstants.EMPTY.equals(readAdjustedGift.getPaymentStatus());
        assert readAdjustedGift.getPaymentType() == null;
        assert readAdjustedGift.getSelectedPaymentSource().getId() == null;
        
    }
    
    @Test(groups = { "testReadAdjustedGift" })
    public void testReadAdjustedGiftById() throws Exception {
        AdjustedGift adjustedGift = adjustedGiftDao.readAdjustedGiftById(0L);
        assert adjustedGift == null;
        
        adjustedGift = adjustedGiftDao.readAdjustedGiftById(1L);
        assert adjustedGift.getId().equals(1L);
        assert adjustedGift.getAdjustedAmount().floatValue() == new BigDecimal("-160").floatValue();
        assert adjustedGift.getAdjustedReason().equals("Just Because");
        assert adjustedGift.getAdjustedStatus().equals("In Progress");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        assert "08/08/2009".equals(sdf.format(adjustedGift.getAdjustedTransactionDate()));
        assert "Partial Refund".equals(adjustedGift.getAdjustedType());
        assert adjustedGift.isAdjustedPaymentRequired();
        assert "foobar".equals(adjustedGift.getAdjustedPaymentTo());
        assert 300L == adjustedGift.getOriginalGiftId();
        assert 300f == adjustedGift.getOriginalAmount().floatValue();
        assert 200L == adjustedGift.getPerson().getId();
        
        assert adjustedGift.getDistributionLines().size() == 2;
        for (DistributionLine line : adjustedGift.getDistributionLines()) {
            assert line.getId() == 1000L || line.getId() == 1100L;
            assert new BigDecimal("-80").floatValue() == line.getAmount().floatValue();
            assert 1L == line.getAdjustedGiftId();
        }
        
        adjustedGift = adjustedGiftDao.readAdjustedGiftById(3L);
        assert adjustedGift.getId().equals(3L);
        assert adjustedGift.getAdjustedAmount().floatValue() == new BigDecimal("-1.01").floatValue();
        assert adjustedGift.getAdjustedReason().equals("Hola");
        assert adjustedGift.getAdjustedStatus().equals("Cancelled");
        assert "08/10/2009".equals(sdf.format(adjustedGift.getAdjustedTransactionDate()));
        assert "Partial Refund".equals(adjustedGift.getAdjustedType());
        assert adjustedGift.isAdjustedPaymentRequired();
        assert "foobar".equals(adjustedGift.getAdjustedPaymentTo());
        assert 300L == adjustedGift.getOriginalGiftId();
        assert 300f == adjustedGift.getOriginalAmount().floatValue();
        assert 200L == adjustedGift.getPerson().getId();
        
        assert adjustedGift.getDistributionLines().size() == 2;
        for (DistributionLine line : adjustedGift.getDistributionLines()) {
            assert line.getId() == 1300L || line.getId() == 1400L;
            assert 3L == line.getAdjustedGiftId();
            if (line.getId() == 1300L) {
                assert new BigDecimal("-1").floatValue() == line.getAmount().floatValue();
            }
            if (line.getId() == 1400L) {
                assert new BigDecimal("-.01").floatValue() == line.getAmount().floatValue();
            }
        }
    }
    
    @Test(groups = { "testReadAdjustedGift" })
    public void testReadAdjustedGiftsForOriginalGiftId() throws Exception {
        List<AdjustedGift> adjustedGifts = adjustedGiftDao.readAdjustedGiftsForOriginalGiftId(0L);
        assert adjustedGifts.isEmpty();
        
        adjustedGifts = adjustedGiftDao.readAdjustedGiftsForOriginalGiftId(300L);
        assert adjustedGifts.size() == 3;
        for (AdjustedGift adjustedGift : adjustedGifts) {
            assert adjustedGift.getId() == 1L || adjustedGift.getId() == 2L || adjustedGift.getId() == 3L;
            assert adjustedGift.getOriginalGiftId() == 300L;
            if (adjustedGift.getId() == 1L) {
                assert adjustedGift.getDistributionLines().size() == 2;
            }
            if (adjustedGift.getId() == 2L) {
                assert adjustedGift.getDistributionLines().size() == 1;
            }
            if (adjustedGift.getId() == 3L) {
                assert adjustedGift.getDistributionLines().size() == 2;
            }
        }
    }
}
