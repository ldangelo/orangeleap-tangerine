package com.mpower.test.dao.ibatis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mpower.dao.interfaces.PaymentSourceDao;
import com.mpower.domain.model.PaymentSource;
import com.mpower.domain.model.Person;
import com.mpower.domain.model.communication.Address;
import com.mpower.domain.model.communication.Phone;

public class IBatisPaymentSourceDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private PaymentSourceDao paymentSourceDao;

    @BeforeMethod
    public void setup() {
        paymentSourceDao = (PaymentSourceDao)super.applicationContext.getBean("paymentSourceDAO");
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
        src.setAddress(addr);
        
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
        assert readSource.getAddress() != null && 100 == readSource.getAddress().getId();
        assert readSource.getPhone() == null;
        
        // Update
        src.setPaymentType(PaymentSource.CREDIT_CARD);
        src.setInactive(true);
        src.setCreditCardExpiration(new Date());
        src.setCreditCardHolderName("Big Bird");
        src.setCreditCardNumber("0000");
        src.setCreditCardType("Visa");
        src.setProfile("Big Bird Visa");
        src.setAddress(null);
        
        Phone phone = new Phone();
        phone.setId(100L);
        src.setPhone(phone);
        src = paymentSourceDao.maintainPaymentSource(src);
        
        readSource = paymentSourceDao.readPaymentSourceById(src.getId());
        assert PaymentSource.CREDIT_CARD.equals(readSource.getPaymentType());
        assert readSource.isInactive();
        assert readSource.getAchAccountNumber() == null;
        assert readSource.getAchRoutingNumber() == null;
        assert readSource.getCreditCardExpiration() != null;
        assert "0000".equals(readSource.getCreditCardNumber());
        assert "Big Bird".equals(readSource.getCreditCardHolderName());
        assert "Visa".equals(readSource.getCreditCardType());
        assert "Big Bird Visa".equals(readSource.getProfile());
        assert readSource.getPerson() != null && 200L == readSource.getPerson().getId();
        assert readSource.getAddress() == null;
        assert readSource.getPhone() != null && 100L == readSource.getPhone().getId();
    }

    @Test(groups = { "testReadPaymentSource" })
    public void testReadPaymentSourceById() throws Exception {
        PaymentSource source = paymentSourceDao.readPaymentSourceById(0L);
        assert source == null;

        source = paymentSourceDao.readPaymentSourceById(100L);
        assert source != null;
        assert 100L == source.getId();
        assert "000001".equals(source.getAchAccountNumber());
        assert "Joe Blow".equals(source.getAchHolderName());
        assert "1234".equals(source.getAchRoutingNumber());
        assert source.isInactive();
        assert "Joe ACH".equals(source.getProfile());
        assert PaymentSource.ACH.equals(source.getPaymentType());
        assert source.getCreditCardExpiration() == null;
        assert source.getCreditCardNumber() == null;
        assert source.getCreditCardType() == null;
        assert source.getAddress() != null && 100L == source.getAddress().getId();
        assert "3726 THIRD ST".equals(source.getAddress().getAddressLine1());
        assert source.getPhone() != null && 100L == source.getPhone().getId();
        assert "214-443-6829".equals(source.getPhone().getNumber());
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
        assert "3111".equals(source.getCreditCardNumber());
        assert "Halle Berry".equals(source.getCreditCardHolderName());
        assert source.isInactive() == false;
        assert "Halle Discover".equals(source.getProfile());
        assert PaymentSource.CREDIT_CARD.equals(source.getPaymentType());
        assert source.getCreditCardExpiration() != null;
        assert "Discover".equals(source.getCreditCardType());
        assert source.getAddress() == null;
        assert source.getPhone() == null;
        assert source.getPerson() != null && 300L == source.getPerson().getId();
        assert "Doody".equals(source.getPerson().getLastName());
        assert "Howdy".equals(source.getPerson().getFirstName());
        
        assert source.getAchAccountNumber() == null;
        assert source.getAchRoutingNumber() == null;
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
                    assert src.getCreditCardExpiration() != null;
                    assert "Billy Graham".equals(src.getCreditCardHolderName());
                    assert "4222".equals(src.getCreditCardNumber());
                    assert "Billy Graham Visa".equals(src.getProfile());
                    assert src.getAchAccountNumber() == null;
                    assert src.getAchRoutingNumber() == null;
                    assert PaymentSource.CREDIT_CARD.equals(src.getPaymentType());
                    break;
                case 300:
                    assert src.getCreditCardExpiration() == null;
                    assert src.getCreditCardNumber() == null;
                    assert src.getProfile() == null;
                    assert src.getAchAccountNumber() == null;
                    assert src.getAchRoutingNumber() == null;
                    assert PaymentSource.CASH.equals(src.getPaymentType());
                    break;
                case 500:
                    assert src.getCreditCardExpiration() == null;
                    assert src.getCreditCardNumber() == null;
                    assert "Ruth ACH".equals(src.getProfile());
                    assert "999999".equals(src.getAchAccountNumber());
                    assert "1234".equals(src.getAchRoutingNumber());
                    assert PaymentSource.ACH.equals(src.getPaymentType());
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
                    assert src.getCreditCardExpiration() != null;
                    assert "Billy Graham".equals(src.getCreditCardHolderName());
                    assert "4222".equals(src.getCreditCardNumber());
                    assert "Billy Graham Visa".equals(src.getProfile());
                    assert src.getAchAccountNumber() == null;
                    assert src.getAchRoutingNumber() == null;
                    assert PaymentSource.CREDIT_CARD.equals(src.getPaymentType());
                    break;
                case 500:
                    assert src.getCreditCardExpiration() == null;
                    assert src.getCreditCardNumber() == null;
                    assert "Ruth ACH".equals(src.getProfile());
                    assert "999999".equals(src.getAchAccountNumber());
                    assert "1234".equals(src.getAchRoutingNumber());
                    assert PaymentSource.ACH.equals(src.getPaymentType());
                    break;
                default:
                    assert false == true;
            }
        }        
    }
}
