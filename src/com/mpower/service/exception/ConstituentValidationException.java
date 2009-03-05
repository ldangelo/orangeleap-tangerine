package com.mpower.service.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConstituentValidationException extends ValidationException {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	
    private static final long serialVersionUID = 1L;

    public static final String SITE_REQUIRED = "MSG_SITE_REQUIRED";
}
