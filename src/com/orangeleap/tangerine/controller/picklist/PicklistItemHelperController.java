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
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.validator.GenericValidator;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class PicklistItemHelperController extends ParameterizableViewController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "picklistItemService")
    private PicklistItemService picklistItemService;

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (!PicklistCustomizeBaseController.picklistEditAllowed(request)) return null;

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
            if (item != null && (showInactive == null || showInactive.equals(item.isInactive()))) {
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
        mav.addObject("picklistDesc", picklist.getPicklistDesc());
        return mav;
    }
}
