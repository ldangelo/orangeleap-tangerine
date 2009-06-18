package com.orangeleap.tangerine.test.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.dao.AuditDao;
import com.orangeleap.tangerine.domain.Audit;
import com.orangeleap.tangerine.type.AuditType;

public class IBatisAuditDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());
    
    private AuditDao auditDao;

    @BeforeMethod
    public void setupMocks() {
    	auditDao = (AuditDao)super.applicationContext.getBean("auditDAO");
    }
    
    private static final String TEST_USER = "testuser";
    
    @Test(groups = { "createAuditEntry" })
    public void testListCodeTypes() throws Exception {
    	Audit audit = new Audit();
    	audit.setAuditType(AuditType.CREATE);
    	audit.setObjectId(new Long(1));
    	audit.setDate(new java.util.Date());
    	audit.setEntityType("gift");
    	audit.setUser(TEST_USER);
    	audit.setDescription("test audit");
    	audit = auditDao.auditObject(audit);
    	assert audit.getSiteName().equals("company1");
    } 

    @Test(groups = { "testReadCodeType" }, dependsOnGroups = { "createAuditEntry" })
    public void testReadCodeType() throws Exception {
    	List<Audit> list = auditDao.allAuditHistoryForSite();
        assert list != null && list.size() > 0 && list.get(0).getUser().equals(TEST_USER);
    } 
    
    
}
