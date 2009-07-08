/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.controller.picklist;

import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PicklistItemFormController extends SimpleFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "picklistItemService")
    private PicklistItemService picklistItemService;

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {

        if (!PicklistCustomizeBaseController.picklistEditAllowed(request)) return null;

        String picklistNameId = request.getParameter("picklistNameId");
        String picklistId = request.getParameter("picklistId");
        String picklistItemId = request.getParameter("picklistItemId");
        String itemName = request.getParameter("itemName");

        PicklistItem picklistItem = new PicklistItem();
        if (picklistNameId != null && picklistNameId.length() > 0) {
            Picklist picklist = picklistItemService.getPicklist(picklistNameId);
            picklistItem.setPicklistId(picklist.getId());
        }

        if (picklistId != null && picklistId.length() > 0) {
            Picklist picklist = picklistItemService.getPicklistById(new Long(picklistId));
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

        if (!PicklistCustomizeBaseController.picklistEditAllowed(request)) return null;

        PicklistItem picklistItem = (PicklistItem) command;
        // Need to modify id outside of transaction
        PicklistItem newPicklistItem = picklistItemService.maintainPicklistItem(picklistItem);

        ModelAndView mav = new ModelAndView(getSuccessView());
        mav.addObject("picklistItem", newPicklistItem);
        return mav;

    }
}
