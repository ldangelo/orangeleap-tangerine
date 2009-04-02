package com.orangeleap.tangerine.json.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    protected final Log logger = LogFactory.getLog(getClass());

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
