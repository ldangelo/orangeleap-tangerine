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

package com.orangeleap.tangerine.util;

import com.orangeleap.tangerine.service.ErrorLogService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Resource;

public class TableLoggerImpl implements TableLogger, ApplicationContextAware, java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        OLLogger.setTableLogger(this);  // Once the app context is set up it should be safe to start logging to db.
    }

    @Resource(name = "errorLogService")
    private ErrorLogService errorLogService;

    public void logToTable(String loglevel, Object o, Throwable t) {
        errorLogService.addErrorMessage(loglevel + ": " + o + (t == null ? "" : ": " + t.getMessage()), "");
    }

}
