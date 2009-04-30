package com.orangeleap.tangerine.controller.picklist;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.service.PicklistItemService;

public class PicklistItemManageController extends ParameterizableViewController {
	
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="picklistItemService")
    private PicklistItemService picklistItemService;

	@Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if (!PicklistCustomizeBaseController.picklistEditAllowed(request)) return null; 
		
    	List<Picklist> picklists = picklistItemService.listPicklists();
        ModelAndView mav = new ModelAndView(super.getViewName());
        mav.addObject("picklists", picklists);
        return mav;
        
    }
}
