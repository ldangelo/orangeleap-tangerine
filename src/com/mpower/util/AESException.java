package com.mpower.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AESException extends RuntimeException {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 1L;

    public AESException(String msg) {
        super(msg);
    }

    public AESException(String msg, Throwable root) {
        super(msg, root);
    }

}
