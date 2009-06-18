package com.orangeleap.tangerine.test;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@ContextConfiguration(locations = { "classpath:/test-applicationContext.xml" })
public abstract class BaseTest extends AbstractTestNGSpringContextTests {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

}
