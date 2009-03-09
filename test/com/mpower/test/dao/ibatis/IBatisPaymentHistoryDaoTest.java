package com.mpower.test.dao.ibatis;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mpower.dao.interfaces.PaymentHistoryDao;
import com.mpower.domain.model.PaymentHistory;
import com.mpower.domain.model.Person;
import com.mpower.domain.model.paymentInfo.Gift;
import com.mpower.type.PaymentHistoryType;
import com.mpower.util.StringConstants;

public class IBatisPaymentHistoryDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private PaymentHistoryDao paymentHistoryDao;

    @BeforeMethod
    public void setupMocks() {
    	paymentHistoryDao = (PaymentHistoryDao)super.applicationContext.getBean("paymentHistoryDAO");
    }
    
    private final static Long PERSON_ID = new Long(100);
    private final static Long GIFT_ID = new Long(100);
    
    @Test(groups = { "testCreatePaymentHistoryEntry" })
    public void testCreatePaymentHistoryEntry() throws Exception {
    	PaymentHistory paymentHistory = new PaymentHistory();
    	paymentHistory.setPaymentHistoryType(PaymentHistoryType.GIFT);
    	paymentHistory.setCurrencyCode(StringConstants.USD);
    	paymentHistory.setAmount(new BigDecimal(100l));
    	paymentHistory.setPaymentType(StringConstants.EMPTY);
    	paymentHistory.setTransactionDate(new java.util.Date());
    	
    	Person constituent = new Person();
    	constituent.setId(PERSON_ID);
    	paymentHistory.setPerson(constituent);
    	
    	Gift gift = new Gift();
    	gift.setId(GIFT_ID);
    	paymentHistory.setGift(gift);
    	paymentHistory = paymentHistoryDao.addPaymentHistory(paymentHistory);
    	assert paymentHistory.getId() > 0;
    } 
    
    private void testCreatedHistory(PaymentHistory history) {
        assert history.getId() > 0;
        assert PaymentHistoryType.GIFT.equals(history.getPaymentHistoryType());
        assert StringConstants.USD.equals(history.getCurrencyCode());
        assert 100 == history.getAmount().intValue();
        assert StringConstants.EMPTY.equals(history.getPaymentType());
        assert history.getTransactionDate() != null;
        
        assert history.getPerson() != null;
        Person constituent = history.getPerson();
        IBatisConstituentDaoTest.testConstituentId100(constituent);
        
        assert history.getGift() != null;
        IBatisGiftDaoTest.testGiftId100(history.getGift());
        assert history.getGift().getDistributionLines() == null;        
    }

    @Test(groups = { "testReadPaymentHistoryEntries" }, dependsOnGroups = { "testCreatePaymentHistoryEntry" })
    public void testReadPaymentHistoryByConstituentId() throws Exception {
        List<PaymentHistory> list = paymentHistoryDao.readPaymentHistoryByConstituentId(0L);
        assert list != null && list.isEmpty();
        
    	list = paymentHistoryDao.readPaymentHistoryByConstituentId(new Long(PERSON_ID));
        assert list != null && list.size() > 0;
        PaymentHistory history = list.get(0);
        testCreatedHistory(history);
    } 

    @Test(groups = { "testReadPaymentHistoryEntries" }, dependsOnGroups = { "testCreatePaymentHistoryEntry" })
    public void testReadPaymentHistoryBySite() throws Exception {
        List<PaymentHistory> list = paymentHistoryDao.readPaymentHistoryBySite();
        assert list != null && list.size() == 2;     
        for (PaymentHistory history : list) {
            switch (history.getId().intValue()) {
                case 100:
                    assert history.getAmount().intValue() == 0;
                    assert StringConstants.USD.equals(history.getCurrencyCode());
                    assert "Paid in full".equals(history.getDescription());
                    assert PaymentHistoryType.GIFT.equals(history.getPaymentHistoryType());
                    assert "Cash".equals(history.getPaymentType());
                    assert history.getTransactionDate() != null;
                    assert "123456789".equals(history.getTransactionId());
                    assert history.getGift() != null && history.getGift().getId() == 600L;
                    assert history.getGift().getPerson() != null && history.getGift().getPerson().getId() == 300L;
                    assert history.getGift().getSelectedEmail() == null;
                    assert history.getGift().getSelectedAddress() == null;
                    assert history.getGift().getSelectedPhone() == null;
                    assert history.getGift().getSelectedPaymentSource() == null;
                    assert history.getPerson() != null && history.getPerson().getId() == 300L;
                    IBatisConstituentDaoTest.testConstituentId300(history.getPerson());
                    break;
                default:
                    testCreatedHistory(history);
                    break;
            }
        }
    }
}
