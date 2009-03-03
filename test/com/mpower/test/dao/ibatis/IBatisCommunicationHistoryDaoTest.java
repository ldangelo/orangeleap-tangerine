package com.mpower.test.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mpower.dao.interfaces.CommunicationHistoryDao;
import com.mpower.domain.model.CommunicationHistory;
import com.mpower.domain.model.Person;
import com.mpower.domain.model.Site;
import com.mpower.type.CommunicationHistoryType;

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
    public void testReadCommunicationHistoryByConstituentId() throws Exception {
        List<CommunicationHistory> list = communicationHistoryDao.readCommunicationHistoryByConstituentId(0L);
        assert list != null && list.isEmpty();
        
        list = communicationHistoryDao.readCommunicationHistoryByConstituentId(new Long(PERSON_ID));
        assert list != null && list.size() == 1;
        CommunicationHistory history = list.get(0);
        IBatisConstituentDaoTest.testConstituentId100(history.getPerson());
        assert CommunicationHistoryType.GIFT_RECEIPT.equals(history.getCommunicationHistoryType());
        assert history.getCreateDate() != null;
        assert history.getRecordDate() != null;
        assert history.isSystemGenerated() == false;
        assert history.getUpdateDate() != null;
        assert "hello there".equals(history.getComments());
        assert 100L == history.getCommitmentId();
        assert history.getGiftId() == null;
    }
    
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
        assert history.getCommitmentId() == null;
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
    	assert readHistory.getCommitmentId() == null;
    	IBatisConstituentDaoTest.testConstituentId100(readHistory.getPerson());
    	assert readHistory.getCreateDate() != null;
    	assert readHistory.getUpdateDate() != null;
    	assert readHistory.getRecordDate() == null;
    	assert readHistory.isSystemGenerated() == false;
    	
    	// Update
    	history.setCommunicationHistoryType(CommunicationHistoryType.MANUAL);
    	history.setComments(null);
    	history.setGiftId(null);
    	history.setCommitmentId(100L);
    	history.setSystemGenerated(true);
        communicationHistoryDao.maintainCommunicationHistory(history);
        
        readHistory = communicationHistoryDao.readCommunicationHistoryById(history.getId());
        assert readHistory != null;
        assert history.getId().equals(readHistory.getId());
        assert CommunicationHistoryType.MANUAL.equals(readHistory.getCommunicationHistoryType());
        assert readHistory.getCommitmentId() == 100L;
        IBatisConstituentDaoTest.testConstituentId100(readHistory.getPerson());
        assert readHistory.isSystemGenerated() == true;
        assert readHistory.getComments() == null;
        assert readHistory.getGiftId() == null;
        assert readHistory.getCreateDate() != null;
        assert readHistory.getUpdateDate() != null;
        assert readHistory.getRecordDate() == null;
    } 
}
