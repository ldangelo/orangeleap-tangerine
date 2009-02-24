package com.mpower.test.dao.ibatis;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mpower.dao.interfaces.MessageDao;
import com.mpower.type.MessageResourceType;

public class IBatisMessageResourceDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private MessageDao messageDao;

    @BeforeMethod
    public void setupMocks() {
    	messageDao = (MessageDao)super.applicationContext.getBean("messageDAO");
    }
    
    @Test(groups = { "testReadMessage" })
    public void testReadMessage() throws Exception {
    	String message = messageDao.readMessage(MessageResourceType.FIELD_VALIDATION, "fieldRequiredFailure.gift.amount", new Locale("en_US"));
    	assert message != null && message.contains("COMPANY1");  // get overridden message for company1
    } 

    
    
}
