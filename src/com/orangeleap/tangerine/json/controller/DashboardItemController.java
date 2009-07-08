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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.orangeleap.tangerine.domain.customization.DashboardData;
import com.orangeleap.tangerine.domain.customization.DashboardItem;
import com.orangeleap.tangerine.service.customization.DashboardService;

/**
 * This controller handles JSON requests for populating the dashboard
 * @version 1.0
 */
@Controller
public class DashboardItemController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name="dashboardService")
    private DashboardService dashboardService;

    @SuppressWarnings("unchecked")
    @RequestMapping("/dashboardItemsData.json")
    public ModelMap getDashboardItemsData(HttpServletRequest request) {
    	
    	List<DashboardItem> items = dashboardService.getDashboard();
    	List<DashboardData> itemsData = new ArrayList<DashboardData>();
    	for (DashboardItem item : items) {
    		try {
    			DashboardData data = dashboardService.getDashboardQueryContent(item);
    			itemsData.add(data);
    		} catch (Exception e) {
    			logger.error("Error rendering dashboard item "+item.getTitle()+": "+e.getMessage());
    		}
    	}
    	
    	if (logger.isDebugEnabled()) {
    		debugPrint(itemsData);
    	}
    	
        ModelMap map = new ModelMap();
        map.put("itemsData", itemsData);
        return map;
    }
    
    private void debugPrint(List<DashboardData> itemsData) {
    	for (DashboardData data : itemsData) {
    		logger.debug(data);
    	}
    }

}
