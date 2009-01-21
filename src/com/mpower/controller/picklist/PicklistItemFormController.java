package com.mpower.controller.picklist;

import java.util.List;

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
import com.mpower.service.impl.SessionServiceImpl;

public class PicklistItemFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PicklistItemService picklistItemService;

    public void setPicklistItemService(PicklistItemService picklistItemService) {
        this.picklistItemService = picklistItemService;
    }

    @SuppressWarnings("unchecked")
	public static Picklist getCurrentPicklist(HttpServletRequest request) {
        String picklistId = request.getParameter("picklistId");
        Picklist currentPicklist = null;
        List<Picklist> picklists = (List<Picklist>)request.getSession().getAttribute(PicklistItemManageController.PICKLIST_MANAGE_DATA);  
        for (Picklist picklist : picklists) {
        	if (picklist.getId().equals(picklistId)) {
        		currentPicklist = picklist;
        	}
        }
        return currentPicklist;
    }
    
	@Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
       
		Picklist currentPicklist = getCurrentPicklist(request);

        String picklistItemId = request.getParameter("picklistItemId");

        if (picklistItemId != null) {
            for (PicklistItem item : currentPicklist.getPicklistItems()) {
            	if (picklistItemId.equals(item.getId().toString())) {
            		return item;
            	}
            }
        }
        
        PicklistItem picklistItem = new PicklistItem();
        picklistItem.setPicklist(currentPicklist);
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
        
        PicklistItem newPicklistItem = picklistItemService.maintainPicklistItem(picklistItem);
        
        // Update working copy
        List<Picklist> picklists = picklistItemService.listPicklists(SessionServiceImpl.lookupUserSiteName());
		request.getSession().setAttribute(PicklistItemManageController.PICKLIST_MANAGE_DATA, picklists);  
		
        ModelAndView mav = new ModelAndView(getSuccessView());
        mav.addObject("picklistItem", newPicklistItem);
        return mav;
        
    }
}
