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
import com.orangeleap.tangerine.dao.SectionDao;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.service.customization.FieldService;
import com.orangeleap.tangerine.service.customization.PageCustomizationService;
import com.orangeleap.tangerine.type.CacheGroupType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;

import edu.emory.mathcs.backport.java.util.Collections;

public class SectionDefinitionFormController extends SimpleFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "pageCustomizationService")
    private PageCustomizationService pageCustomizationService;
    
    @Resource(name = "sectionDAO")
    private SectionDao sectionDao;
    
    @Resource(name = "siteService")
    private SiteService siteService;
    
    @Resource(name = "fieldService")
    private FieldService fieldService;
    
    @Resource(name = "cacheGroupDAO")
    private CacheGroupDao cacheGroupDao;

    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;


  
    
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
    	
        String id = request.getParameter("id"); 
        String pageType = request.getParameter("pageType"); 
        
        ModelAndView mav = new ModelAndView(getSuccessView());
        
        SectionDefinition sectionDef = sectionDao.readSectionDefinition(new Long(id));
        List<SectionField> sectionFields = getSectionFields(sectionDef);
        
        List<SectionFieldView> fieldList = new ArrayList<SectionFieldView>();
        for (SectionField sf: sectionFields) fieldList.add(new SectionFieldView(sf, fieldService.lookupFieldRequired(sf)));
        
        mav.addObject("fieldList", fieldList);
        mav.addObject("id", id);
        mav.addObject("pageType", pageType);
        
        return mav;
    }
    
    private static String getKey(SectionField sf) {
    	return sf.getFieldDefinition().getId() + ":" + ( sf.getSecondaryFieldDefinition() == null ? "" : sf.getSecondaryFieldDefinition().getId() ) ;
    }
    
    private static String getSectionFieldLongDescription(SectionField sf) {
    	
    	FieldDefinition fd = sf.getSecondaryFieldDefinition() == null ? sf.getFieldDefinition() : sf.getSecondaryFieldDefinition();
    	String result = fd.getId();
    	
    	int i = result.indexOf("[");
    	if (i > -1) result = result.substring(i);
    	result = result.replace("[", "").replace("]", "").trim();
    	i = result.lastIndexOf(".");
    	if (i > -1) result = result.substring(i+1);
    	
    	result = getFriendlyName(result);
    	result = result.replace("_", " ");
    	
    	return result;
    }
    
    private static String getFriendlyName(String s) {
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < s.length();i++) {
    		char c = s.charAt(i);
    		if (Character.isUpperCase(c)) {
    			sb.append(" ");
    		}
    		if (i == 0) c = Character.toUpperCase(c);
			sb.append(c);
    	}
    	return sb.toString();
    }

 
    public static final class SectionFieldView {
    	
		private SectionField sectionField;
    	private boolean visible;
    	private boolean required;
    	private String name;
    	private String description;
    	private String longDescription;
    	private String tabIndex;

    	public SectionFieldView(SectionField sf, FieldRequired fr) {
    		setSectionField(sf);
    		setVisible(sf.getFieldOrder() != 0);
    		setName(getKey(sf));
    		setDescription(sf.getFieldDefinition().getDefaultLabel());
    		setLongDescription(getSectionFieldLongDescription(sf));
    		setRequired(fr != null && fr.isRequired());
    		setTabIndex(""+sf.getTabIndex());
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
		public void setRequired(boolean required) {
			this.required = required;
		}
		public boolean isRequired() {
			return required;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

		public void setLongDescription(String longDescription) {
			this.longDescription = longDescription;
		}

		public String getLongDescription() {
			return longDescription;
		}
		
		public void setTabIndex(String tabIndex) {
			this.tabIndex = tabIndex;
		}

		public String getTabIndex() {
			return tabIndex;
		}

    }
    
    private List<SectionField> getSectionFields(SectionDefinition sectionDef) {
    	
        List<SectionField> sectionFields = pageCustomizationService.readSectionFieldsBySection(sectionDef, true);
        
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
    private static final String TOGGLE_REQUIRED = "togglerequired";
    private static final String CHANGE_DESC = "changedescription";
    private static final String CHANGE_TAB_INDEX = "changetabindex";
    
    
    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
    	
        if (!PageTypeManageController.accessAllowed(request)) return null;
        
        String id = request.getParameter("id"); 
        String fieldName = request.getParameter("fieldName"); 
        String action = request.getParameter("action"); 
        String description = request.getParameter("description"); 
        String tabIndex = request.getParameter("tabIndex"); 
        boolean toggleVisible = TOGGLE_VISIBLE.equals(action);
        boolean toggleRequired = TOGGLE_REQUIRED.equals(action);
        boolean moveUp = MOVE_UP.equals(action);
        boolean changeDescription = CHANGE_DESC.equals(action);
        boolean changeTabIndex = CHANGE_TAB_INDEX.equals(action);
        
        SectionDefinition sectionDef = sectionDao.readSectionDefinition(new Long(id));
        List<SectionField> sectionFields = getSectionFields(sectionDef);

        // Locate target field
        SectionField targetSectionField = null;
        int index = -1;
        int maxvalue = -1;
        for (int i = 0; i < sectionFields.size(); i++) {
        	SectionField sf = sectionFields.get(i);
        	if (getKey(sf).equals(fieldName)) {
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

        } else if (toggleRequired) {
        	
        	FieldRequired fieldRequired = fieldService.lookupFieldRequired(targetSectionField);
        	if (fieldRequired == null) fieldRequired = getNewFieldRequired(targetSectionField);
        	if (fieldRequired.getSite() == null) {
        		// Create a site-specific copy
        		fieldRequired.setSite(new Site(tangerineUserHelper.lookupUserSiteName()));
        		fieldRequired.setId(null);
        	}
        	
        	boolean isRequired = fieldRequired.isRequired();
        	
        	if (isRequired) {
        		fieldRequired.setRequired(false);
        	} else {
        		fieldRequired.setRequired(true);
        	}
            pageCustomizationService.maintainFieldRequired(fieldRequired);

        } else if (changeDescription) {
        	
        	if (!targetSectionField.getFieldDefinition().getDefaultLabel().equals(description)) {
	            targetSectionField.getFieldDefinition().setDefaultLabel(description);
	            pageCustomizationService.maintainFieldDefinition(targetSectionField.getFieldDefinition());
        	}
        	
        } else if (changeTabIndex) {
        	
        	try {
        		Integer itabIndex = new Integer(tabIndex);
	        	if (!targetSectionField.getTabIndex().equals(itabIndex)) {
		            targetSectionField.setTabIndex(itabIndex);
		            pageCustomizationService.maintainSectionField(targetSectionField);
	        	}
        	} catch (Exception e) {}
        	
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
        cacheGroupDao.updateCacheGroupTimestamp(CacheGroupType.PICKLIST); // Field required is in the picklist cache

        return new ModelAndView(getSuccessView());

    }
    
    private FieldRequired getNewFieldRequired(SectionField sf) {
    	FieldRequired fieldRequired = new FieldRequired();
    	fieldRequired.setFieldDefinition(sf.getFieldDefinition());
    	fieldRequired.setSecondaryFieldDefinition(sf.getSecondaryFieldDefinition());
    	fieldRequired.setSectionName(sf.getSectionDefinition().getSectionName());
    	fieldRequired.setRequired(false);
    	fieldRequired.setSite(new Site(tangerineUserHelper.lookupUserSiteName()));
    	return fieldRequired;
    }
    
}
