package com.orangeleap.tangerine.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.ErrorLogDao;
import com.orangeleap.tangerine.service.ErrorLogService;
import com.orangeleap.tangerine.util.TangerineUserHelper;

@Service("errorLogService")
@Transactional(propagation = Propagation.REQUIRED)
public class ErrorLogServiceImpl extends AbstractTangerineService implements ErrorLogService {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "errorLogDAO")
    private ErrorLogDao errorLogDao;
    
    @Resource(name="tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public void addErrorMessage(String message, String context) {
    	try {
        	Long constituentId = tangerineUserHelper.lookupUserId();
    		errorLogDao.addErrorMessage(message, context, constituentId);
    	} catch (Exception e) {
    		logger.error("Unable to log error message to ERROR_LOG: "+message, e);
    	}
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public void removeErrorMessagesOlderThanDays(int days) {
    	errorLogDao.removeErrorMessagesOlderThanDays(days);
    }
}
