package com.mpower.test.dao.ibatis;

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

import com.mpower.dao.interfaces.GiftDao;
import com.mpower.domain.model.PaymentSource;
import com.mpower.domain.model.Person;
import com.mpower.domain.model.Site;
import com.mpower.domain.model.communication.Address;
import com.mpower.domain.model.communication.Phone;
import com.mpower.domain.model.paymentInfo.DistributionLine;
import com.mpower.domain.model.paymentInfo.Gift;
import com.mpower.type.GiftEntryType;
import com.mpower.util.StringConstants;

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
        assert gift.getCommitmentId() == null;
        assert gift.getDonationDate() != null;
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
        
        assert gift.getPerson() != null && gift.getPerson().getId() == 100L;
        assert "Billy Graham Ministries".equals(gift.getPerson().getOrganizationName());
        assert "Graham".equals(gift.getPerson().getLastName());
        assert "Billy".equals(gift.getPerson().getFirstName());
        
        assert gift.getAddress() != null && gift.getAddress().getId() == 100L;
        assert "3726 THIRD ST".equals(gift.getAddress().getAddressLine1());
        assert "home".equals(gift.getAddress().getAddressType());
        assert "Dallas".equals(gift.getAddress().getCity());
        assert "TX".equals(gift.getAddress().getStateProvince());
        assert "75554".equals(gift.getAddress().getPostalCode());
        assert "US".equals(gift.getAddress().getCountry());
        assert gift.getAddress().getAddressLine2() == null;
        assert gift.getAddress().getAddressLine3() == null;
        
        assert gift.getPhone() != null && gift.getPhone().getId() == 100L;
        assert "214-443-6829".equals(gift.getPhone().getNumber());
        assert "home".equals(gift.getPhone().getPhoneType());
        assert gift.getPhone().getCreateDate() != null;
        assert gift.getPhone().getUpdateDate() != null;
        assert 100L == gift.getPhone().getPersonId();
        
        assert gift.getEmail() != null && gift.getEmail().getId() == 100L;
        assert "hobo@gmail.com".equals(gift.getEmail().getEmailAddress());
        assert "home".equals(gift.getEmail().getEmailType());
        assert gift.getEmail().isInactive() == false;
        
        assert gift.getPaymentSource() == null;
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
        Address addr = new Address();
        addr.setId(100L);
        gift.setAddress(addr);
        PaymentSource src = new PaymentSource();
        src.setId(100L);
        gift.setPaymentSource(src);
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
        assert readGift.getAddress() != null && 100L == readGift.getAddress().getId();
        assert readGift.getPhone() == null;
        assert readGift.getEmail() == null;
        assert readGift.getPaymentSource() != null && 100L == readGift.getPaymentSource().getId();
        assert readGift.getPerson() != null && 100L == readGift.getPerson().getId();
        assert readGift.getDistributionLines() != null && readGift.getDistributionLines().size() == 2;
        assert readGift.getCommitmentId() == null;
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
                assert line.getCommitmentId() == null;
            }
            else if (125.5 == line.getAmount().floatValue()) {
                assert "bar".equals(line.getMotivationCode());
                assert line.getProjectCode() == null;
                assert line.getOther_motivationCode() == null;
                assert line.getCommitmentId() == null;
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
        
        gift.setAddress(null);
        Phone phone = new Phone();
        phone.setId(100L);
        gift.setPhone(phone);
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
        assert 125.5 == readGift.getAmount().floatValue();
        assert "USD".equals(readGift.getCurrencyCode());
        assert "foobar!!".equals(readGift.getComments());
        assert "0101010101".equals(readGift.getTxRefNum());
        assert readGift.getAddress() == null;
        assert readGift.getPhone() != null && gift.getPhone().getId() == 100L;
        assert readGift.getEmail() == null;
        assert readGift.getPaymentSource() != null && 100L == readGift.getPaymentSource().getId();
        assert readGift.getPerson() != null && 100L == readGift.getPerson().getId();
        assert readGift.getDistributionLines() != null && readGift.getDistributionLines().size() == 2;
        assert readGift.getCommitmentId() == null;
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
                assert line.getCommitmentId() == null;
            }
            else if (125.5 == line.getAmount().floatValue()) {
                assert "bar".equals(line.getMotivationCode());
                assert line.getProjectCode() == null;
                assert line.getOther_motivationCode() == null;
                assert line.getCommitmentId() == null;
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
        List<Gift> gifts = giftDao.readGiftsByConstituentId(0L);
        assert gifts != null && gifts.isEmpty();
        
        gifts = giftDao.readGiftsByConstituentId(200L);
        assert gifts != null && gifts.size() == 2;
        
        for (Gift gift : gifts) {
            assert gift.getId() == 300L || gift.getId() == 400L;

            assert "Painters, Inc.".equals(gift.getPerson().getOrganizationName());
            assert "Picasso".equals(gift.getPerson().getLastName());
            assert "Pablo".equals(gift.getPerson().getFirstName());
            assert gift.getPerson().getMiddleName() == null;
            assert "Sr".equals(gift.getPerson().getSuffix());
            assert "company1".equals(gift.getPerson().getSite().getName());

            switch (gift.getId().intValue()) {
                case 300: 
                    assert "Straight Cash Homey".equals(gift.getComments());
                    assert gift.getTransactionDate() != null;
                    assert 300 == gift.getAmount().intValue();
                    assert 300== gift.getDeductibleAmount().intValue();
                    assert PaymentSource.ACH.equals(gift.getPaymentType());
                    assert gift.isDeductible() == false;
                    assert GiftEntryType.MANUAL.equals(gift.getEntryType());
                    assert gift.getCommitmentId() == 300L;
                    assert gift.getDonationDate() != null;
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
                    
                    assert gift.getEmail() != null && gift.getEmail().getId() == 200L;
                    assert "samsam@yahoo.com".equals(gift.getEmail().getEmailAddress());
                    assert "work".equals(gift.getEmail().getEmailType());
                    assert gift.getEmail().isInactive() == false;

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
                                assert line.getCommitmentId() == null;
                                break;
                            case 200:
                                assert 75 == line.getAmount().intValue();
                                assert 25 == line.getPercentage().intValue();
                                assert line.getProjectCode() == null;
                                assert line.getMotivationCode() == null;
                                assert "whoa".equals(line.getOther_motivationCode());
                                assert line.getCommitmentId() == null;
                                break;
                            case 300:
                                assert 37.5 == line.getAmount().floatValue();
                                assert 12.5 == line.getPercentage().floatValue();
                                assert line.getProjectCode() == null;
                                assert "foo".equals(line.getMotivationCode());
                                assert line.getOther_motivationCode() == null;
                                assert line.getCommitmentId() == null;
                                break;
                            case 400:
                                assert 37.5 == line.getAmount().floatValue();
                                assert 12.5 == line.getPercentage().floatValue();
                                assert line.getProjectCode() == null;
                                assert line.getMotivationCode() == null;
                                assert line.getOther_motivationCode() == null;
                                assert line.getCommitmentId() == null;
                                break;
                            default:
                                assert false == true;
                        }
                    }

                    assert gift.getAddress() == null;
                    assert gift.getPhone() == null;
                    assert gift.getPaymentSource() == null;
                    
                    break;
                case 400:
                    assert "Rollin".equals(gift.getComments());
                    assert gift.getTransactionDate() != null;
                    assert 99999 == gift.getAmount().intValue();
                    assert 99999== gift.getDeductibleAmount().intValue();
                    assert PaymentSource.CREDIT_CARD.equals(gift.getPaymentType());
                    assert gift.isDeductible() == false;
                    assert GiftEntryType.MANUAL.equals(gift.getEntryType());
                    assert gift.getCommitmentId() == 300L;
                    assert gift.getDonationDate() != null;
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
                    
                    assert gift.getAddress() != null && gift.getAddress().getId() == 100L;
                    assert "3726 THIRD ST".equals(gift.getAddress().getAddressLine1());
                    assert "home".equals(gift.getAddress().getAddressType());
                    assert "Dallas".equals(gift.getAddress().getCity());
                    assert "TX".equals(gift.getAddress().getStateProvince());
                    assert "75554".equals(gift.getAddress().getPostalCode());
                    assert "US".equals(gift.getAddress().getCountry());
                    assert gift.getAddress().getAddressLine2() == null;
                    assert gift.getAddress().getAddressLine3() == null;
                    
                    assert gift.getPaymentSource() != null && gift.getPaymentSource().getId() == 200L;
                    assert gift.getPaymentSource().getCreditCardExpiration() != null;
                    assert "Billy Graham".equals(gift.getPaymentSource().getCreditCardHolderName());
                    assert "4222".equals(gift.getPaymentSource().getCreditCardNumber());
                    assert "Billy Graham Visa".equals(gift.getPaymentSource().getProfile());
                    assert gift.getPaymentSource().getAchAccountNumber() == null;
                    assert gift.getPaymentSource().getAchRoutingNumber() == null;
                    assert PaymentSource.CREDIT_CARD.equals(gift.getPaymentSource().getPaymentType());

                    assert gift.getPhone() == null;
                    assert gift.getEmail() == null;
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
        assert gifts != null && gifts.size() == 6;
        for (Gift gift : gifts) {
            assert gift.getId() >= 100L && gift.getId() <= 600L;
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
    public void testReadGiftsReceivedSumByCommitmentId() throws Exception {
        assert BigDecimal.ZERO.equals(giftDao.readGiftsReceivedSumByCommitmentId(0L));
        assert 100299 == giftDao.readGiftsReceivedSumByCommitmentId(300L).intValue();
    }
    
    @Test(groups = { "testCommitmentForGift" })
    public void testReadGiftsByCommitmentId() throws Exception {
        List<Gift> gifts = giftDao.readGiftsByCommitmentId(0L);
        assert gifts != null && gifts.isEmpty();
        gifts = giftDao.readGiftsByCommitmentId(300L);
        
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
        	System.out.println(gift.getPerson().getFirstName());
            assert gift.getPerson().getFirstName().equals("Pablo");
            assert gift.getAmount().compareTo(new BigDecimal(300.00)) == 0;
        }
        
    }    
    

    
 }
