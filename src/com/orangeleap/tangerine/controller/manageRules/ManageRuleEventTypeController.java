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

package com.orangeleap.tangerine.controller.manageRules;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.orangeleap.tangerine.service.customization.PageCustomizationService;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.util.OLLogger;

public class ManageRuleEventTypeController extends SimpleFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "pageCustomizationService")
    private PageCustomizationService pageCustomizationService;
  

    
	@SuppressWarnings("unchecked")
	public static boolean accessAllowed(HttpServletRequest request) {
		Map<String, AccessType> pageAccess = (Map<String, AccessType>)WebUtils.getSessionAttribute(request, "pageAccess");
		return pageAccess.get("/screenDefinition.htm") == AccessType.ALLOWED;
	}


    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
    	return "";
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {

        if (!accessAllowed(request)) return null;

        ModelAndView mav = super.showForm(request, response, errors, controlModel);
        mav.addObject("pageTypes", getSelectionList());
        return mav;
    }


    private Map<String, String> getSelectionList() {
        Map<String, String> map = new TreeMap<String, String>();
    	List<String> list = pageCustomizationService.readDistintSectionDefinitionsPageTypes();
        for (String s : list) {
        	map.put(getDescription(s), s);
        }
        return map;
    }
    
    private String getDescription(String s) {
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < s.length() ;i++) {
    		char c = s.charAt(i);
    		if (i == 0) c = Character.toUpperCase(c);
    		if (Character.isUpperCase(c)) {
    			sb.append(" ");
    			c = Character.toUpperCase(c);
    		}
    		sb.append(c);
    	}
		return sb.toString();
    }
    
}
