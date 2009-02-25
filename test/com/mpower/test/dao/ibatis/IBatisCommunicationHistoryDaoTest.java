package com.mpower.test.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mpower.dao.interfaces.CommunicationHistoryDao;
import com.mpower.dao.interfaces.ConstituentDao;
import com.mpower.domain.model.CommunicationHistory;
import com.mpower.domain.model.Person;
import com.mpower.type.CommunicationHistoryType;

public class IBatisCommunicationHistoryDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private CommunicationHistoryDao communicationHistoryDao;
    private ConstituentDao constituentDao;

    @BeforeMethod
    public void setupMocks() {
    	communicationHistoryDao = (CommunicationHistoryDao)super.applicationContext.getBean("communicationHistoryDAO");
    	constituentDao = (ConstituentDao)super.applicationContext.getBean("constituentDAO");
    }
    
    private final static Long PERSON_ID = new Long(100);
    private final static String COMMENTS = "Comments added here...";
    
    @Test(groups = { "testCreateCommunicationHistoryEntry" })
    public void testCreateCommunicationHistoryEntry() throws Exception {
    	CommunicationHistory communicationHistory = new CommunicationHistory();
    	Person person = constituentDao.readConstituentById(new Long(PERSON_ID));
    	communicationHistory.setPerson(person);

    	communicationHistory.setCommunicationHistoryType(CommunicationHistoryType.GIFT_RECEIPT);
    	communicationHistory.setComments(COMMENTS);
    	communicationHistoryDao.maintainCommunicationHistory(communicationHistory);
    } 

    @Test(groups = { "testReadCommunicationHistoryEntries" }, dependsOnGroups = { "testCreateCommunicationHistoryEntry" })
    public void testReadCommunicationHistoryEntries() throws Exception {
    	List<CommunicationHistory> list = communicationHistoryDao.readCommunicationHistoryByPerson(new Long(PERSON_ID));
        assert list != null && list.size() > 0;
        CommunicationHistory communicationHistory = list.get(0);
        assert communicationHistory.getPerson().getId().equals(new Long(PERSON_ID));
        assert communicationHistory.getComments().equals(COMMENTS); 
    }
    
}
