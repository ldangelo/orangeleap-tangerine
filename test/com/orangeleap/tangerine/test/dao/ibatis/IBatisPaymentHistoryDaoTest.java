package com.orangeleap.tangerine.test.dao.ibatis;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.dao.PaymentHistoryDao;
import com.orangeleap.tangerine.domain.PaymentHistory;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.type.PaymentHistoryType;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

public class IBatisPaymentHistoryDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private PaymentHistoryDao paymentHistoryDao;

    @BeforeMethod
    public void setupMocks() {
    	paymentHistoryDao = (PaymentHistoryDao)super.applicationContext.getBean("paymentHistoryDAO");
    }
    
    private final static Long CONSTITUENT_ID = new Long(100);
    private final static Long GIFT_ID = new Long(100);
    
    @Test(groups = { "testCreatePaymentHistoryEntry" })
    public void testCreatePaymentHistoryEntry() throws Exception {
    	PaymentHistory paymentHistory = new PaymentHistory();
    	paymentHistory.setPaymentHistoryType(PaymentHistoryType.GIFT);
    	paymentHistory.setCurrencyCode(StringConstants.USD);
    	paymentHistory.setAmount(new BigDecimal(100l));
    	paymentHistory.setPaymentType(StringConstants.EMPTY);
    	paymentHistory.setTransactionDate(new java.util.Date());
    	
    	Constituent constituent = new Constituent();
    	constituent.setId(CONSTITUENT_ID);
    	paymentHistory.setConstituent(constituent);
    	
    	paymentHistory.setGiftId(GIFT_ID);
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
        
        assert history.getConstituent() != null;
        Constituent constituent = history.getConstituent();
        IBatisConstituentDaoTest.testConstituentId100(constituent);
        
        assert history.getGiftId() != null;
        assert 100L == history.getGiftId();
    }

    @Test(groups = { "testReadPaymentHistoryEntries" }, dependsOnGroups = { "testCreatePaymentHistoryEntry" })
    public void testReadPaymentHistoryByConstituentId() throws Exception {
    	SortInfo sortinfo = new SortInfo();
    	sortinfo.setSort("phis.TRANSACTION_DATE");
        PaginatedResult pr = paymentHistoryDao.readPaymentHistoryByConstituentId(0L, sortinfo);
        assert pr.getRowCount() == 0;
        
    	pr = paymentHistoryDao.readPaymentHistoryByConstituentId(new Long(CONSTITUENT_ID), sortinfo);
        assert pr.getRowCount() > 0;
        PaymentHistory history = (PaymentHistory)pr.getRows().get(0);
        testCreatedHistory(history);
    } 

    @Test(groups = { "testReadPaymentHistoryEntries" }, dependsOnGroups = { "testCreatePaymentHistoryEntry" })
    public void testReadPaymentHistoryBySite() throws Exception {
    	SortInfo sortinfo = new SortInfo();
    	sortinfo.setSort("phis.TRANSACTION_DATE");
    	PaginatedResult pr = paymentHistoryDao.readPaymentHistoryBySite(sortinfo);
        assert pr.getRowCount() > 0;     
        for (Object o : pr.getRows()) {
        	PaymentHistory history = (PaymentHistory)o;
            switch (history.getId().intValue()) {
                case 100:
                    assert history.getAmount().intValue() == 0;
                    assert StringConstants.USD.equals(history.getCurrencyCode());
                    assert "Paid in full".equals(history.getDescription());
                    assert PaymentHistoryType.GIFT.equals(history.getPaymentHistoryType());
                    assert "Cash".equals(history.getPaymentType());
                    assert history.getTransactionDate() != null;
                    assert "123456789".equals(history.getTransactionId());
                    assert history.getGiftId() == 600L;
                    assert history.getConstituent() != null && history.getConstituent().getId() == 300L;
                    IBatisConstituentDaoTest.testConstituentId300(history.getConstituent());
                    break;
                default:
                    testCreatedHistory(history);
                    break;
            }
        }
    }
}
