package com.orangeleap.tangerine.test.dao.ibatis;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.dao.GiftDao;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.type.GiftEntryType;
import com.orangeleap.tangerine.type.GiftType;
import com.orangeleap.tangerine.util.StringConstants;

public class IBatisGiftDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private GiftDao giftDao;

    @BeforeMethod
    public void setup() {
        giftDao = (GiftDao)super.applicationContext.getBean("giftDAO");
    }
    
    public static void testGiftId100(Gift gift) {
        assert gift != null;
        assert 100L == gift.getId(); 
        assert "Thank you for your support".equals(gift.getComments());
        assert gift.getTransactionDate() != null;
        assert 1000 == gift.getAmount().intValue();
        assert 1000 == gift.getDeductibleAmount().intValue();
        assert PaymentSource.CASH.equals(gift.getPaymentType());
        assert gift.isDeductible() == true;
        assert GiftEntryType.MANUAL.equals(gift.getEntryType());
        assert gift.getRecurringGiftId() == null;
        assert gift.getDonationDate() == null;
        assert gift.getPostmarkDate() == null;
        assert gift.getAuthCode() == null;
        assert gift.getOriginalGiftId() == null;
        assert gift.getRefundGiftId() == null;
        assert gift.getRefundGiftTransactionDate() == null;
        assert gift.getTxRefNum() == null;
        assert gift.getPaymentStatus() == null;
        assert gift.getPaymentMessage() == null;
        assert StringConstants.USD.equals(gift.getCurrencyCode());
        assert gift.getCheckNumber() == null;
        assert gift.isSendAcknowledgment() == false;
        assert gift.getAcknowledgmentDate() == null;
        assert GiftType.MONETARY_GIFT.equals(gift.getGiftType());
        
        assert gift.getPerson() != null && gift.getPerson().getId() == 100L;
        assert "Billy Graham Ministries".equals(gift.getPerson().getOrganizationName());
        assert "Graham".equals(gift.getPerson().getLastName());
        assert "Billy".equals(gift.getPerson().getFirstName());
        
        assert gift.getSelectedAddress() != null && gift.getSelectedAddress().getId() == 100L;
        assert "3726 THIRD ST".equals(gift.getSelectedAddress().getAddressLine1());
        assert "Dallas".equals(gift.getSelectedAddress().getCity());
        assert "TX".equals(gift.getSelectedAddress().getStateProvince());
        assert "75554".equals(gift.getSelectedAddress().getPostalCode());
        assert "US".equals(gift.getSelectedAddress().getCountry());
        assert gift.getSelectedAddress().getAddressLine2() == null;
        assert gift.getSelectedAddress().getAddressLine3() == null;
        
        assert gift.getSelectedPhone() != null && gift.getSelectedPhone().getId() == 100L;
        assert "214-443-6829".equals(gift.getSelectedPhone().getNumber());
        assert gift.getSelectedPhone().getCreateDate() != null;
        assert gift.getSelectedPhone().getUpdateDate() != null;
        assert 100L == gift.getSelectedPhone().getPersonId();
        
        assert gift.getSelectedEmail() != null && gift.getSelectedEmail().getId() == 100L;
        assert "hobo@gmail.com".equals(gift.getSelectedEmail().getEmailAddress());
        assert gift.getSelectedEmail().isInactive() == false;
        
        assert gift.getSelectedPaymentSource() != null && gift.getSelectedPaymentSource().getId() == null;
    }
    
    private void setupDistributionLines(Gift gift) {
        List<DistributionLine> lines = new ArrayList<DistributionLine>();
        
        DistributionLine line = new DistributionLine();
        line.setAmount(new BigDecimal(100.50));
        line.setProjectCode("foo");
        line.setGiftId(gift.getId());
        lines.add(line);
        
        line = new DistributionLine();
        line.setAmount(new BigDecimal(125.50));
        line.setMotivationCode("bar");
        line.setGiftId(gift.getId());
        lines.add(line);
        
        gift.setDistributionLines(lines);
    }

    @Test(groups = { "testMaintainGift" }, dependsOnGroups = { "testReadGift" })
    public void testMaintainGift() throws Exception {
        // Insert
        Gift gift = new Gift();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        gift.setAcknowledgmentDate(sdf.parse("01/01/2001"));
        gift.setDonationDate(sdf.parse("10/31/1999"));
        gift.setEntryType(GiftEntryType.AUTO);
        gift.setAmount(new BigDecimal(125.50));
        gift.setCurrencyCode("JPY");
        gift.setComments("foobar!!");
        gift.setTxRefNum("0101010101");
        gift.setGiftType(GiftType.MONETARY_GIFT);
        Address addr = new Address();
        addr.setId(100L);
        gift.setSelectedAddress(addr);
        PaymentSource src = new PaymentSource();
        src.setId(100L);
        gift.setSelectedPaymentSource(src);
        Site site = new Site("company1");
        Person person = new Person();
        person.setId(100L);
        person.setSite(site);
        gift.setPerson(person);
        setupDistributionLines(gift);
        
        gift = giftDao.maintainGift(gift);
        assert gift.getId() > 0;
        
        Gift readGift = giftDao.readGiftById(gift.getId());
        assert readGift != null;
        assert gift.getId().equals(readGift.getId());
        assert sdf.parse("01/01/2001").equals(readGift.getAcknowledgmentDate());
        assert sdf.parse("10/31/1999").equals(readGift.getDonationDate());
        assert GiftEntryType.AUTO.equals(readGift.getEntryType());
        assert 125.5 == readGift.getAmount().floatValue();
        assert "JPY".equals(readGift.getCurrencyCode());
        assert "foobar!!".equals(readGift.getComments());
        assert "0101010101".equals(readGift.getTxRefNum());
        assert GiftType.MONETARY_GIFT.equals(readGift.getGiftType());
        assert readGift.getSelectedAddress() != null && 100L == readGift.getSelectedAddress().getId();
        assert readGift.getSelectedPhone() != null && readGift.getSelectedPhone().getId() == null;
        assert readGift.getSelectedEmail() != null && readGift.getSelectedEmail().getId() == null;
        assert readGift.getSelectedPaymentSource() != null && 100L == readGift.getSelectedPaymentSource().getId();
        assert readGift.getPerson() != null && 100L == readGift.getPerson().getId();
        Assert.assertNotNull("readGift distributionLines is null", readGift.getDistributionLines());
        Assert.assertEquals("readGift distributionLines size is " + readGift.getDistributionLines().size(), 2, readGift.getDistributionLines().size());
        assert readGift.getRecurringGiftId() == null;
        assert readGift.getPostmarkDate() == null;
        assert StringConstants.EMPTY.equals(readGift.getAuthCode());
        assert readGift.getOriginalGiftId() == null;
        assert readGift.getRefundGiftId() == null;
        assert readGift.getRefundGiftTransactionDate() == null;
        assert readGift.getPaymentStatus() == null;
        assert readGift.getPaymentMessage() == null;
        assert readGift.getCheckNumber() == null;
        assert readGift.isSendAcknowledgment() == false;

        for (DistributionLine line : readGift.getDistributionLines()) {
            assert readGift.getId().equals(line.getGiftId());
            if (100.5 == line.getAmount().floatValue()) {
                assert "foo".equals(line.getProjectCode());
                assert line.getMotivationCode() == null;
                assert line.getOther_motivationCode() == null;
                assert line.getPledgeId() == null;
            }
            else if (125.5 == line.getAmount().floatValue()) {
                assert "bar".equals(line.getMotivationCode());
                assert line.getProjectCode() == null;
                assert line.getOther_motivationCode() == null;
                assert line.getPledgeId() == null;
            }
            else {
                assert false == true;
            }
        }
        
        // Update
        gift = readGift;
        gift.setAcknowledgmentDate(null);
        gift.setSendAcknowledgment(true);
        gift.setPaymentMessage("Hi mom");
        gift.setCheckNumber(111);
        gift.setEntryType(GiftEntryType.MANUAL);
        gift.setCurrencyCode("USD");
        
        gift.setSelectedAddress(null);
        Phone phone = new Phone();
        phone.setId(100L);
        gift.setSelectedPhone(phone);
        for (DistributionLine line : gift.getDistributionLines()) {
            if (100.5 == line.getAmount().floatValue()) {
                line.setProjectCode(null);
                line.setOther_motivationCode("sss");
                line.setAmount(new BigDecimal(0.5));
            }
        }
        gift = giftDao.maintainGift(gift);
        readGift = giftDao.readGiftById(gift.getId());
        assert readGift != null;
        assert gift.getId().equals(readGift.getId());
        assert readGift.getAcknowledgmentDate() == null;
        assert sdf.parse("10/31/1999").equals(readGift.getDonationDate());
        assert GiftEntryType.MANUAL.equals(readGift.getEntryType());
        assert GiftType.MONETARY_GIFT.equals(readGift.getGiftType());
        assert 125.5 == readGift.getAmount().floatValue();
        assert "USD".equals(readGift.getCurrencyCode());
        assert "foobar!!".equals(readGift.getComments());
        assert "0101010101".equals(readGift.getTxRefNum());
        assert readGift.getSelectedAddress() != null && readGift.getSelectedAddress().getId() == null;
        assert readGift.getSelectedPhone() != null && readGift.getSelectedPhone().getId() == 100L;
        assert readGift.getSelectedEmail() != null && readGift.getSelectedEmail().getId() == null;
        assert readGift.getSelectedPaymentSource() != null && 100L == readGift.getSelectedPaymentSource().getId();
        assert readGift.getPerson() != null && 100L == readGift.getPerson().getId();
        Assert.assertNotNull("readGift distributionLines is null", readGift.getDistributionLines());
        Assert.assertEquals("readGift distributionLines size is " + readGift.getDistributionLines().size(), 2, readGift.getDistributionLines().size());
        assert readGift.getRecurringGiftId() == null;
        assert readGift.getPostmarkDate() == null;
        assert StringConstants.EMPTY.equals(readGift.getAuthCode());
        assert readGift.getOriginalGiftId() == null;
        assert readGift.getRefundGiftId() == null;
        assert readGift.getRefundGiftTransactionDate() == null;
        assert readGift.getPaymentStatus() == null;
        assert "Hi mom".equals(readGift.getPaymentMessage());
        assert readGift.getCheckNumber() == 111;
        assert readGift.isSendAcknowledgment() == true;

        for (DistributionLine line : readGift.getDistributionLines()) {
            assert readGift.getId().equals(line.getGiftId());
            if (0.5 == line.getAmount().floatValue()) {
                assert line.getProjectCode() == null;
                assert line.getMotivationCode() == null;
                assert "sss".equals(line.getOther_motivationCode());
                assert line.getPledgeId() == null;
            }
            else if (125.5 == line.getAmount().floatValue()) {
                assert "bar".equals(line.getMotivationCode());
                assert line.getProjectCode() == null;
                assert line.getOther_motivationCode() == null;
                assert line.getPledgeId() == null;
            }
            else {
                Assert.assertEquals("amount = " + line.getAmount().floatValue(), false, true);
            }
        }
    }
    
    @Test(groups = { "testReadGift" })
    public void testReadGiftById() throws Exception {
        Gift gift = giftDao.readGiftById(0L);
        assert gift == null;
        
        gift = giftDao.readGiftById(100L);
        testGiftId100(gift);
        
        assert gift.getDistributionLines() != null && gift.getDistributionLines().isEmpty();        
    }
    
    @Test(groups = { "testReadGift" })
    public void testReadGiftsByConstituentId() throws Exception {
        List<Gift> gifts = giftDao.readMonetaryGiftsByConstituentId(0L);
        assert gifts != null && gifts.isEmpty();
        
        gifts = giftDao.readMonetaryGiftsByConstituentId(200L);
        assert gifts != null && gifts.size() == 2;
        
        for (Gift gift : gifts) {
            assert gift.getId() == 300L || gift.getId() == 400L;

            assert "Painters, Inc.".equals(gift.getPerson().getOrganizationName());
            assert "Picasso".equals(gift.getPerson().getLastName());
            assert "Pablo".equals(gift.getPerson().getFirstName());
            assert gift.getPerson().getMiddleName() == null;
            assert "Sr".equals(gift.getPerson().getSuffix());
            assert "company1".equals(gift.getPerson().getSite().getName());
            assert GiftType.MONETARY_GIFT.equals(gift.getGiftType());

            switch (gift.getId().intValue()) {
                case 300: 
                    assert "Straight Cash Homey".equals(gift.getComments());
                    assert gift.getTransactionDate() != null;
                    assert 300 == gift.getAmount().intValue();
                    assert 300== gift.getDeductibleAmount().intValue();
                    assert PaymentSource.ACH.equals(gift.getPaymentType());
                    assert gift.isDeductible() == false;
                    assert GiftEntryType.MANUAL.equals(gift.getEntryType());
                    assert gift.getRecurringGiftId() == 300L;
                    assert gift.getDonationDate() == null;
                    assert gift.getPostmarkDate() == null;
                    assert gift.getAuthCode() == null;
                    assert gift.getOriginalGiftId() == null;
                    assert gift.getRefundGiftId() == null;
                    assert gift.getRefundGiftTransactionDate() == null;
                    assert gift.getTxRefNum() == null;
                    assert gift.getPaymentStatus() == null;
                    assert gift.getPaymentMessage() == null;
                    assert StringConstants.USD.equals(gift.getCurrencyCode());
                    assert gift.getCheckNumber() == null;
                    assert gift.isSendAcknowledgment() == false;
                    assert gift.getAcknowledgmentDate() == null;
                    
                    assert gift.getSelectedEmail() != null && gift.getSelectedEmail().getId() == 200L;
                    assert "samsam@yahoo.com".equals(gift.getSelectedEmail().getEmailAddress());
                    assert gift.getSelectedEmail().isInactive() == false;

                    assert gift.getDistributionLines() != null && gift.getDistributionLines().size() == 4;
                    for (DistributionLine line : gift.getDistributionLines()) {
                        assert line.getId() >= 100L && line.getId() <= 400L;
                        assert 300L == line.getGiftId();
                        switch (line.getId().intValue()) {
                            case 100:
                                assert 150 == line.getAmount().intValue();
                                assert 50 == line.getPercentage().intValue();
                                assert "01000".equals(line.getProjectCode());
                                assert line.getMotivationCode() == null;
                                assert line.getOther_motivationCode() == null;
                                assert line.getPledgeId() == null;
                                break;
                            case 200:
                                assert 75 == line.getAmount().intValue();
                                assert 25 == line.getPercentage().intValue();
                                assert line.getProjectCode() == null;
                                assert line.getMotivationCode() == null;
                                assert "whoa".equals(line.getOther_motivationCode());
                                assert line.getPledgeId() == null;
                                break;
                            case 300:
                                assert 37.5 == line.getAmount().floatValue();
                                assert 12.5 == line.getPercentage().floatValue();
                                assert line.getProjectCode() == null;
                                assert "foo".equals(line.getMotivationCode());
                                assert line.getOther_motivationCode() == null;
                                assert line.getPledgeId() == null;
                                break;
                            case 400:
                                assert 37.5 == line.getAmount().floatValue();
                                assert 12.5 == line.getPercentage().floatValue();
                                assert line.getProjectCode() == null;
                                assert line.getMotivationCode() == null;
                                assert line.getOther_motivationCode() == null;
                                assert line.getPledgeId() == null;
                                break;
                            default:
                                assert false == true;
                        }
                    }

                    assert gift.getSelectedAddress() != null && gift.getSelectedAddress().getId() == null;
                    assert gift.getSelectedPhone() != null && gift.getSelectedPhone().getId() == null;
                    assert gift.getSelectedPaymentSource() != null && gift.getSelectedPaymentSource().getId() == null;
                    
                    break;
                case 400:
                    assert "Rollin".equals(gift.getComments());
                    assert gift.getTransactionDate() != null;
                    assert 99999 == gift.getAmount().intValue();
                    assert 99999== gift.getDeductibleAmount().intValue();
                    assert PaymentSource.CREDIT_CARD.equals(gift.getPaymentType());
                    assert gift.isDeductible() == false;
                    assert GiftEntryType.MANUAL.equals(gift.getEntryType());
                    assert gift.getRecurringGiftId() == 300L;
                    assert gift.getDonationDate() == null;
                    assert gift.getPostmarkDate() == null;
                    assert gift.getAuthCode() == null;
                    assert gift.getOriginalGiftId() == null;
                    assert gift.getRefundGiftId() == null;
                    assert gift.getRefundGiftTransactionDate() == null;
                    assert gift.getTxRefNum() == null;
                    assert gift.getPaymentStatus() == null;
                    assert gift.getPaymentMessage() == null;
                    assert StringConstants.USD.equals(gift.getCurrencyCode());
                    assert gift.getCheckNumber() == null;
                    assert gift.isSendAcknowledgment() == false;
                    assert gift.getAcknowledgmentDate() == null;
                    
                    assert gift.getSelectedAddress() != null && gift.getSelectedAddress().getId() == 100L;
                    assert "3726 THIRD ST".equals(gift.getSelectedAddress().getAddressLine1());
                    assert "Dallas".equals(gift.getSelectedAddress().getCity());
                    assert "TX".equals(gift.getSelectedAddress().getStateProvince());
                    assert "75554".equals(gift.getSelectedAddress().getPostalCode());
                    assert "US".equals(gift.getSelectedAddress().getCountry());
                    assert gift.getSelectedAddress().getAddressLine2() == null;
                    assert gift.getSelectedAddress().getAddressLine3() == null;
                    
                    assert gift.getSelectedPaymentSource() != null && gift.getSelectedPaymentSource().getId() == 200L;
                    assert gift.getSelectedPaymentSource().getCreditCardExpiration() != null;
                    assert "Billy Graham".equals(gift.getSelectedPaymentSource().getCreditCardHolderName());
                    assert "4222".equals(gift.getSelectedPaymentSource().getCreditCardNumber());
                    assert "Billy Graham Visa".equals(gift.getSelectedPaymentSource().getProfile());
                    assert gift.getSelectedPaymentSource().getAchAccountNumber() == null;
                    assert gift.getSelectedPaymentSource().getAchRoutingNumber() == null;
                    assert PaymentSource.CREDIT_CARD.equals(gift.getSelectedPaymentSource().getPaymentType());

                    assert gift.getSelectedPhone() != null && gift.getSelectedPhone().getId() == null;
                    assert gift.getSelectedEmail() != null && gift.getSelectedEmail().getId() == null;
                    assert gift.getDistributionLines() != null && gift.getDistributionLines().isEmpty();
                    break;
                default: 
                    assert false == true;
            }
        }
    }
    
    @Test(groups = { "testReadGift" })
    public void testReadAllGiftsBySite() throws Exception {
        List<Gift> gifts = giftDao.readAllGiftsBySite();
        assert gifts != null && gifts.size() == 7;
        for (Gift gift : gifts) {
            assert gift.getId() >= 100L && gift.getId() <= 700L;
        }
    }
    
    @Test(groups = { "testAnalyze" })
    public void testAnalyzeMajorDonor() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy kk:mm");
        assert 1025d == giftDao.analyzeMajorDonor(100L, sdf.parse("06/05/2007 00:00"), sdf.parse("06/07/2007 00:00"));
        assert 0 == giftDao.analyzeMajorDonor(100L, sdf.parse("01/01/1990 00:00"), sdf.parse("01/02/1990 00:00"));
        assert 1000d == giftDao.analyzeMajorDonor(100L, sdf.parse("06/06/2007 00:00"), sdf.parse("06/07/2007 00:00"));
    }
    
    @Test(groups = { "testAnalyze" })
    public void testAnalyzeLapsedDonor() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy kk:mm");
        List<Person> constituents = giftDao.analyzeLapsedDonor(sdf.parse("06/05/2007 00:00"), sdf.parse("06/07/2007 00:00"));
        assert constituents != null && constituents.size() == 1 && constituents.get(0).getId() == 100L;
        
        constituents = giftDao.analyzeLapsedDonor(sdf.parse("06/03/2007 00:00"), sdf.parse("06/07/2007 00:00"));
        assert constituents != null && constituents.size() == 2;
        for (Person constituent : constituents) {
            assert constituent.getId() == 100L || constituent.getId() == 200L;
            switch (constituent.getId().intValue()) {
                case 100:
                    assert "Billy Graham Ministries".equals(constituent.getOrganizationName());
                    assert "Graham".equals(constituent.getLastName());
                    assert "Billy".equals(constituent.getFirstName());
                    break;
                case 200:
                    assert "Painters, Inc.".equals(constituent.getOrganizationName());
                    assert "Picasso".equals(constituent.getLastName());
                    assert "Pablo".equals(constituent.getFirstName());
                    break;
                default:
                    Assert.assertEquals("Id = " + constituent.getId(), true, false);
            }
        }
    }
    
    @Test(groups = { "testCommitmentForGift" })
    public void testReadGiftsReceivedSumByRecurringGiftId() throws Exception {
        assert BigDecimal.ZERO.equals(giftDao.readGiftsReceivedSumByRecurringGiftId(0L));
        assert 100299 == giftDao.readGiftsReceivedSumByRecurringGiftId(300L).intValue();
    }
    
    @Test(groups = { "testCommitmentForGift" })
    public void testReadGiftsByRecurringGiftId() throws Exception {
        List<Gift> gifts = giftDao.readGiftsByRecurringGiftId(0L);
        assert gifts != null && gifts.isEmpty();
        gifts = giftDao.readGiftsByRecurringGiftId(300L);
        
        assert gifts.size() == 2;
        for (Gift gift : gifts) {
            assert gift.getId() == 300L || gift.getId() == 400L;
        }
    }
    
    @Test(groups = { "testCommitmentForGift" })
    public void testReadGiftsReceivedSumByPledgeId() throws Exception {
        assert BigDecimal.ZERO.equals(giftDao.readGiftsReceivedSumByPledgeId(0L));
        assert 100299 == giftDao.readGiftsReceivedSumByPledgeId(900L).intValue();
    }
    
    @Test(groups = { "testCommitmentForGift" })
    public void testReadGiftsByPledgeId() throws Exception {
        List<Gift> gifts = giftDao.readGiftsByPledgeId(0L);
        assert gifts != null && gifts.isEmpty();
        gifts = giftDao.readGiftsByPledgeId(900L);
        
        assert gifts.size() == 2;
        for (Gift gift : gifts) {
            assert gift.getId() == 300L || gift.getId() == 400L;
        }
    }
    
    @Test(groups = { "testSearchGifts" })
    public void testSearchGifts() throws Exception {
    	Map<String, Object> params = new HashMap<String, Object>();
        params.put("firstName", "Pablo");
        params.put("accountNumber", new Long(200));
        params.put("phoneMap[home].number", "214-113-2542");
        params.put("addressMap[home].addressLine1", "ACORN");
        params.put("emailMap[home].email", "");
        params.put("amount", new BigDecimal(300.00));
    	
        List<Gift> gifts = giftDao.searchGifts(params);
        assert gifts != null && gifts.size() > 0;
        for (Gift gift : gifts) {
            assert gift.getPerson().getFirstName().equals("Pablo");
            assert gift.getAmount().compareTo(new BigDecimal(300.00)) == 0;
        }
    }    
}
