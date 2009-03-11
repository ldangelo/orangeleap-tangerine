package com.orangeleap.tangerine.controller.picklist;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.service.PicklistItemService;

public class PicklistItemFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Resource(name="picklistItemService")
    private PicklistItemService picklistItemService;
    
	@Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
       
        String picklistId = request.getParameter("picklistId");
        String picklistItemId = request.getParameter("picklistItemId");
        String itemName = request.getParameter("itemName");

        PicklistItem picklistItem = new PicklistItem();
        if (picklistId != null) {
	        Picklist picklist = picklistItemService.getPicklist(picklistId);
	        if (picklist != null) {
	        	if (picklistItemId != null) {
		            for (PicklistItem item : picklist.getPicklistItems()) {
		            	if (picklistItemId.equals(item.getId().toString())) {
		            		return item;
		            	}
		            }
		            for (PicklistItem item : picklist.getPicklistItems()) {
		            	if (item.getItemName().equals(itemName)) {
		            		return item;
		            	}
		            }
	        	}
	        	picklistItem.setItemOrder(picklist.getPicklistItems().size());
	            picklistItem.setPicklistId(picklist.getId());
	        }
        }
        
        return picklistItem;
    }

    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
        PicklistItem picklistItem = (PicklistItem) command;
        // Need to modify id outside of transaction
        PicklistItem newPicklistItem = picklistItemService.maintainPicklistItem(picklistItem);
        
        ModelAndView mav = new ModelAndView(getSuccessView());
        mav.addObject("picklistItem", newPicklistItem);
        return mav;
        
    }
}
