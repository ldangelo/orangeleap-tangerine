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

package com.orangeleap.tangerine.json.controller.admin.dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import com.orangeleap.tangerine.domain.customization.DashboardItem;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.service.customization.DashboardService;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineUserHelper;

@Controller
@RequestMapping("/manageDashboard.json")
public class ManageDashboardController {

    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "siteService")
    private SiteService siteService;

    @Resource(name = "dashboardService")
    private DashboardService dashboardService;

    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;

    // TODO: move to a filter or something and throw an UnauthorizedAccessException
    @SuppressWarnings("unchecked")
    private void checkAccess(HttpServletRequest request) {
        Map<String, AccessType> pageAccess = (Map<String, AccessType>) WebUtils.getSessionAttribute(request, "pageAccess");
        if (pageAccess.get("/siteSettings.htm") != AccessType.ALLOWED) {
            throw new RuntimeException("You are not authorized to access this page");
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ModelMap getDashboardItems(HttpServletRequest request) throws Exception {
        checkAccess(request);
        ModelMap modelMap = new ModelMap();
        
        // Gets either site-customized dashboard or default (legacy) one.
        List<DashboardItem> items = dashboardService.getAllDashboardItems(); 
        // Cannot edit default items - this editor can be used to point to guru reports only - don't allow custom SQL report creation in tangerine.
        
        // If there are no dashboard items set up, copy from defaults.
    	Iterator<DashboardItem> it = items.iterator();
    	while (it.hasNext()) {
    		DashboardItem item = it.next();
    		if (isAllowedType(item.getType())) {
    			if (item.getSiteName() == null) {
	    			item.setId(0L);
	    			item.setSiteName(tangerineUserHelper.lookupUserSiteName());
    			}
    		} else {
    			it.remove();
    		}
    	}
        
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        addItems(items, returnList);
        modelMap.put(StringConstants.ROWS, returnList);
        modelMap.put(StringConstants.TOTAL_ROWS, returnList.size());
        return modelMap;
    }
    	
    private boolean isAllowedType(String type) {
    	return "Rss".equals(type) || "Guru".equals(type);
    }

    private void addItems(List<DashboardItem> items, List<Map<String, Object>> returnList) {
    	int i = 0;
        for (DashboardItem item : items) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", i++);
            map.put("itemid", item.getId());
            map.put("type", item.getType());
            map.put("title", item.getTitle());
            map.put("url", item.getUrl());
            map.put("order", item.getOrder());
            returnList.add(map);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ModelMap saveDashboard(HttpServletRequest request, String rows) throws Exception {
        checkAccess(request);
        
        List<DashboardItem> items = dashboardService.getAllDashboardItems();

        JSONArray jsonArray = JSONArray.fromObject(rows);
        List<DynaBean> beans = (List<DynaBean>) JSONSerializer.toJava(jsonArray);
        Iterator<DynaBean> beanIterator = beans.iterator();
        Iterator<DashboardItem> itemIterator = items.iterator();
        while (itemIterator.hasNext()) {
        	DashboardItem item = itemIterator.next();
            boolean found = false;
            while (beanIterator.hasNext()) {
                DynaBean bean = beanIterator.next();
                if (bean.getDynaClass().getDynaProperty("itemid") != null &&
                        bean.get("itemid") != null && 
                        item.getId().equals(bean.get("itemid"))) {
                	
                    if (bean.getDynaClass().getDynaProperty("type") != null && bean.get("type") != null) {
                        item.setType((String) bean.get("type"));
                    }
                    
                    if (bean.getDynaClass().getDynaProperty("title") != null && bean.get("title") != null) {
                        item.setTitle((String) bean.get("title"));
                    }
                    
                    if (bean.getDynaClass().getDynaProperty("url") != null && bean.get("url") != null) {
                        item.setUrl((String) bean.get("url"));
                    }

                    if (bean.getDynaClass().getDynaProperty("order") != null && bean.get("order") != null) {
                        item.setOrder( new Integer( (String)bean.get("order")));
                    }

                    found = true;
                    beanIterator.remove();
                }
            }
            if (found) {
            	dashboardService.maintainDashboardItem(item);
            } else {
            	dashboardService.deleteDashboardItemById(item.getId());
            	itemIterator.remove();
            }
            
        }

        // The following are new options
        beanIterator = beans.iterator();
        while (beanIterator.hasNext()) {
            DynaBean bean = beanIterator.next();
            if (bean.getDynaClass().getDynaProperty("itemid") != null &&
                    bean.get("itemid") != null) {
            	
            	DashboardItem item = new DashboardItem();

                if (bean.getDynaClass().getDynaProperty("type") != null && bean.get("type") != null) {
                    item.setType((String) bean.get("type"));
                } 
                
                if (bean.getDynaClass().getDynaProperty("title") != null && bean.get("title") != null) {
                    item.setTitle((String) bean.get("title"));
                }
                
                if (bean.getDynaClass().getDynaProperty("url") != null && bean.get("url") != null) {
                    item.setUrl((String) bean.get("url"));
                }

                if (bean.getDynaClass().getDynaProperty("order") != null && bean.get("order") != null) {
                    item.setOrder( new Integer( (String)bean.get("order")));
                }

            	dashboardService.maintainDashboardItem(item);
                items.add(item);
            }
        }

        ModelMap map = new ModelMap();

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        addItems(items, returnList);
        map.put(StringConstants.ROWS, returnList);
        map.put(StringConstants.TOTAL_ROWS, returnList.size());
        map.put(StringConstants.SUCCESS, Boolean.TRUE.toString().toLowerCase());
        return map;
    }
}