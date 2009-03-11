package com.mpower.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public abstract class BaseTest extends AbstractTestNGSpringContextTests {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

}
