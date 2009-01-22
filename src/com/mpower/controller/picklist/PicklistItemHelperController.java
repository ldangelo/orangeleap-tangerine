package com.mpower.controller.picklist;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.mpower.domain.customization.Picklist;
import com.mpower.domain.customization.PicklistItem;
import com.mpower.service.PicklistItemService;

public class PicklistItemHelperController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PicklistItemService picklistItemService;

    public void setPicklistItemService(PicklistItemService picklistItemService) {
        this.picklistItemService = picklistItemService;
    }

	@Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
		Picklist currentPicklist = PicklistItemFormController.getCurrentPicklist(request);

        if (currentPicklist == null) return null;
    	List<PicklistItem> currentPicklistItems = currentPicklist.getPicklistItems();

    	Boolean showInactive;
        if (StringUtils.equalsIgnoreCase(request.getParameter("inactive"), "all")) {
            showInactive = null;
        } else {
            showInactive = Boolean.valueOf(request.getParameter("inactive"));
        }
    	
        String searchString = request.getParameter("q");
        if (GenericValidator.isBlankOrNull(searchString)) {
            searchString = request.getParameter("value");
        }
        if (searchString == null) {
            searchString = "";
        }
        String description = request.getParameter("description");
        if (GenericValidator.isBlankOrNull(description)) description = "";
        
        searchString = searchString.toUpperCase();
        description = description.toUpperCase();
        
        List<PicklistItem> picklistItems = new ArrayList<PicklistItem>();
    	for (PicklistItem item : currentPicklistItems) {
    		if (showInactive || !item.isInactive()) {
		        if (description.length() > 0) {
		        	if (item.getDefaultDisplayValue() != null && item.getDefaultDisplayValue().toUpperCase().startsWith(description)) picklistItems.add(item);
		        } else {
		        	if (item.getItemName() != null && item.getItemName().toUpperCase().startsWith(searchString)) picklistItems.add(item);
		        }
    		}
    	}
    	
        ModelAndView mav = new ModelAndView(super.getViewName());
        mav.addObject("picklistItems", picklistItems);
        return mav;
        
    }

}
