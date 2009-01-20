package com.mpower.controller.picklist;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.mpower.domain.customization.PicklistItem;
import com.mpower.service.PicklistItemService;

public class PicklistItemFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PicklistItemService picklistItemService;

    public void setPicklistItemService(PicklistItemService picklistItemService) {
        this.picklistItemService = picklistItemService;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        String picklistItemId = request.getParameter("picklistItemId");
        String picklistId = request.getParameter("picklistId");
        if (picklistItemId == null) {
            PicklistItem picklistItem = new PicklistItem();
            picklistItem.setPicklist(picklistItemService.readPicklist(picklistId));
            return picklistItem;
        } else {
            return picklistItemService.readPicklistItemById(new Long(picklistItemId));
        }
    }

    @Override
    public ModelAndView onSubmit(Object command, BindException errors) throws ServletException {
        PicklistItem picklistItem = (PicklistItem) command;
        //picklistItem.setPicklist(picklistItemService.readPicklist(picklistItem.getPicklist().getId()));
        PicklistItem newPicklistItem = picklistItemService.maintainPicklistItem(picklistItem);

        ModelAndView mav = new ModelAndView(getSuccessView());
        mav.addObject("picklistItem", newPicklistItem);
        return mav;
    }
}
