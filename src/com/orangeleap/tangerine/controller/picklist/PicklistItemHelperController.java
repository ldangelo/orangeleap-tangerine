package com.orangeleap.tangerine.controller.picklist;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.service.PicklistItemService;

public class PicklistItemHelperController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="picklistItemService")
    private PicklistItemService picklistItemService;

	@Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String picklistNameId = request.getParameter("picklistNameId");

        Picklist picklist = picklistItemService.getPicklist(picklistNameId);
        if (picklist == null) {
            return null;
        }

    	List<PicklistItem> picklistItems = picklist.getPicklistItems();

    	String inactive = request.getParameter("inactive");
    	if (inactive == null || inactive.length() == 0) {
            inactive = "all";
        }
    	
    	Boolean showInactive;
        if (StringUtils.equalsIgnoreCase(inactive, "all")) {
            showInactive = null;
        } else {
            showInactive = Boolean.valueOf(inactive);
        } 	
        
        String searchString = request.getParameter("q");
        if (GenericValidator.isBlankOrNull(searchString)) {
            searchString = request.getParameter("value");
        }
        if (searchString == null) {
            searchString = "";
        }
        String description = request.getParameter("description");
        if (GenericValidator.isBlankOrNull(description)) {
            description = "";
        }
        
        searchString = searchString.toUpperCase();
        description = description.toUpperCase();
        
        List<PicklistItem> filteredPicklistItems = new ArrayList<PicklistItem>();
    	for (PicklistItem item : picklistItems) {
    		if (showInactive == null || showInactive.equals(item.isInactive())) {
		        if (description.length() > 0) {
		        	if (item.getDefaultDisplayValue() != null && item.getDefaultDisplayValue().toUpperCase().startsWith(description)) {
                        filteredPicklistItems.add(item);
                    }
		        } else {
		        	if (item.getItemName() != null && item.getItemName().toUpperCase().startsWith(searchString)) {
                        filteredPicklistItems.add(item);
                    }
		        }
    		}
    	}
    	
        ModelAndView mav = new ModelAndView(super.getViewName());
        mav.addObject("picklistItems", filteredPicklistItems);
        return mav;
    }
}
