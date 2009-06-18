package com.orangeleap.tangerine.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OLLogger implements Log {

    private static boolean enableTableLogging = ( null == System.getProperty("ol.disable.table.logging"));

    private static TableLogger tablelogger;

    private Log logger;


    public static Log getLog(Class clazz) {
        return new OLLogger(clazz);
    }

    public static void setTableLogger(TableLogger atablelogger) {
        tablelogger = atablelogger;
    }

    private OLLogger(Class clazz) {
        logger = LogFactory.getLog(clazz);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public boolean isFatalEnabled() {
        return logger.isFatalEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public void trace(Object o) {
        if (logger.isTraceEnabled()) logToTable("TRACE", o, null);
        logger.trace(o);
    }

    @Override
    public void trace(Object o, Throwable throwable) {
        if (logger.isTraceEnabled()) logToTable("TRACE", o, throwable);
        logger.trace(o, throwable);
    }

    @Override
    public void debug(Object o) {
        if (logger.isDebugEnabled()) logToTable("DEBUG", o, null);
        logger.debug(o);
    }

    @Override
    public void debug(Object o, Throwable throwable) {
        if (logger.isDebugEnabled()) logToTable("DEBUG", o, throwable);
        logger.debug(o, throwable);
    }

    @Override
    public void info(Object o) {
        if (logger.isInfoEnabled()) logToTable("INFO", o, null);
        logger.info(o);
    }

    @Override
    public void info(Object o, Throwable throwable) {
        if (logger.isInfoEnabled()) logToTable("INFO", o, throwable);
        logger.info(o, throwable);
    }

    @Override
    public void warn(Object o) {
        if (logger.isWarnEnabled()) logToTable("WARN", o, null);
        logger.warn(o);
    }

    @Override
    public void warn(Object o, Throwable throwable) {
        if (logger.isWarnEnabled()) logToTable("WARN", o, throwable);
        logger.warn(o, throwable);
    }

    @Override
    public void error(Object o) {
        if (logger.isErrorEnabled()) logToTable("ERROR", o, null);
        logger.error(o);
    }

    @Override
    public void error(Object o, Throwable throwable) {
        if (logger.isErrorEnabled()) logToTable("ERROR", o, throwable);
        logger.error(o, throwable);
    }

    @Override
    public void fatal(Object o) {
        if (logger.isFatalEnabled()) logToTable("FATAL", o, null);
        logger.fatal(o);
    }

    @Override
    public void fatal(Object o, Throwable throwable) {
        if (logger.isFatalEnabled()) logToTable("FATAL", o, throwable);
        logger.fatal(o, throwable);
    }

    private void logToTable(String loglevel, Object o, Throwable t) {
       if (tablelogger != null && enableTableLogging) tablelogger.logToTable(loglevel,  o,  t);
    }

}
