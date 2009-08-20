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
import java.util.Comparator;
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
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.service.customization.PageCustomizationService;
import com.orangeleap.tangerine.type.CacheGroupType;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.OLLogger;

import edu.emory.mathcs.backport.java.util.Collections;

public class SectionDefinitionFormController extends SimpleFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "pageCustomizationService")
    private PageCustomizationService pageCustomizationService;
    
    @Resource(name = "cacheGroupDAO")
    private CacheGroupDao cacheGroupDao;


  
    
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
        
        Collections.sort(sectionFields, new Comparator<SectionField>() {

			@Override
			public int compare(SectionField o1, SectionField o2) {
				int i = o1.getFieldOrder().compareTo(o2.getFieldOrder());
				if (i == 0) i = o1.getFieldDefinition().getDefaultLabel().compareTo(o2.getFieldDefinition().getDefaultLabel());
				return i;
			}
        	
        });
        
        return sectionFields;

    }
    
    private static final String MOVE_UP = "moveup";
    private static final String TOGGLE_VISIBLE = "togglevisible";
    
    
    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
    	
        if (!PageTypeManageController.accessAllowed(request)) return null;
        
        String sectionName = request.getParameter("sectionName"); 
        String pageType = request.getParameter("pageType"); 
        String role = request.getParameter("role"); 
        String fieldName = request.getParameter("fieldName"); 
        String action = request.getParameter("action"); 
        boolean toggleVisible = TOGGLE_VISIBLE.equals(action);
        boolean moveUp = MOVE_UP.equals(action);
        
        List<SectionField> sectionFields = getSectionFields(sectionName, pageType, role);

        // Locate target field
        SectionField targetSectionField = null;
        int index = -1;
        int maxvalue = -1;
        for (int i = 0; i < sectionFields.size(); i++) {
        	SectionField sf = sectionFields.get(i);
        	if (sf.getFieldDefinition().getDefaultLabel().equals(fieldName)) {
	        	targetSectionField = sf;
	        	index = i;
            }
        	if (maxvalue < sf.getFieldOrder()) maxvalue = sf.getFieldOrder();
        }
        
        if (toggleVisible) {
        	
        	boolean isVisible = targetSectionField.getFieldOrder().intValue() != 0;
        	
        	if (isVisible) {
        		targetSectionField.setFieldOrder(0);
        	} else {
        		targetSectionField.setFieldOrder(maxvalue + 100);
        	}
            pageCustomizationService.maintainSectionField(targetSectionField);
        	
        } else if (moveUp && index > 0) {
        	
        	SectionField higherSectionField = sectionFields.get(index - 1);
        	int hv = higherSectionField.getFieldOrder().intValue();
        	if (hv != 0) {
        		higherSectionField.setFieldOrder(targetSectionField.getFieldOrder());
        		targetSectionField.setFieldOrder(hv);
        	}
        	List<SectionField> fieldsToUpdate = new ArrayList<SectionField>();
        	fieldsToUpdate.add(higherSectionField);
        	fieldsToUpdate.add(targetSectionField);
            pageCustomizationService.maintainSectionFields(fieldsToUpdate);
        	
        }
        
        cacheGroupDao.updateCacheGroupTimestamp(CacheGroupType.PAGE_CUSTOMIZATION);

        return new ModelAndView(getSuccessView());

    }
}
