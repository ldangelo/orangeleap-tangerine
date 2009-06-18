package com.orangeleap.tangerine.service.exception;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;

public class ConstituentValidationException extends ValidationException {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

	
    private static final long serialVersionUID = 1L;

    public static final String SITE_REQUIRED = "MSG_SITE_REQUIRED";
    
    public ConstituentValidationException() {
    	
    }
    
    public ConstituentValidationException(String message) {
    	super(message);
    }
}

