// Can be used for running as JUnit tests
// Add config and test.properties folders to Junit classpath.
package com.orangeleap.tangerine.test;

import org.apache.commons.logging.Log;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import com.orangeleap.tangerine.util.OLLogger;

public class SwitchTest extends AbstractTransactionalDataSourceSpringContextTests {

	/** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

	@Override
 	protected String[] getConfigLocations() {
		return new String[]{"applicationContext.xml", "test-applicationContext.xml"};
 	}
	
}
