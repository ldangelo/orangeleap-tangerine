package com.mpower.test.dao.ibatis;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mpower.dao.interfaces.ConstituentDao;
import com.mpower.dao.interfaces.PaymentHistoryDao;
import com.mpower.domain.model.PaymentHistory;
import com.mpower.domain.model.Person;
import com.mpower.type.PaymentHistoryType;

public class IBatisPaymentHistoryDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private PaymentHistoryDao paymentHistoryDao;
    private ConstituentDao constituentDao;

    @BeforeMethod
    public void setupMocks() {
    	paymentHistoryDao = (PaymentHistoryDao)super.applicationContext.getBean("paymentHistoryDAO");
    	constituentDao = (ConstituentDao)super.applicationContext.getBean("constituentDAO");
    }
    
    private final static Long PERSON_ID = new Long(100);
    
    @Test(groups = { "testCreatePaymentHistoryEntry" })
    public void testCreatePaymentHistoryEntry() throws Exception {
    	PaymentHistory paymentHistory = new PaymentHistory();
    	paymentHistory.setPaymentHistoryType(PaymentHistoryType.GIFT);
    	paymentHistory.setCurrencyCode("USD");
    	paymentHistory.setAmount(new BigDecimal(100l));
    	paymentHistory.setPaymentType("");
    	paymentHistory.setTransactionDate(new java.util.Date());
    	Person person = constituentDao.readConstituentById(new Long(PERSON_ID));
    	paymentHistory.setPerson(person);
    	paymentHistoryDao.addPaymentHistory(paymentHistory);
    } 

    @Test(groups = { "testReadPaymentHistoryEntries" }, dependsOnGroups = { "testCreatePaymentHistoryEntry" })
    public void testReadPaymentHistoryEntries() throws Exception {
    	List<PaymentHistory> list = paymentHistoryDao.readPaymentHistory(new Long(PERSON_ID));
        assert list != null && list.size() > 0 && list.get(0).getPerson().getId().equals(new Long(PERSON_ID));
    } 
    
    
}
