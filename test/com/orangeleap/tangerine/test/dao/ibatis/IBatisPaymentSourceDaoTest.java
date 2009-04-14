package com.orangeleap.tangerine.test.dao.ibatis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.dao.PaymentSourceDao;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.communication.Phone;

public class IBatisPaymentSourceDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private PaymentSourceDao paymentSourceDao;

    @BeforeMethod
    public void setup() {
        paymentSourceDao = (PaymentSourceDao)super.applicationContext.getBean("paymentSourceDAO");
    }
    
    public static void testId200(PaymentSource src) {
        assert src.getCreditCardExpiration() != null;
        assert "Billy Graham".equals(src.getCreditCardHolderName());
        assert "4222".equals(src.getCreditCardNumberEncrypted());
        assert "Billy Graham Visa".equals(src.getProfile());
        assert src.isInactive() == false;
        assert src.getAchAccountNumber() == null;
        assert src.getAchRoutingNumber() == null;
        assert PaymentSource.CREDIT_CARD.equals(src.getPaymentType());
    }
    
    public static void testId500(PaymentSource src) {
        assert src.getCreditCardExpiration() == null;
        assert src.getCreditCardNumber() == null;
        assert "Ruth ACH".equals(src.getProfile());
        assert "999999".equals(src.getAchAccountNumberEncrypted());
        assert "1234".equals(src.getAchRoutingNumber());
        assert PaymentSource.ACH.equals(src.getPaymentType());
    }

    public static void testId900(PaymentSource src) {
        assert src.getCreditCardExpiration() != null;
        assert "Franklin Graham".equals(src.getCreditCardHolderName());
        assert "4222".equals(src.getCreditCardNumberEncrypted());
        assert src.isInactive();
        assert "Frank Graham Visa".equals(src.getProfile());
        assert src.getAchAccountNumber() == null;
        assert src.getAchRoutingNumber() == null;
        assert PaymentSource.CREDIT_CARD.equals(src.getPaymentType());
    }
    
    @Test(groups = { "testMaintainPaymentSource" }, dependsOnGroups = { "testReadPaymentSource" })
    public void testMaintainPaymentSource() throws Exception {
        // Insert
        PaymentSource src = new PaymentSource();
        
        Person constituent = new Person();
        constituent.setId(200L);
        src.setPerson(constituent);
        
        Address addr = new Address();
        addr.setId(100L);
        src.setSelectedAddress(addr);
        
        src.setPaymentType(PaymentSource.CHECK);
        src.setInactive(false);
        
        src = paymentSourceDao.maintainPaymentSource(src);
        assert src.getId() > 0;
        PaymentSource readSource = paymentSourceDao.readPaymentSourceById(src.getId());
        assert src.getPaymentType().equals(readSource.getPaymentType());
        assert src.isInactive() == readSource.isInactive();
        assert readSource.getAchAccountNumber() == null;
        assert readSource.getAchRoutingNumber() == null;
        assert readSource.getCreditCardExpiration() == null;
        assert readSource.getCreditCardNumber() == null;
        assert readSource.getCreditCardType() == null;
        assert readSource.getProfile() == null;
        assert readSource.getPerson() != null && 200 == readSource.getPerson().getId();
        assert readSource.getSelectedAddress() != null && 100 == readSource.getSelectedAddress().getId();
        assert readSource.getSelectedPhone() != null && readSource.getSelectedPhone().getId() == null;
        
        // Update
        src.setPaymentType(PaymentSource.CREDIT_CARD);
        src.setInactive(true);
        src.setCreditCardExpiration(new Date());
        src.setCreditCardHolderName("Big Bird");
        src.setCreditCardNumberEncrypted("0000");
        src.setCreditCardType("Visa");
        src.setProfile("Big Bird Visa");
        src.setSelectedAddress(null);
        
        Phone phone = new Phone();
        phone.setId(100L);
        src.setSelectedPhone(phone);
        src = paymentSourceDao.maintainPaymentSource(src);
        
        readSource = paymentSourceDao.readPaymentSourceById(src.getId());
        assert PaymentSource.CREDIT_CARD.equals(readSource.getPaymentType());
        assert readSource.isInactive();
        assert readSource.getAchAccountNumber() == null;
        assert readSource.getAchRoutingNumber() == null;
        assert readSource.getCreditCardExpiration() != null;
        assert "0000".equals(readSource.getCreditCardNumberEncrypted());
        assert "Big Bird".equals(readSource.getCreditCardHolderName());
        assert "Visa".equals(readSource.getCreditCardType());
        assert "Big Bird Visa".equals(readSource.getProfile());
        assert readSource.getPerson() != null && 200L == readSource.getPerson().getId();
        assert readSource.getSelectedAddress() != null && readSource.getSelectedAddress().getId() == null;
        assert readSource.getSelectedPhone() != null && 100L == readSource.getSelectedPhone().getId();
    }

    @Test(groups = { "testReadPaymentSource" })
    public void testReadPaymentSourceById() throws Exception {
        PaymentSource source = paymentSourceDao.readPaymentSourceById(0L);
        assert source == null;

        source = paymentSourceDao.readPaymentSourceById(100L);
        assert source != null;
        assert 100L == source.getId();
        assert "000001".equals(source.getAchAccountNumberEncrypted());
        assert "Joe Blow".equals(source.getAchHolderName());
        assert "1234".equals(source.getAchRoutingNumber());
        assert source.isInactive();
        assert "Joe ACH".equals(source.getProfile());
        assert PaymentSource.ACH.equals(source.getPaymentType());
        assert source.getCreditCardExpiration() == null;
        assert source.getCreditCardNumber() == null;
        assert source.getCreditCardType() == null;
        assert source.getSelectedAddress() != null && 100L == source.getSelectedAddress().getId();
        assert "3726 THIRD ST".equals(source.getSelectedAddress().getAddressLine1());
        assert source.getSelectedPhone() != null && 100L == source.getSelectedPhone().getId();
        assert "214-443-6829".equals(source.getSelectedPhone().getNumber());
        assert source.getPerson() != null && 100L == source.getPerson().getId();
        assert "Billy Graham Ministries".equals(source.getPerson().getOrganizationName());
        assert "Graham".equals(source.getPerson().getLastName());
        assert "Billy".equals(source.getPerson().getFirstName());
    } 
    
    @Test(groups = { "testReadPaymentSource" })
    public void testReadPaymentSourceByProfile() throws Exception {
        PaymentSource source = paymentSourceDao.readPaymentSourceByProfile(300L, "foo");
        assert source == null;

        source = paymentSourceDao.readPaymentSourceByProfile(300L, "Halle Discover");
        assert source != null;
        assert 600L == source.getId();
        assert "3111".equals(source.getCreditCardNumberEncrypted());
        assert "Halle Berry".equals(source.getCreditCardHolderName());
        assert source.isInactive() == false;
        assert "Halle Discover".equals(source.getProfile());
        assert PaymentSource.CREDIT_CARD.equals(source.getPaymentType());
        assert source.getCreditCardExpiration() != null;
        assert "Discover".equals(source.getCreditCardType());
        assert source.getSelectedAddress() != null && source.getSelectedAddress().getId() == null;
        assert source.getSelectedPhone() != null && source.getSelectedPhone().getId() == null;
        assert source.getPerson() != null && 300L == source.getPerson().getId();
        assert "Doody".equals(source.getPerson().getLastName());
        assert "Howdy".equals(source.getPerson().getFirstName());
        
        assert source.getAchAccountNumber() == null;
        assert source.getAchRoutingNumber() == null;
    }

    @Test(groups = { "testReadPaymentSource" })
    public void testReadAllPaymentSources() throws Exception {
        List<PaymentSource> sources = paymentSourceDao.readAllPaymentSources(100L);
        assert sources != null && sources.size() == 6;
        for (int i = 0; i < 3; i++) {
            assert sources.get(i).isInactive() == false;
        }
        for (int i = 3; i < 6; i++) {
            assert sources.get(i).isInactive() == true;
        }
    }
    
    @Test(groups = { "testReadPaymentSource" })
    public void testReadActivePaymentSources() throws Exception {
        List<PaymentSource> sources = paymentSourceDao.readActivePaymentSources(200L);
        assert sources != null && sources.isEmpty();
        
        sources = paymentSourceDao.readActivePaymentSources(100L);
        assert sources != null && sources.size() == 3;
        
        for (PaymentSource src : sources) {
            assert src.getId() == 200 || src.getId() == 300 || src.getId() == 500;
            assert src.isInactive() == false;
            switch (src.getId().intValue()) {
                case 200: 
                    testId200(src);
                    assert src.getPerson() != null && src.getPerson().getId() == 100L;
                    break;
                case 300:
                    assert src.getCreditCardExpiration() == null;
                    assert src.getCreditCardNumberEncrypted() == null;
                    assert src.getProfile() == null;
                    assert src.getAchAccountNumber() == null;
                    assert src.getAchRoutingNumber() == null;
                    assert PaymentSource.CASH.equals(src.getPaymentType());
                    break;
                case 500:
                    testId500(src);
                    assert src.getPerson() != null && src.getPerson().getId() == 100L;
                    break;
                default:
                    assert false == true;
            }
        }
    }
    
    @Test(groups = { "testReadPaymentSource" })
    public void testReadActivePaymentSourcesByTypes() throws Exception {
        List<String> types = new ArrayList<String>();
        types.add(PaymentSource.CASH);
        List<PaymentSource> sources = paymentSourceDao.readActivePaymentSourcesByTypes(300L, types);
        assert sources != null && sources.isEmpty();
        
        types = new ArrayList<String>();
        types.add(PaymentSource.CREDIT_CARD);
        types.add(PaymentSource.ACH);
        sources = paymentSourceDao.readActivePaymentSourcesByTypes(100L, types);
        assert sources != null && sources.size() == 2;
        
        for (PaymentSource src : sources) {
            assert src.getId() == 200 || src.getId() == 500;
            assert src.isInactive() == false;
            switch (src.getId().intValue()) {
                case 200: 
                    testId200(src);
                    assert src.getPerson() != null && src.getPerson().getId() == 100L;
                    break;
                case 500:
                    testId500(src);
                    assert src.getPerson() != null && src.getPerson().getId() == 100L;
                    break;
                default:
                    assert false == true;
            }
        }        
    }
    
    @Test(groups = { "testReadPaymentSource" })
    public void testReadExistingCreditCards() throws Exception {
        List<PaymentSource> sources = paymentSourceDao.readExistingCreditCards("foo");
        assert sources != null && sources.isEmpty();
        
        sources = paymentSourceDao.readExistingCreditCards("4222");
        assert sources != null && sources.size() == 2;
        for (PaymentSource src : sources) {
            if (src.getId() == 200L) {
                testId200(src);
                assert src.getPerson() != null && src.getPerson().getId() == 100L;
            }
            else if (src.getId() == 900L) {
                testId900(src);
                assert src.getPerson() != null && src.getPerson().getId() == 100L;
            }
            else {
                Assert.assertTrue("Unexpected id = " + src.getId(), false);
            }
        }
    }

    @Test(groups = { "testReadPaymentSource" })
    public void testReadExistingAchAccounts() throws Exception {
        List<PaymentSource> sources = paymentSourceDao.readExistingAchAccounts("foo", "bar");
        assert sources != null && sources.isEmpty();
        
        sources = paymentSourceDao.readExistingAchAccounts("999999", "1234");
        assert sources != null && sources.size() == 1;
        testId500(sources.get(0));
        assert sources.get(0).getPerson() != null && sources.get(0).getPerson().getId() == 100L;
    }
}
