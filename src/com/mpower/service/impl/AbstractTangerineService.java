package com.mpower.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.util.TangerineUserHelper;

public abstract class AbstractTangerineService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Resource(name="tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;

    protected String getSiteName() {
        return tangerineUserHelper.lookupUserSiteName();
    }

    
    
}
