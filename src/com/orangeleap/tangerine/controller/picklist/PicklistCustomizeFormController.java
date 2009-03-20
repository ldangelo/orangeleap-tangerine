package com.orangeleap.tangerine.controller.picklist;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.orangeleap.tangerine.domain.customization.Picklist;

public class PicklistCustomizeFormController extends PicklistCustomizeBaseController {

    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {
        String picklistNameId = request.getParameter("picklistNameId");

        Picklist picklist = picklistItemService.getPicklist(picklistNameId);
        
        Map<String, String> stringmap = getMap(picklist.getCustomFieldMap());
    	if (stringmap.size() == 0) {
    		if (isOutOfBoxDependentPicklist(picklist)) {
    			stringmap.put(PARENT_LIST, BLANK);    	
    			stringmap.put(ITEM_TEMPLATE + PARENT_VALUE, BLANK);
    		}
    		if (isGLCoded(picklist)) {
	    		stringmap.put(ITEM_TEMPLATE + GL_ACCOUNT_CODE, BLANK);
	    		stringmap.put(ITEM_TEMPLATE + "01-GLPART1", BLANK);
	    		stringmap.put(ITEM_TEMPLATE + "02-GLPART2", BLANK);
	    		stringmap.put(ITEM_TEMPLATE + "03-GLPART3", BLANK);
	    		stringmap.put(ITEM_TEMPLATE + "04-GLPART4", BLANK);
    		}
    	}

		if (stringmap.size() == 0) stringmap.put("", "");
        request.setAttribute("map", stringmap);
        request.setAttribute("picklist", picklist);
        return super.showForm(request, response, errors, controlModel);
    }
    
    private boolean isOutOfBoxDependentPicklist(Picklist picklist) {
    	return picklist.getPicklistName().equals("stateProvince");
    }
    
	@Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
        String picklistNameId = request.getParameter("picklistNameId");
        
        Picklist picklist = picklistItemService.getPicklist(picklistNameId);
     
        Map<String, String> stringmap = getMap(request);

        updateCustomFieldMap(stringmap, picklist);
         
        picklist = picklistItemService.maintainPicklist(picklist);
        
        ModelAndView mav = new ModelAndView(getSuccessView());
        stringmap = getMap(picklist.getCustomFieldMap());
		
        if (stringmap.size() == 0) stringmap.put("", "");
		mav.addObject("map", stringmap);
		mav.addObject("picklist", picklist);
        return mav;
        
    }
	    
	
}
