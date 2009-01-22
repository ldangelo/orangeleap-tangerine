package com.mpower.controller.picklist;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.mpower.domain.customization.Picklist;
import com.mpower.domain.customization.PicklistItem;
import com.mpower.service.PicklistItemService;
import com.mpower.service.impl.PicklistItemServiceImpl;
import com.mpower.service.impl.SessionServiceImpl;

public class PicklistItemFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PicklistItemService picklistItemService;

    public void setPicklistItemService(PicklistItemService picklistItemService) {
        this.picklistItemService = picklistItemService;
    }
    
	public static void removeSiteFromId(Picklist picklist) {
		String id = picklist.getId();
		int i = id.indexOf("-");
		if (i > -1) id = id.substring(i+1);
		picklist.setId(id);
	}
	
	public static void addSiteToId(String siteName, Picklist picklist) {
		picklist.setId(PicklistItemServiceImpl.addSiteToId(siteName, picklist.getId()));
	}
    
	@Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
       
        String picklistId = request.getParameter("picklistId");
        String picklistItemId = request.getParameter("picklistItemId");

        PicklistItem picklistItem = new PicklistItem();
        if (picklistItemId != null) {
	        Picklist picklist = picklistItemService.getPicklist(SessionServiceImpl.lookupUserSiteName(), picklistId);
	        if (picklist != null) {
	        	removeSiteFromId(picklist);
	            for (PicklistItem item : picklist.getPicklistItems()) {
	            	if (picklistItemId.equals(item.getId().toString())) {
	            		return item;
	            	}
	            }
	            picklistItem.setPicklist(picklist);
	        }
        }
        
        return picklistItem;
    }

    @Override
    public ModelAndView onSubmit(
    		HttpServletRequest request,
    		HttpServletResponse response,
    		Object command,
    		BindException errors		
    ) throws ServletException {
    	
        PicklistItem picklistItem = (PicklistItem) command;
        String siteName = SessionServiceImpl.lookupUserSiteName();
        
        // Need to modify id outside of transaction
        addSiteToId(siteName, picklistItem.getPicklist());
        PicklistItem newPicklistItem = picklistItemService.maintainPicklistItem(SessionServiceImpl.lookupUserSiteName(), picklistItem);
        removeSiteFromId(picklistItem.getPicklist());
        
        ModelAndView mav = new ModelAndView(getSuccessView());
        mav.addObject("picklistItem", newPicklistItem);
        return mav;
        
    }
}
