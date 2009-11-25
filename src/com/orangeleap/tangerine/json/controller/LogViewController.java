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

package com.orangeleap.tangerine.json.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import com.orangeleap.tangerine.domain.ErrorLog;
import com.orangeleap.tangerine.service.ErrorLogService;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.util.HttpUtil;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

/**
 * This controller handles JSON requests for populating
 * the grid of log events.
 * @version 1.0
 */
@Controller
public class LogViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    private final static int MAX_MESSAGE_LENGTH = 2000;

    @Resource(name="errorLogService")
    private ErrorLogService errorLogService;


    private final static Map<String, Object> NAME_MAP = new HashMap<String, Object>();

    static {
        NAME_MAP.put("id", "ERROR_LOG_ID");
        NAME_MAP.put("context", "CONTEXT");
        NAME_MAP.put("message", "MESSAGE");
        NAME_MAP.put("createdate", "CREATE_DATE");
    }

    @SuppressWarnings("unchecked")
    public static boolean accessAllowed(HttpServletRequest request) {
        Map<String, AccessType> pageAccess = (Map<String, AccessType>) WebUtils.getSessionAttribute(request, "pageAccess");
        return pageAccess.get("/logView.htm") == AccessType.ALLOWED;
    }



    @SuppressWarnings("unchecked")
    @RequestMapping("/logView.json")
    public ModelMap getLogEvents(HttpServletRequest request, SortInfo sortInfo) {

    	if (!accessAllowed(request)) {
			return null;
		}

        List<Map> rows = new ArrayList<Map>();

        // if we're not getting back a valid column name, possible SQL injection,
        // so send back an empty list.
        if(!sortInfo.validateSortField(NAME_MAP.keySet())) {
            logger.warn("getLogEvents called with invalid sort column: [" + sortInfo.getSort() + "]");
            return new ModelMap("rows", rows);
        }
                                                     
        // set the sort to the valid column name, based on the map
        sortInfo.setSort( (String) NAME_MAP.get(sortInfo.getSort()) );

        PaginatedResult result = errorLogService.readErrorMessages(sortInfo);
        

        List<ErrorLog> list = result.getRows();

        for(ErrorLog errorLog : list) {
            rows.add( logEventToMap(errorLog) );
        }

        ModelMap map = new ModelMap("rows", rows);
        map.put("totalRows", result.getRowCount());
        return map;
    }

    private Map<String,Object> logEventToMap(ErrorLog errorLog) {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // keys should map with the NAME_MAP constant
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", errorLog.getId());
        map.put("createdate", formatter.format(errorLog.getCreateDate()) );
        map.put("context", errorLog.getContext());
        String message = errorLog.getMessage();
        if (message.length() > MAX_MESSAGE_LENGTH) {
			message = message.substring(0,MAX_MESSAGE_LENGTH);
		}
        message = HttpUtil.jsEscape(message);
        map.put("message", message);

        return map;

    }
}