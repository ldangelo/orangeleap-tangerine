package com.orangeleap.tangerine.controller.picklist;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;

public class PicklistItemCustomizeFormController extends PicklistCustomizeBaseController {

	public final static String ACCOUNT_STRING_1 = "AccountString1";
	public final static String ACCOUNT_STRING_2 = "AccountString2";
	
	
    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {
        String picklistId = request.getParameter("picklistId");
        String picklistItemId = request.getParameter("picklistItemId");

        Picklist picklist = picklistItemService.getPicklistById(new Long(picklistId));
        PicklistItem item = getPicklistItem(picklist, new Long(picklistItemId));
        
        Map<String, String> stringmap = getMap(item.getCustomFieldMap());
		if (stringmap.size() < 1 || (isGLCoded(picklist) && stringmap.size() < 2)) {
	        addDefaultFields(picklist, stringmap);
		}
		
		if (stringmap.size() == 0) stringmap.put("", "");
        request.setAttribute("map", stringmap);
        request.setAttribute("picklistItem", item);
        return super.showForm(request, response, errors, controlModel);
    }
    
	@Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
        String picklistId = request.getParameter("picklistId");
        String picklistItemId = request.getParameter("picklistItemId");
        
        Picklist picklist = picklistItemService.getPicklistById(new Long(picklistId));
        PicklistItem item = getPicklistItem(picklist, new Long(picklistItemId));
     
        Map<String, String> stringmap = getMap(request);
        if (isGLCoded(picklist)) {
        	stringmap.put(ACCOUNT_STRING_1, getAccountString(stringmap, false));
        	stringmap.put(ACCOUNT_STRING_2, getAccountString(stringmap, true));
        }
        
        updateCustomFieldMap(stringmap, item);
         
    	item = picklistItemService.maintainPicklistItem(item);
        
        ModelAndView mav = new ModelAndView(getSuccessView());
        stringmap = getMap(item.getCustomFieldMap());
		
        if (stringmap.size() == 0) stringmap.put("", "");
		mav.addObject("map", stringmap);
		mav.addObject("picklistItem", item);
        return mav;
        
    }
	
    private void addDefaultFields(Picklist picklist, Map<String, String> map) {
    	for (Map.Entry<String, CustomField> e : picklist.getCustomFieldMap().entrySet()) {
    		String name = e.getValue().getName();
    		if (name.startsWith(ITEM_TEMPLATE)) {
    			name = name.substring(ITEM_TEMPLATE.length());
	    		String value = e.getValue().getValue();
	    		if (value.equalsIgnoreCase(BLANK)) value = "";
	    		map.put(name, value);
    		}
    	}
    }

    private PicklistItem getPicklistItem(Picklist picklist, Long picklistItemId) {
    	if (picklist != null) {
    		if (picklistItemId != null) {
    			for (PicklistItem item : picklist.getPicklistItems()) {
    				if (picklistItemId.equals(item.getId())) {
    					return picklistItemService.getPicklistItem(item.getId());
    				}
    			}
    		}
    	}
    	return null;
    }
	
	private String getAccountString(Map<String, String> map, boolean extraDash) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> e : map.entrySet()) {
			if (e.getKey().matches("^[0-9]+-.*")) {
				String value = e.getValue().trim();
				boolean hasValue = value.length() > 0;
				if (sb.length() > 0) {
					if (hasValue || extraDash) sb.append("-");
				}
				sb.append(e.getValue());
			}
		}
		return sb.toString();
	}
	
}
