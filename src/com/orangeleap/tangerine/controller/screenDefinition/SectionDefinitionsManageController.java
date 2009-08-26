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

package com.orangeleap.tangerine.controller.screenDefinition;

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

import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.service.customization.PageCustomizationService;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.OLLogger;

public class SectionDefinitionsManageController extends SimpleFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "pageCustomizationService")
    private PageCustomizationService pageCustomizationService;


    @SuppressWarnings("unchecked")
	@Override
	protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {

        if (!PageTypeManageController.accessAllowed(request)) return null;
        
        String pageType = request.getParameter("pageType"); 
        

        ModelAndView mav = new ModelAndView(getSuccessView());
        mav.addObject("pageType", pageType);
        mav.addObject("sectionNames", getSelectionList(request));
        return mav;

    }
    
    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
    	return "";
    }
    
    private Map<String, String> getSelectionList(HttpServletRequest request) {
    	String pageType = request.getParameter("pageType");
        List<SectionDefinition> sectionDefinitions = pageCustomizationService.readSectionDefinitionsByPageType(PageType.valueOf(pageType));

        Map<String, String> map = new TreeMap<String, String>();
        for (SectionDefinition sectionDefinition : sectionDefinitions) {
        	map.put(sectionDefinition.getDefaultLabel() + " " + sectionDefinition.getRole(), ""+sectionDefinition.getId());
        }
        return map;
    }
    
}
