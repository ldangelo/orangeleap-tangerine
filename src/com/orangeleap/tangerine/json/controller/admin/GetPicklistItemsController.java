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

package com.orangeleap.tangerine.json.controller.admin;

import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GetPicklistItemsController {
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "picklistItemService")
    private PicklistItemService picklistItemService;

    @SuppressWarnings("unchecked")
    @RequestMapping("/getPicklistItems.json")
    public ModelMap getPicklistItems(String picklistNameId) {
        if (logger.isTraceEnabled()) {
            logger.trace("getPicklistItems: picklistNameId = " + picklistNameId);
        }
        ModelMap map = new ModelMap();
        Picklist picklist = picklistItemService.getPicklist(picklistNameId);
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        if (picklist != null && picklist.getPicklistItems() != null) {
            Collections.sort(picklist.getPicklistItems(), new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    PicklistItem item1 = (PicklistItem) o1;
                    PicklistItem item2 = (PicklistItem) o2;
                    if (item1.getItemOrder() == null) {
                        item1.setItemOrder(0);
                    }
                    if (item2.getItemOrder() == null) {
                        item2.setItemOrder(0);
                    }
                    return item1.getItemOrder().compareTo(item2.getItemOrder());
                }
            });
            for (PicklistItem item : picklist.getPicklistItems()) {
                if (item != null) {
                    Map<String, Object> pMap = new HashMap<String, Object>();
                    pMap.put(StringConstants.ID, item.getId());
                    pMap.put("b", item.getItemName());
                    pMap.put("c", item.getDefaultDisplayValue());
                    pMap.put("d", item.getLongDescription());
                    pMap.put("e", item.getDetail());
                    pMap.put("f", item.getItemOrder());
                    pMap.put("g", item.isInactive());
                    returnList.add(pMap);
                }
            }
        }
        map.put("rows", returnList);
        map.put("totalRows", returnList.size());
        return map;
    }
}
