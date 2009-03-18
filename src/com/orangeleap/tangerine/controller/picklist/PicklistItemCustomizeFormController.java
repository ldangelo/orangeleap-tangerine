package com.orangeleap.tangerine.controller.picklist;

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

import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.service.PicklistItemService;

public class PicklistItemCustomizeFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Resource(name="picklistItemService")
    private PicklistItemService picklistItemService;
    
	
	
    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {
        String picklistId = request.getParameter("picklistId");
        String picklistItemId = request.getParameter("picklistItemId");
        PicklistItem item = getPicklistItem(picklistId, picklistItemId);
        Map<String, String> map = getMap(item.getCustomFieldMap());
		if (map.size() == 0) {
	        if (item.getPicklistId().endsWith("projectCode")) {
	        	addDefaultGLCodes(map);
	        } else {
	        	map.put("", "");
	        }
		}
        request.setAttribute("map", map);
        request.setAttribute("picklistItem", item);
        return super.showForm(request, response, errors, controlModel);
    }
    
    private void addDefaultGLCodes(Map<String, String> map) {
    	for (int i = 1; i <= 10; i++) {
    		map.put("GL"+i, "");
    	}
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        return "";
    }
	
	private PicklistItem getPicklistItem(String picklistId, String picklistItemId) {
        if (picklistId != null) {
	        Picklist picklist = picklistItemService.getPicklist(picklistId);
	        if (picklist != null) {
	        	if (picklistItemId != null) {
		            for (PicklistItem item : picklist.getPicklistItems()) {
		            	if (picklistItemId.equals(item.getId().toString())) {
		            		return picklistItemService.getPicklistItem(item.getId());
		            	}
		            }
	        	}
	        }
        }
        return null;
	}
	
	private Map<String, String> getMap(Map<String, CustomField> map) {
		Map<String, String> result = new TreeMap<String, String>();
		for (Map.Entry<String, CustomField> entry : map.entrySet()) {
			result.put(entry.getValue().getName(), entry.getValue().getValue());
		}
		return result;
	}

	@Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
        String picklistId = request.getParameter("picklistId");
        String picklistItemId = request.getParameter("picklistItemId");
        PicklistItem item = getPicklistItem(picklistId, picklistItemId);
        
        updateCustomFieldMap(getMap(request), item);
         
    	item = picklistItemService.maintainPicklistItem(item);
        
        ModelAndView mav = new ModelAndView(getSuccessView());
        Map<String, String> map = getMap(item.getCustomFieldMap());
		if (map.size() == 0) map.put("", "");
		mav.addObject("map", map);
		mav.addObject("picklistItem", item);
        return mav;
        
    }
	
	@SuppressWarnings("unchecked")
	private Map<String, String> getMap(HttpServletRequest request) {
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
	
	private void updateCustomFieldMap(Map<String, String> map, PicklistItem item) {
		item.getCustomFieldMap().clear();
		for (Map.Entry<String, String> e : map.entrySet()) {
			item.setCustomFieldValue(e.getKey(), e.getValue());
		}
	}
	
}
