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

import java.util.ArrayList;
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

import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.service.customization.PageCustomizationService;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.OLLogger;

public class SectionDefinitionFormController extends SimpleFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "pageCustomizationService")
    private PageCustomizationService pageCustomizationService;
  
    
    @SuppressWarnings("unchecked")
	protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {
    	if (!PageTypeManageController.accessAllowed(request)) return null;
        return getModelAndView(request);
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
    	return "";
    }
    
    private ModelAndView getModelAndView(HttpServletRequest request) {
    	
        String sectionName = request.getParameter("sectionName"); 
        String pageType = request.getParameter("pageType"); 
        String role = request.getParameter("role"); 
        
        ModelAndView mav = new ModelAndView(getSuccessView());
        
        List<SectionField> sectionFields = getSectionFields(sectionName, pageType, role);
        List<SectionFieldView> fieldList = new ArrayList<SectionFieldView>();
        for (SectionField sf: sectionFields) fieldList.add(new SectionFieldView(sf));
        
        mav.addObject("fieldList", fieldList);
        mav.addObject("sectionName", sectionName);
        mav.addObject("pageType", pageType);
        mav.addObject("role", role);
        
        return mav;
    }
    
    public static final class SectionFieldView {
    	
		private SectionField sectionField;
    	private boolean visible;
    	private String name;

    	public SectionFieldView(SectionField sf) {
    		setSectionField(sf);
    		setVisible(sf.getFieldOrder() != 0);
    		setName(sf.getFieldDefinition().getDefaultLabel());
    	}
    	
    	public void setSectionField(SectionField sectionField) {
			this.sectionField = sectionField;
		}
		public SectionField getSectionField() {
			return sectionField;
		}
		public void setVisible(boolean visible) {
			this.visible = visible;
		}
		public boolean isVisible() {
			return visible;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
    }
    
    private List<SectionField> getSectionFields(String sectionName, String pageType, String role) {
    	
        List<SectionField> sectionFields = new ArrayList<SectionField>();
        List<String> roles = new ArrayList<String>();
        roles.add(role);
        List<SectionDefinition> sectionDefinitions = pageCustomizationService.readSectionDefinitionsByPageTypeRoles(PageType.valueOf(pageType), roles);
        for (SectionDefinition sectionDef : sectionDefinitions) {
        	if (sectionDef.getSectionName().equals(sectionName)) {
                sectionFields = pageCustomizationService.readSectionFieldsBySection(sectionDef);
                break;
        	}
        }
        
        return sectionFields;

    }
    
    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
    	
        if (!PageTypeManageController.accessAllowed(request)) return null;

    	// TODO need to create to site-specific rows for any changed rows
    	// TODO need to clear cache

        return new ModelAndView(getSuccessView());

    }
}
