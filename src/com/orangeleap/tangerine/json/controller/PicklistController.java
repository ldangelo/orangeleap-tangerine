package com.orangeleap.tangerine.json.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.validator.GenericValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import com.orangeleap.tangerine.domain.PaymentHistory;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.service.PaymentHistoryService;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;
import com.orangeleap.tangerine.type.AccessType;

/**
 * This controller handles JSON requests for populating
 * the grid of payment history.
 * @version 1.0
 */
@Controller
public class PicklistController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());


    @Resource(name="picklistItemService")
    private PicklistItemService picklistItemService;

    @SuppressWarnings("unchecked")
    @RequestMapping("/picklistItem.json")
    public ModelMap picklistItem(HttpServletRequest request, SortInfo sortInfo) {

        if (!picklistEditAllowed(request)) return null;

        String picklistId = request.getParameter("picklistId");

        Picklist picklist = picklistItemService.getPicklistById(new Long(picklistId));
        if (picklist == null || picklist.getSite() == null) {
            return null;
        }


        String reorder = request.getParameter("reorder");
        if ("up".equals(reorder)) {
            Long picklistItemId = new Long(request.getParameter("picklistItemId"));
            for (int i = 0; i < picklist.getPicklistItems().size(); i++) {
                PicklistItem item = picklist.getPicklistItems().get(i);
                if (item.getId().equals(picklistItemId)) {
                    if (i > 0) {
                       int n = item.getItemOrder();
                       PicklistItem prev = picklist.getPicklistItems().get(i-1);
                       item.setItemOrder(prev.getItemOrder());
                       prev.setItemOrder(n); 
                       picklistItemService.maintainPicklist(picklist);
                    }
                    break;
                }
            }
        }


        return new ModelMap();
    }

    @SuppressWarnings("unchecked")
    public static boolean picklistEditAllowed(HttpServletRequest request) {
        Map<String, AccessType> pageAccess = (Map<String, AccessType>) WebUtils.getSessionAttribute(request, "pageAccess");
        return pageAccess.get("/picklistItems.htm") == AccessType.ALLOWED;
    }

       

}