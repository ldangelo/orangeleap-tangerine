package com.mpower.controller.picklist;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.mpower.domain.customization.Picklist;
import com.mpower.service.PicklistItemService;
import com.mpower.service.impl.SessionServiceImpl;

public class PicklistItemManageController extends ParameterizableViewController {
	
	public static final String PICKLIST_MANAGE_DATA = "PICKLIST_MANAGE_DATA";

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PicklistItemService picklistItemService;

    public void setPicklistItemService(PicklistItemService picklistItemService) {
        this.picklistItemService = picklistItemService;
    }

    @SuppressWarnings("unchecked")
	@Override
    public ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

    	// Use a working copy of picklists for site-specific modifications
    	List<Picklist> picklists = (List<Picklist>)request.getSession().getAttribute(PICKLIST_MANAGE_DATA);
    	if (picklists == null) {
    		picklists = picklistItemService.listPicklists(SessionServiceImpl.lookupUserSiteName());
    		request.getSession().setAttribute(PICKLIST_MANAGE_DATA, picklists);  
    	}

        ModelAndView mav = new ModelAndView(super.getViewName());
        mav.addObject("picklists", picklists);

        return mav;
    }

}
