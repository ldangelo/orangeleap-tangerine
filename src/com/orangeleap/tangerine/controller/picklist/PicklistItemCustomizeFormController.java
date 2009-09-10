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

import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class PicklistItemCustomizeFormController extends PicklistCustomizeBaseController {

    public final static String ACCOUNT_STRING_1 = "AccountString1";
    public final static String ACCOUNT_STRING_2 = "AccountString2";


    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {

        if (!picklistEditAllowed(request)) return null;

        String picklistId = request.getParameter("picklistId");
        String picklistItemId = request.getParameter("picklistItemId");

        Picklist picklist = picklistItemService.getPicklistById(new Long(picklistId));
        PicklistItem item = getPicklistItem(picklist, new Long(picklistItemId));

        Map<String, String> stringmap = getMap(item.getCustomFieldMap());
        addDefaultFields(picklist, stringmap);

        if (stringmap.size() == 0) stringmap.put("", "");
        request.setAttribute("map", stringmap);
        request.setAttribute("picklist", picklist);
        request.setAttribute("picklistItem", item);
        return super.showForm(request, response, errors, controlModel);
    }

    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {

        if (!picklistEditAllowed(request)) return null;

        String picklistId = request.getParameter("picklistId");
        String picklistItemId = request.getParameter("picklistItemId");

        Picklist picklist = picklistItemService.getPicklistById(new Long(picklistId));
        PicklistItem item = getPicklistItem(picklist, new Long(picklistItemId));

        Map<String, String> stringmap = getMap(request);
        if (isGLCoded(picklist)) {
            stringmap.put(ACCOUNT_STRING_1, getAccountString(stringmap, false));
            stringmap.put(ACCOUNT_STRING_2, getAccountString(stringmap, true));
        }

        updateCustomFieldMap(stringmap, item);

        item = picklistItemService.maintainPicklistItem(item);

        ModelAndView mav = new ModelAndView(getSuccessView());
        stringmap = getMap(item.getCustomFieldMap());

        if (stringmap.size() == 0) stringmap.put("", "");
        mav.addObject("map", stringmap);
        mav.addObject("picklist", picklist);
        mav.addObject("picklistItem", item);
        return mav;

    }

    private void addDefaultFields(Picklist picklist, Map<String, String> map) {
        for (Map.Entry<String, CustomField> e : picklist.getCustomFieldMap().entrySet()) {
            String name = e.getValue().getName();
            if (name.startsWith(ITEM_TEMPLATE)) {
                name = name.substring(ITEM_TEMPLATE.length());
                String value = e.getValue().getValue();
                if (value.equalsIgnoreCase(BLANK)) value = "";
                if (!map.containsKey(name)) map.put(name, value);
            }
        }
    }

    private PicklistItem getPicklistItem(Picklist picklist, Long picklistItemId) {
        if (picklist != null) {
            if (picklistItemId != null) {
                for (PicklistItem item : picklist.getPicklistItems()) {
                    if (picklistItemId.equals(item.getId())) {
                        return picklistItemService.getPicklistItem(item.getId());
                    }
                }
            }
        }
        return null;
    }

    private String getAccountString(Map<String, String> map, boolean extraDash) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> e : map.entrySet()) {
            if (e.getKey().matches("^[0-9]+-.*")) {
                String value = e.getValue().trim();
                boolean hasValue = value.length() > 0;
                if (sb.length() > 0) {
                    if (hasValue || extraDash) sb.append("-");
                }
                sb.append(e.getValue());
            }
        }
        return sb.toString();
    }

}
