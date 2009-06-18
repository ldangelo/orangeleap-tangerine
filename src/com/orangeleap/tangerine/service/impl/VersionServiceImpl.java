package com.orangeleap.tangerine.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.stereotype.Service;

import com.orangeleap.tangerine.dao.VersionDao;
import com.orangeleap.tangerine.service.VersionService;

@Service("versionService")
public class VersionServiceImpl extends AbstractTangerineService implements VersionService {

	// Increment this value when the tangerine.ddl has an incompatible change for a release.
	public final static int ORANGE_SCHEMA_MAJOR_VERSION = 1;

	
	public final static String ORANGE_ID = "ORANGE";
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "versionDAO")
    private VersionDao versionDao;

    @Override
    public void checkOrangeLeapSchemaCompatible() {
    	
    	String message = "";
    	try {
    		int majorVersion = versionDao.selectVersion(ORANGE_ID).getMajorVersion();
    	    if (majorVersion == ORANGE_SCHEMA_MAJOR_VERSION) {
    	    	logger.info("Schema version successfully checked.");
    	    	return;
    	    }
    	    message = "Invalid database version "+majorVersion+" does not match program schema version "+ORANGE_SCHEMA_MAJOR_VERSION;
    	} catch (Exception e) {
    		e.printStackTrace();
    		message = "Unable to determine database schema version.";
    	}
    	
    	logger.fatal(message);
		throw new RuntimeException(message);
    	
    }
    
}
