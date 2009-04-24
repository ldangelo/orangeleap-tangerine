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

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.customization.ConstituentCustomFieldRelationship;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.service.ConstituentCustomFieldRelationshipService;

public class RelationshipCustomizeFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    

    @Resource(name="constituentCustomFieldRelationshipService")
    protected ConstituentCustomFieldRelationshipService constituentCustomFieldRelationshipService;
    
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
    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {
    	
    	String id = request.getParameter("id");

        ConstituentCustomFieldRelationship constituentCustomFieldRelationship = constituentCustomFieldRelationshipService.readById(new Long(id));
        
        Map<String, String> stringmap = getMap(constituentCustomFieldRelationship.getCustomFieldMap());

		if (stringmap.size() == 0) stringmap.put("", "");
        request.setAttribute("map", stringmap);
        request.setAttribute("constituentCustomFieldRelationship", constituentCustomFieldRelationship);
        return super.showForm(request, response, errors, controlModel);
    }
    
	@Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
    	
		// NOTE: On first save we need to make a copy of the field relationship record to a site-specific one if it is not already, and change the field relationship id being edited!
		// This is because we can't save the generic parent ids in a site-specific dependent record in case they want to customize the relationship for the site later.
		
    	String id = request.getParameter("id");

        ConstituentCustomFieldRelationship constituentCustomFieldRelationship = constituentCustomFieldRelationshipService.readById(new Long(id));
     
        Map<String, String> stringmap = getMap(request);

        updateCustomFieldMap(stringmap, constituentCustomFieldRelationship);
         
        constituentCustomFieldRelationship = constituentCustomFieldRelationshipService.maintainConstituentCustomFieldRelationshipCustomFields(constituentCustomFieldRelationship);
        
        ModelAndView mav = new ModelAndView(getSuccessView());
        stringmap = getMap(constituentCustomFieldRelationship.getCustomFieldMap());
		
        if (stringmap.size() == 0) stringmap.put("", "");
		mav.addObject("map", stringmap);
		mav.addObject("constituentCustomFieldRelationship", constituentCustomFieldRelationship);
        return mav;
        
    }
	    

	
}
