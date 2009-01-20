package com.mpower.controller.picklist;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.mpower.domain.customization.PicklistItem;
import com.mpower.service.PicklistItemService;
import com.mpower.service.impl.SessionServiceImpl;

public class PicklistItemHelperController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PicklistItemService picklistItemService;

    public void setPicklistItemService(PicklistItemService picklistItemService) {
        this.picklistItemService = picklistItemService;
    }

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String searchString = request.getParameter("q");
        Boolean showInactive;
        if (StringUtils.equalsIgnoreCase(request.getParameter("inactive"), "all")) {
            showInactive = null;
        } else {
            showInactive = Boolean.valueOf(request.getParameter("inactive"));
        }
        if (GenericValidator.isBlankOrNull(searchString)) {
            searchString = request.getParameter("value");
        }
        if (searchString == null) {
            searchString = "";
        }
        String description = request.getParameter("description");
        String picklistId = request.getParameter("picklistId");
        List<PicklistItem> picklistItems;
        if (description != null) {
        	picklistItems = picklistItemService.readPicklistItems(SessionServiceImpl.lookupUserSiteName(), picklistId, searchString, description,
                    showInactive);
        } else {
        	picklistItems = picklistItemService.readPicklistItems(SessionServiceImpl.lookupUserSiteName(), picklistId, searchString);
        }
        ModelAndView mav = new ModelAndView(super.getViewName());
        mav.addObject("picklistItems", picklistItems);
        return mav;
    }

}
