package com.orangeleap.tangerine.util;

import com.orangeleap.tangerine.service.ErrorLogService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Resource;

public class TableLoggerImpl implements TableLogger, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        OLLogger.setTableLogger(this);  // Once the app context is set up it should be safe to start logging to db.
    }

    @Resource(name = "errorLogService")
    private ErrorLogService errorLogService;
    
    public void logToTable(String loglevel, Object o, Throwable t) {
        errorLogService.addErrorMessage(loglevel+": " + o + (t == null ? "" : ": " + t.getMessage()), "");
    }

}
