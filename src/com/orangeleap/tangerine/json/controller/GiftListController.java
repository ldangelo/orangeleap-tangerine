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

package com.orangeleap.tangerine.json.controller;

import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This controller handles JSON requests for populating
 * the grid of gifts.
 *
 * @version 1.0
 */
@Controller
public class GiftListController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    private final static Map<String, Object> NAME_MAP = new HashMap<String, Object>();

    static {
        NAME_MAP.put("id", "id");
        NAME_MAP.put("date", "date");
        NAME_MAP.put("constituentId", "constituentid");
        NAME_MAP.put("amount", "amount");
        NAME_MAP.put("currencycode", "currencycode");
        NAME_MAP.put("type", "type");
        NAME_MAP.put("status", "status");
        NAME_MAP.put("comments", "comments");
        NAME_MAP.put("refnumber", "refnumber");
        NAME_MAP.put("authcode", "authcode");
        NAME_MAP.put("gifttype", "gifttype");
    }

    @Resource(name = "giftService")
    private GiftService giftService;

	@Resource(name = "picklistItemService")
	private PicklistItemService picklistItemService;

    @SuppressWarnings("unchecked")
    @RequestMapping("/giftList.json")
    public ModelMap getGiftList(HttpServletRequest request, SortInfo sortInfo) {
        List<Map> rows = new ArrayList<Map>();

        // if we're not getting back a valid column name, possible SQL injection,
        // so send back an empty list.
        if (!sortInfo.validateSortField(NAME_MAP.keySet())) {
            logger.warn("getGiftList called with invalid sort column: [" + sortInfo.getSort() + "]");
            return new ModelMap("rows", rows);
        }

        // set the sort to the valid column name, based on the map
        sortInfo.setSort((String) NAME_MAP.get(sortInfo.getSort()));

        String constituentId = request.getParameter("constituentId");
        PaginatedResult result = giftService.readPaginatedGiftList(Long.valueOf(constituentId), sortInfo);
	    for (Object row : result.getRows()) {
		    Map thisGift = (Map) row;

		    PicklistItem item = picklistItemService.getPicklistItem("gift.paymentType", (String) thisGift.get("type"));
		    if (item != null) {
			    thisGift.put("type", item.resolveDisplayValue());
		    }
	    }

        ModelMap map = new ModelMap("rows", result.getRows());
        map.put("totalRows", result.getRowCount());
        return map;
    }
}
