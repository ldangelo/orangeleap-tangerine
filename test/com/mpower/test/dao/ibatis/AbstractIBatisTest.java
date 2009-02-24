package com.mpower.test.dao.ibatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.mpower.util.TangerineUserHelper;

@ContextConfiguration(locations = { "classpath:/test-ibatis-application-context.xml" })
public abstract class AbstractIBatisTest extends AbstractTestNGSpringContextTests  {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    public static TangerineUserHelper createMockUserHelper() {
        final Mockery tangerineUserHelperMock = new Mockery();
        final TangerineUserHelper tangerineUserHelper = tangerineUserHelperMock.mock(TangerineUserHelper.class);

        tangerineUserHelperMock.checking(new Expectations() {{
            allowing (tangerineUserHelper).lookupUserSiteName(); will(returnValue("company1"));
            allowing (tangerineUserHelper).lookupUserPassword(); will(returnValue("password"));
        }});
        return tangerineUserHelper;
    }
}
