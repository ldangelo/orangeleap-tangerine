package com.orangeleap.tangerine.test.service.impl;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.type.GiftEntryType;
import com.orangeleap.tangerine.type.GiftType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GiftServiceTest extends BaseTest {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    private GiftService giftService;
    
    @Autowired
    private ConstituentService constituentService;
    
    @Test
    public void testCombineGiftPledgeDistributionLines() throws Exception {
        DistributionLine defaultDistributionLine = new DistributionLine();
        List<DistributionLine> lines = giftService.combineGiftCommitmentDistributionLines(null, null, defaultDistributionLine, BigDecimal.ZERO, 2, null, true);
        assert lines != null && lines.isEmpty();
        
        lines = giftService.combineGiftCommitmentDistributionLines(setupGiftDistributionLinesForPledge(), null, defaultDistributionLine, BigDecimal.TEN, 2, null, true);
        assert lines != null && lines.size() == 1;
        for (DistributionLine line : lines) {
            assert line.getId() == 1L;
            assert new BigDecimal(3).equals(line.getAmount());
            assert new BigDecimal(15).equals(line.getPercentage());
        }
        
        lines = giftService.combineGiftCommitmentDistributionLines(null, setupPledgeDistributionLines(), defaultDistributionLine, new BigDecimal("33.33"), 2, null, true);
        assert lines != null && lines.size() == 3;
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalPct = BigDecimal.ZERO;
        for (DistributionLine line : lines) {
            assert line.getId() == 98L || line.getId() == 99L || line.getId() == 100L;
            assert line.getPledgeId() == null;
            totalAmount = totalAmount.add(line.getAmount());
            totalPct = totalPct.add(line.getPercentage());
            if (line.getId() == 98L) {
                assert line.getCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID).equals("333");
                Assert.assertEquals("Expected amount to be 6.67, not " + line.getAmount(), new BigDecimal("6.67"), line.getAmount());
                Assert.assertEquals("Expected percentage to be 20.00, not " + line.getPercentage(), new BigDecimal("20.00"), line.getPercentage());
            }
            else if (line.getId() == 99L) {
                assert line.getCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID).equals("333");
                Assert.assertEquals("Expected amount to be 10.00, not " + line.getAmount(), new BigDecimal("10.00"), line.getAmount());
                Assert.assertEquals("Expected percentage to be 30.00, not " + line.getPercentage(), new BigDecimal("30.00"), line.getPercentage());
            }
            else if (line.getId() == 100L) {
                assert line.getCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID).equals("111");
                Assert.assertEquals("Expected amount to be 16.66, not " + line.getAmount(), new BigDecimal("16.66"), line.getAmount());
                Assert.assertEquals("Expected percentage to be 50.00, not " + line.getPercentage(), new BigDecimal("50.00"), line.getPercentage());
            }
        }
        Assert.assertEquals("Expected total amount = 33.33", new BigDecimal("33.33"), totalAmount);
        Assert.assertEquals("Expected total percentage = 100.00", new BigDecimal("100.00"), totalPct);

        lines = giftService.combineGiftCommitmentDistributionLines(null, setupPledgeDistributionLines(), defaultDistributionLine, new BigDecimal(0), 2, null, true);
        assert lines != null && lines.size() == 3;
        for (DistributionLine line : lines) {
            assert line.getId() == 98L || line.getId() == 99L || line.getId() == 100L;
            assert line.getPledgeId() == null;
            Assert.assertEquals("Expected amount to be 0.00, not " + line.getAmount(), new BigDecimal("0.00"), line.getAmount());
            if (line.getId() == 98L) {
                assert line.getCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID).equals("333");
                Assert.assertEquals("Expected percentage to be 20.00, not " + line.getPercentage(), new BigDecimal("20.00"), line.getPercentage());
            }
            else if (line.getId() == 99L) {
                assert line.getCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID).equals("333");
                Assert.assertEquals("Expected percentage to be 30.00, not " + line.getPercentage(), new BigDecimal("30.00"), line.getPercentage());
            }
            else if (line.getId() == 100L) {
                assert line.getCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID).equals("111");
                Assert.assertEquals("Expected percentage to be 50.00, not " + line.getPercentage(), new BigDecimal("50.00"), line.getPercentage());
            }
        }
        
        lines = giftService.combineGiftCommitmentDistributionLines(setupGiftDistributionLinesForPledge(), setupPledgeDistributionLines(), defaultDistributionLine, new BigDecimal(20), 2, null, true);
        assert lines != null && lines.size() == 4;
        for (DistributionLine line : lines) {
            assert line.getId() == 1L || line.getId() == 3L || line.getId() == 98L || line.getId() == 99L;
            if (line.getId() == 3L) {
                assert line.getPledgeId() == null;
                assert line.getCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID).equals("111");
                Assert.assertEquals("Expected amount to be 50, not " + line.getAmount(), new BigDecimal("50"), line.getAmount());
                Assert.assertEquals("Expected percentage to be 79, not " + line.getPercentage(), new BigDecimal("79"), line.getPercentage());
            }
            else if (line.getId() == 98L) {
                assert line.getPledgeId() == null;
                assert line.getCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID).equals("333");
                Assert.assertEquals("Expected amount to be 4.00, not " + line.getAmount(), new BigDecimal("4.00"), line.getAmount());
                Assert.assertEquals("Expected percentage to be 20.00, not " + line.getPercentage(), new BigDecimal("20.00"), line.getPercentage());
            }
            else if (line.getId() == 99L) {
                assert line.getPledgeId() == null;
                assert line.getCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID).equals("333");
                Assert.assertEquals("Expected amount to be 6.00, not " + line.getAmount(), new BigDecimal("6.00"), line.getAmount());
                Assert.assertEquals("Expected percentage to be 30.00, not " + line.getPercentage(), new BigDecimal("30.00"), line.getPercentage());
            }
        }

        lines = giftService.combineGiftCommitmentDistributionLines(setupGiftDistributionLinesForPledge(), setupPledgeDistributionLines(), defaultDistributionLine, BigDecimal.ZERO, 2, null, true);
        assert lines != null && lines.size() == 4;
        for (DistributionLine line : lines) {
            assert line.getId() == 1L || line.getId() == 3L || line.getId() == 98L || line.getId() == 99L;
            if (line.getId() == 3L) {
                assert line.getPledgeId() == null;
                assert line.getCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID).equals("111");
                Assert.assertEquals("Expected amount to be 50, not " + line.getAmount(), new BigDecimal("50"), line.getAmount());
                Assert.assertEquals("Expected percentage to be 79, not " + line.getPercentage(), new BigDecimal("79"), line.getPercentage());
            }
            else if (line.getId() == 98L) {
                assert line.getPledgeId() == null;
                assert line.getCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID).equals("333");
                Assert.assertEquals("Expected amount to be 0.00, not " + line.getAmount(), new BigDecimal("0.00"), line.getAmount());
                Assert.assertEquals("Expected percentage to be 20.00, not " + line.getPercentage(), new BigDecimal("20.00"), line.getPercentage());
            }
            else if (line.getId() == 99L) {
                assert line.getPledgeId() == null;
                assert line.getCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID).equals("333");
                Assert.assertEquals("Expected amount to be 0.00, not " + line.getAmount(), new BigDecimal("0.00"), line.getAmount());
                Assert.assertEquals("Expected percentage to be 30.00, not " + line.getPercentage(), new BigDecimal("30.00"), line.getPercentage());
            }
        }
    }
    
    private List<DistributionLine> setupGiftDistributionLinesForPledge() {
        List<DistributionLine> lines = new ArrayList<DistributionLine>();
        DistributionLine line = new DistributionLine(new BigDecimal(3), new BigDecimal(15), "00001", "abcde", null);
        line.setId(1L);
        lines.add(line);
        
        line = new DistributionLine(new BigDecimal(5.25), new BigDecimal(20), null, null, "sss");
        line.setId(2L);
        line.addCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID, "999");
        lines.add(line);
        
        line = new DistributionLine(new BigDecimal(50), new BigDecimal(79), null, null, null);
        line.setId(3L);
        line.addCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID, "111");
        lines.add(line);
        
        return lines;
    }
    
    private List<DistributionLine> setupPledgeDistributionLines() {
        List<DistributionLine> lines = new ArrayList<DistributionLine>();
        DistributionLine line = new DistributionLine(new BigDecimal(1.99), new BigDecimal(40), null, "fghij", null);
        line.setId(98L);
        line.setPledgeId(333L);
        lines.add(line);
        
        line = new DistributionLine(new BigDecimal(2.99), new BigDecimal(60), null, null, "nbil");
        line.setId(99L);
        line.setPledgeId(333L);
        lines.add(line);
        
        line = new DistributionLine(new BigDecimal(25), new BigDecimal(100), null, null, null);
        line.setId(100L);
        line.setPledgeId(111L);
        lines.add(line);
        
        return lines;
    }

    @Test
    public void testCombineGiftRecurringGiftDistributionLines() throws Exception {
        DistributionLine defaultDistributionLine = new DistributionLine();
        List<DistributionLine> lines = giftService.combineGiftCommitmentDistributionLines(null, null, defaultDistributionLine, BigDecimal.ZERO, 2, null, false);
        assert lines != null && lines.isEmpty();
        
        lines = giftService.combineGiftCommitmentDistributionLines(setupGiftDistributionLinesForRecurringGift(), null, defaultDistributionLine, BigDecimal.TEN, 2, null, false);
        assert lines != null && lines.size() == 1;
        for (DistributionLine line : lines) {
            assert line.getId() == 1L;
            assert new BigDecimal(3).equals(line.getAmount());
            assert new BigDecimal(15).equals(line.getPercentage());
        }
        
        lines = giftService.combineGiftCommitmentDistributionLines(null, setupRecurringGiftDistributionLines(), defaultDistributionLine, new BigDecimal("33.33"), 2, null, false);
        assert lines != null && lines.size() == 2;
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalPct = BigDecimal.ZERO;
        for (DistributionLine line : lines) {
            assert line.getId() == 101L || line.getId() == 103L;
            assert line.getRecurringGiftId() == null;
            totalAmount = totalAmount.add(line.getAmount());
            totalPct = totalPct.add(line.getPercentage());
            if (line.getId() == 101L) {
                assert line.getCustomFieldValue(StringConstants.ASSOCIATED_RECURRING_GIFT_ID).equals("88888");
                Assert.assertEquals("Expected amount to be 16.66, not " + line.getAmount(), new BigDecimal("16.66"), line.getAmount());
                Assert.assertEquals("Expected percentage to be 50.00, not " + line.getPercentage(), new BigDecimal("50.00"), line.getPercentage());
            }
            else if (line.getId() == 103L) {
                assert line.getCustomFieldValue(StringConstants.ASSOCIATED_RECURRING_GIFT_ID).equals("7");
                Assert.assertEquals("Expected amount to be 16.66, not " + line.getAmount(), new BigDecimal("16.66"), line.getAmount());
                Assert.assertEquals("Expected percentage to be 50.00, not " + line.getPercentage(), new BigDecimal("50.00"), line.getPercentage());
            }
        }
        Assert.assertEquals("Expected total amount = 33.32", new BigDecimal("33.32"), totalAmount);
        Assert.assertEquals("Expected total percentage = 100.00", new BigDecimal("100.00"), totalPct);

        lines = giftService.combineGiftCommitmentDistributionLines(setupGiftDistributionLinesForRecurringGift(), setupRecurringGiftDistributionLines(), defaultDistributionLine, new BigDecimal(20), 2, null, false);
        assert lines != null && lines.size() == 3;
        for (DistributionLine line : lines) {
            assert line.getId() == 1L || line.getId() == 5L || line.getId() == 101L;
            if (line.getId() == 5L) {
                assert line.getRecurringGiftId() == null;
                assert line.getCustomFieldValue(StringConstants.ASSOCIATED_RECURRING_GIFT_ID).equals("7");
                Assert.assertEquals("Expected amount to be 50, not " + line.getAmount(), new BigDecimal("50"), line.getAmount());
                Assert.assertEquals("Expected percentage to be 79, not " + line.getPercentage(), new BigDecimal("79"), line.getPercentage());
            }
            else if (line.getId() == 101L) {
                assert line.getRecurringGiftId() == null;
                assert line.getCustomFieldValue(StringConstants.ASSOCIATED_RECURRING_GIFT_ID).equals("88888");
                Assert.assertEquals("Expected amount to be 10.00, not " + line.getAmount(), new BigDecimal("10.00"), line.getAmount());
                Assert.assertEquals("Expected percentage to be 20.00, not " + line.getPercentage(), new BigDecimal("50.00"), line.getPercentage());
            }
        }
    }
    
    private List<DistributionLine> setupGiftDistributionLinesForRecurringGift() {
        List<DistributionLine> lines = new ArrayList<DistributionLine>();
        DistributionLine line = new DistributionLine(new BigDecimal(3), new BigDecimal(15), "00001", "abcde", null);
        line.setId(1L);
        lines.add(line);
        
        line = new DistributionLine(new BigDecimal(5.25), new BigDecimal(20), null, null, "sss");
        line.setId(4L);
        line.addCustomFieldValue(StringConstants.ASSOCIATED_RECURRING_GIFT_ID, "5");
        lines.add(line);
        
        line = new DistributionLine(new BigDecimal(50), new BigDecimal(79), null, null, null);
        line.setId(5L);
        line.addCustomFieldValue(StringConstants.ASSOCIATED_RECURRING_GIFT_ID, "7");
        lines.add(line);
        
        return lines;
    }
    
    private List<DistributionLine> setupRecurringGiftDistributionLines() {
        List<DistributionLine> lines = new ArrayList<DistributionLine>();
        DistributionLine line = new DistributionLine(new BigDecimal(1.99), new BigDecimal(100), null, "fghij", null);
        line.setId(101L);
        line.setRecurringGiftId(88888L);
        lines.add(line);
        
        line = new DistributionLine(new BigDecimal(25), new BigDecimal(100), null, null, null);
        line.setId(103L);
        line.setRecurringGiftId(7L);
        lines.add(line);
        
        return lines;
    }
    
    @Test
    public void testMaintainGiftMajorDonor() throws Exception {
        Gift gift = getGift();
        gift.setSuppressValidation(true);
    	giftService.maintainGift(gift);
    	Constituent constituent = constituentService.readConstituentById(gift.getConstituent().getId());
    	//assert constituent.isMajorDonor();
    }
    
    private Gift getGift() throws Exception {
    	 // Insert
        Gift gift = new Gift();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        gift.setDonationDate(sdf.parse("10/31/1999"));
        gift.setEntryType(GiftEntryType.AUTO);
        gift.setAmount(new BigDecimal(100000.00));
        gift.setCurrencyCode("USD");
        gift.setTxRefNum("0101010101");
        gift.setGiftType(GiftType.MONETARY_GIFT);
        Address addr = new Address();
        addr.setId(100L);
        gift.setAddress(addr);
        PaymentSource src = new PaymentSource();
        src.setId(100L);
        gift.setPaymentSource(src);
        Site site = new Site("company1");
        Constituent constituent = new Constituent();
        constituent.setId(100L);
        constituent.setSite(site);
        gift.setConstituent(constituent);
        List<DistributionLine> lines = new ArrayList<DistributionLine>();
        
        DistributionLine line = new DistributionLine();
        line.setAmount(new BigDecimal(80000.00));
        line.setProjectCode("proj1");
        line.setGiftId(gift.getId());
        lines.add(line);
        
        line = new DistributionLine();
        line.setAmount(new BigDecimal(20000.00));
        line.setProjectCode("proj2");
        line.setGiftId(gift.getId());
        lines.add(line);
        
        gift.setDistributionLines(lines);
        return gift;
    }
    
    @Test
    public void testCheckAssociatedPledgeIds() throws Exception {
        Gift gift = new Gift();
        DistributionLine aLine = new DistributionLine();
        aLine.setCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID, "1");
        gift.addDistributionLine(aLine);
        giftService.checkAssociatedPledgeIds(gift);
        assert gift.getAssociatedPledgeIds().size() == 1 && gift.getAssociatedPledgeIds().get(0) == 1L;
        
        gift = new Gift();
        aLine = new DistributionLine();
        aLine.setCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID, "1");
	    gift.addDistributionLine(aLine);
        gift.addAssociatedPledgeId(1L);
        giftService.checkAssociatedPledgeIds(gift);
        assert gift.getAssociatedPledgeIds().size() == 1 && gift.getAssociatedPledgeIds().get(0) == 1L;

        gift = new Gift();
        aLine = new DistributionLine();
        aLine.setCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID, "2");
	    gift.addDistributionLine(aLine);
        gift.addAssociatedPledgeId(1L);
        giftService.checkAssociatedPledgeIds(gift);
        assert gift.getAssociatedPledgeIds().size() == 1 && gift.getAssociatedPledgeIds().get(0) == 2L;

        gift = new Gift();
        aLine = new DistributionLine();
        aLine.setCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID, "2");
	    gift.addDistributionLine(aLine);
        aLine = new DistributionLine();
        aLine.setCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID, "3");
	    gift.addDistributionLine(aLine);
        gift.addAssociatedPledgeId(3L);
        giftService.checkAssociatedPledgeIds(gift);
        assert gift.getAssociatedPledgeIds().size() == 2;
        for (Long id : gift.getAssociatedPledgeIds()) {
            assert id == 2L || id == 3L;
        }

        gift = new Gift();
        aLine = new DistributionLine();
        aLine.setCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID, "2");
	    gift.addDistributionLine(aLine);
        aLine = new DistributionLine();
        aLine.setCustomFieldValue(StringConstants.ASSOCIATED_PLEDGE_ID, "3");
	    gift.addDistributionLine(aLine);
        gift.addAssociatedPledgeId(2L);
        gift.addAssociatedPledgeId(3L);
        giftService.checkAssociatedPledgeIds(gift);
        assert gift.getAssociatedPledgeIds().size() == 2;
        for (Long id : gift.getAssociatedPledgeIds()) {
            assert id == 2L || id == 3L;
        }

        gift = new Gift();
        gift.addAssociatedPledgeId(1L);
        gift.addAssociatedPledgeId(5L);
        giftService.checkAssociatedPledgeIds(gift);
        assert gift.getAssociatedPledgeIds().isEmpty();

        gift = new Gift();
        giftService.checkAssociatedPledgeIds(gift);
        assert gift.getAssociatedPledgeIds() == null;
    }
}
