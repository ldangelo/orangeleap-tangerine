package com.orangeleap.tangerine.test.dao.ibatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.dao.CommunicationHistoryDao;
import com.orangeleap.tangerine.domain.CommunicationHistory;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.type.CommunicationHistoryType;

public class IBatisCommunicationHistoryDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private CommunicationHistoryDao communicationHistoryDao;

    @BeforeMethod
    public void setup() {
    	communicationHistoryDao = (CommunicationHistoryDao)super.applicationContext.getBean("communicationHistoryDAO");
    }
    
    private final static Long PERSON_ID = new Long(100);
    private final static String COMMENTS = "Comments added here...";

    @Test(groups = { "testReadCommunicationHistory" })
    public void testReadCommunicationHistoryById() throws Exception {
        CommunicationHistory history = communicationHistoryDao.readCommunicationHistoryById(0L);
        assert history == null;
        
        history = communicationHistoryDao.readCommunicationHistoryById(200L);
        assert history != null;
        IBatisConstituentDaoTest.testConstituentId300(history.getPerson());
        assert CommunicationHistoryType.MANUAL.equals(history.getCommunicationHistoryType());
        assert history.getCreateDate() != null;
        assert history.getRecordDate() != null;
        assert history.isSystemGenerated();
        assert history.getUpdateDate() != null;
        assert history.getComments() == null;
        assert history.getGiftId() == 600L;
        assert null == history.getRecurringGiftId();
        assert history.getPledgeId() == null;
        assert history.getSelectedAddress() != null && history.getSelectedAddress().getId() == 100L;
        assert history.getSelectedPhone() != null && history.getSelectedPhone().getId() == 100L;
        assert history.getSelectedEmail() != null && history.getSelectedEmail().getId() == 100L;
    }
    
    @Test(groups = { "testMaintainCommunicationHistory" }, dependsOnGroups = { "testReadCommunicationHistory" })
    public void testMaintainCommunicationHistory() throws Exception {
        // Insert
    	CommunicationHistory history = new CommunicationHistory();
    	Site site = new Site("company1");
    	Person constituent = new Person();
    	constituent.setSite(site);
    	constituent.setId(PERSON_ID);
    	history.setPerson(constituent);

    	history.setCommunicationHistoryType(CommunicationHistoryType.GIFT_RECEIPT);
    	history.setComments(COMMENTS);
    	history.setGiftId(600L);
    	communicationHistoryDao.maintainCommunicationHistory(history);
    	assert history.getId() > 0;
    	
    	CommunicationHistory readHistory = communicationHistoryDao.readCommunicationHistoryById(history.getId());
    	assert readHistory != null;
    	assert history.getId().equals(readHistory.getId());
    	assert COMMENTS.equals(readHistory.getComments());
    	assert CommunicationHistoryType.GIFT_RECEIPT.equals(readHistory.getCommunicationHistoryType());
    	assert readHistory.getGiftId() == 600L;
    	assert readHistory.getPledgeId() == null;
        assert readHistory.getRecurringGiftId() == null;
    	IBatisConstituentDaoTest.testConstituentId100(readHistory.getPerson());
    	assert readHistory.getCreateDate() != null;
    	assert readHistory.getUpdateDate() != null;
    	assert readHistory.getRecordDate() == null;
    	assert readHistory.isSystemGenerated() == false;
    	
    	// Update
    	history.setCommunicationHistoryType(CommunicationHistoryType.MANUAL);
    	history.setComments(null);
    	history.setGiftId(null);
    	history.setPledgeId(200L);
    	history.setSystemGenerated(true);
        communicationHistoryDao.maintainCommunicationHistory(history);
        
        readHistory = communicationHistoryDao.readCommunicationHistoryById(history.getId());
        assert readHistory != null;
        assert history.getId().equals(readHistory.getId());
        assert CommunicationHistoryType.MANUAL.equals(readHistory.getCommunicationHistoryType());
        assert readHistory.getPledgeId() == 200L;
        assert readHistory.getRecurringGiftId() == null;
        IBatisConstituentDaoTest.testConstituentId100(readHistory.getPerson());
        assert readHistory.isSystemGenerated() == true;
        assert readHistory.getComments() == null;
        assert readHistory.getGiftId() == null;
        assert readHistory.getCreateDate() != null;
        assert readHistory.getUpdateDate() != null;
        assert readHistory.getRecordDate() == null;
    } 
}
