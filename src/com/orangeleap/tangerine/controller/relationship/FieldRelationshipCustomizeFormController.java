/*
 * Customizing the relationship field results in a set of custom field template names and default values, similar to picklists.
 * These values are used when initializing the RelationshipCustomizeFormController fields for a specific constituent's relationship field's custom fields.
 * 
 */


package com.orangeleap.tangerine.controller.relationship;

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.CustomFieldRelationship;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.service.CustomFieldRelationshipService;
import com.orangeleap.tangerine.service.RelationshipService;
import com.orangeleap.tangerine.type.AccessType;

public class FieldRelationshipCustomizeFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    

    @Resource(name="customFieldRelationshipService")
    protected CustomFieldRelationshipService customFieldRelationshipService;
 
    @Resource(name="relationshipService")
    protected RelationshipService relationshipService;
 
    @Resource(name="fieldDAO")
    protected FieldDao fieldDao;
    
    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        return "";
    }
	
	protected Map<String, String> getMap(Map<String, CustomField> map) {
		Map<String, String> result = new TreeMap<String, String>();
		for (Map.Entry<String, CustomField> entry : map.entrySet()) {
			result.put(entry.getValue().getName(), entry.getValue().getValue());
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	protected Map<String, String> getMap(HttpServletRequest request) {
		Map map = new TreeMap<String, String>();
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String parm = (String)e.nextElement();
			if (parm.startsWith("cfname")) {
				String fieldnum = parm.substring(6);
				String key = request.getParameter(parm).trim();
				String value = request.getParameter("cfvalue"+fieldnum).trim();
				if (key.length() > 0) map.put(key, value);
			}
		}
		return map;
	}
	
	protected void updateCustomFieldMap(Map<String, String> map, AbstractCustomizableEntity entity) {
		entity.getCustomFieldMap().clear();
		for (Map.Entry<String, String> e : map.entrySet()) {
			entity.setCustomFieldValue(e.getKey(), e.getValue());
		}
	}
	
	@SuppressWarnings("unchecked")
	public static boolean editAllowed(HttpServletRequest request) {
		Map<String, AccessType> pageAccess = (Map<String, AccessType>)WebUtils.getSessionAttribute(request, "pageAccess");
		return pageAccess.get("/fieldRelationshipCustomize.htm") == AccessType.ALLOWED;
	}

	
    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {
     
    	if (!editAllowed(request)) return null;
    	
    	String fieldDefinitionId = request.getParameter("fieldDefinitionId");
    	FieldDefinition fd = fieldDao.readFieldDefinition(fieldDefinitionId);

    	String masterFieldDefinitionId = customFieldRelationshipService.getMasterFieldDefinitonId(fieldDefinitionId);
    	
        CustomFieldRelationship customFieldRelationship = customFieldRelationshipService.readByFieldDefinitionId(masterFieldDefinitionId);
        if (customFieldRelationship == null) {
        	customFieldRelationship = new CustomFieldRelationship();
        	customFieldRelationship.setMasterFieldDefinitionId(masterFieldDefinitionId);
        	customFieldRelationship = customFieldRelationshipService.maintainCustomFieldRelationshipCustomFields(customFieldRelationship);
        }
        
        Map<String, String> stringmap = getMap(customFieldRelationship.getCustomFieldMap());

		if (stringmap.size() == 0) stringmap.put("", "");
        ModelAndView mav = super.showForm(request, response, errors, controlModel);
        mav.addObject("fieldDefinition", fd);
        mav.addObject("map", stringmap);
        mav.addObject("customFieldRelationship", customFieldRelationship);
        return mav;
    }
    
	@Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {

		String fieldDefinitionId = request.getParameter("fieldDefinitionId");
    	FieldDefinition fd = fieldDao.readFieldDefinition(fieldDefinitionId);

    	String id = request.getParameter("id");

        CustomFieldRelationship customFieldRelationship = customFieldRelationshipService.readById(new Long(id));
     
        Map<String, String> stringmap = getMap(request);

        updateCustomFieldMap(stringmap, customFieldRelationship);
         
        customFieldRelationship = customFieldRelationshipService.maintainCustomFieldRelationshipCustomFields(customFieldRelationship);
        
        ModelAndView mav = new ModelAndView(getSuccessView());
        stringmap = getMap(customFieldRelationship.getCustomFieldMap());
		
        if (stringmap.size() == 0) stringmap.put("", "");
        mav.addObject("fieldDefinition", fd);
		mav.addObject("map", stringmap);
		mav.addObject("customFieldRelationship", customFieldRelationship);
        return mav;
        
    }
	    

	
}
