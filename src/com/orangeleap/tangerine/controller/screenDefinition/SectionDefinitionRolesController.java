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

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.orangeleap.tangerine.dao.CacheGroupDao;
import com.orangeleap.tangerine.dao.SectionDao;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.service.customization.PageCustomizationService;
import com.orangeleap.tangerine.type.CacheGroupType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;

public class SectionDefinitionRolesController extends SimpleFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "pageCustomizationService")
    private PageCustomizationService pageCustomizationService;
    
    @Resource(name = "sectionDAO")
    private SectionDao sectionDao;
    
    @Resource(name = "cacheGroupDAO")
    private CacheGroupDao cacheGroupDao;

    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;


  
    
    @SuppressWarnings("unchecked")
	protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {
    	if (!PageTypeManageController.accessAllowed(request)) return null;
        String id = request.getParameter("id"); 
        return getModelAndView(new Long(id));
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
    	return "";
    }
    
	private ModelAndView getModelAndView(Long id) {
    	
        SectionDefinition sectionDefinition = sectionDao.readSectionDefinition(id);
        
        ModelAndView mav = new ModelAndView(getSuccessView());
        mav.addObject("availableRoleList", getAvailableRoleList());
        mav.addObject("roles", getRoleList(sectionDefinition));
        mav.addObject("id", id);
        
        return mav;
    }
    
	private String getRoleList(SectionDefinition sectionDefinition) {
        String [] aroles = (sectionDefinition.getRole() + "").split(",");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < aroles.length; i++) {
        	if (i > 0) sb.append(", ");
        	sb.append(aroles[i].trim());
        }
        return sb.toString();
    }
    
    private List<String> getAvailableRoleList() {
    	return tangerineUserHelper.lookupUserRoles();
    }
    
 
    
    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
    	
        if (!PageTypeManageController.accessAllowed(request)) return null;
        
        String id = request.getParameter("id"); 
        String roles = request.getParameter("roles"); 
        
        SectionDefinition sectionDefinition = sectionDao.readSectionDefinition(new Long(id));
        sectionDefinition.setRole(formatRoles(roles));
        
        sectionDefinition = pageCustomizationService.maintainSectionDefinition(sectionDefinition);

        cacheGroupDao.updateCacheGroupTimestamp(CacheGroupType.PAGE_CUSTOMIZATION);

        return getModelAndView(sectionDefinition.getId());

    }
    
    private String formatRoles(String roles) {
    	StringBuilder sb = new StringBuilder();
    	String sa[] = roles.split(",");
    	for (int i = 0; i < sa.length ;i++) {
    		String r = formatRole(sa[i]);
    		if (r.length() > 0) {
	        	if (i > 0) sb.append(",");
	        	sb.append(r);
    		}
    	}
    	return sb.toString();
    }
    
    private String formatRole(String role) {
    	role = role.toUpperCase().trim();
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < role.length(); i++) {
    		char c = role.charAt(i);
    		if (Character.isUpperCase(c) || c == '_') sb.append(c);
    	}
    	return sb.toString();
    }
    
}
