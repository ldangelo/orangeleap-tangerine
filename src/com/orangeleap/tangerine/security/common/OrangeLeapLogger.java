/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mpower.security.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OrangeLeapLogger implements Log, java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Log logger;


    public static Log getLog(Class clazz) {
        return new OrangeLeapLogger(clazz);
    }

    private OrangeLeapLogger(Class clazz) {
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
        logger.trace(o);
    }

    @Override
    public void trace(Object o, Throwable throwable) {
        logger.trace(o, throwable);
    }

    @Override
    public void debug(Object o) {
        logger.debug(o);
    }

    @Override
    public void debug(Object o, Throwable throwable) {
        logger.debug(o, throwable);
    }

    @Override
    public void info(Object o) {
        logger.info(o);
    }

    @Override
    public void info(Object o, Throwable throwable) {
        logger.info(o, throwable);
    }

    @Override
    public void warn(Object o) {
        logger.warn(o);
    }

    @Override
    public void warn(Object o, Throwable throwable) {
        logger.warn(o, throwable);
    }

    @Override
    public void error(Object o) {
        logger.error(o);
    }

    @Override
    public void error(Object o, Throwable throwable) {
        logger.error(o, throwable);
    }

    @Override
    public void fatal(Object o) {
        logger.fatal(o);
    }

    @Override
    public void fatal(Object o, Throwable throwable) {
        logger.fatal(o, throwable);
    }


}
