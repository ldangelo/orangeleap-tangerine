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

import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.service.RecurringGiftService;
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
 * the grid of pledges.
 *
 * @version 1.0
 */
@Controller
public class RecurringGiftListController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    private final static Map<String, Object> NAME_MAP = new HashMap<String, Object>();

    static {
        NAME_MAP.put("id", "rg.GIFT_ID");
        NAME_MAP.put("constituentId", "rg.CONSTITUENT_ID");
        NAME_MAP.put("status", "rg.RECURRING_GIFT_STATUS");
        NAME_MAP.put("amountpergift", "rg.AMOUNT_PER_GIFT");
        NAME_MAP.put("amounttotal", "rg.AMOUNT_TOTAL");
        NAME_MAP.put("amountpaid", "rg.AMOUNT_PAID");
        NAME_MAP.put("amountremaining", "rg.AMOUNT_REMAINING");
        NAME_MAP.put("startdate", "rg.START_DATE");
        NAME_MAP.put("enddate", "rg.END_DATE");
        NAME_MAP.put("activate", "rg.ACTIVATE");
    }

    private Map<String, Object> recurringGiftToMap(RecurringGift rg) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", rg.getId());
        map.put("constituentId", rg.getConstituent().getId());
        map.put("status", rg.getRecurringGiftStatus());
        map.put("amountpergift", rg.getAmountPerGift());
        map.put("amounttotal", rg.getAmountTotal());
        map.put("amountpaid", rg.getAmountPaid());
        map.put("amountremaining", rg.getAmountRemaining());
        map.put("startdate", rg.getStartDate());
        map.put("enddate", rg.getEndDate());
        map.put("activate", rg.isActivate());

        return map;

    }

    @Resource(name = "recurringGiftService")
    private RecurringGiftService recurringGiftService;

    @SuppressWarnings("unchecked")
    @RequestMapping("/recurringGiftList.json")
    public ModelMap getRecurringGiftList(HttpServletRequest request, SortInfo sortInfo) {

        List<Map> rows = new ArrayList<Map>();

        // if we're not getting back a valid column name, possible SQL injection,
        // so send back an empty list.
        if (!sortInfo.validateSortField(NAME_MAP.keySet())) {
            logger.warn("getPledgeList called with invalid sort column: [" + sortInfo.getSort() + "]");
            return new ModelMap("rows", rows);
        }

        // set the sort to the valid column name, based on the map
        sortInfo.setSort((String) NAME_MAP.get(sortInfo.getSort()));

        String constituentId = request.getParameter("constituentId");
        PaginatedResult result = recurringGiftService.readPaginatedRecurringGiftsByConstituentId(Long.valueOf(constituentId), sortInfo);

        List<RecurringGift> list = result.getRows();

        for (RecurringGift g : list) {

//        	g.setGifts(giftService.readGiftsByRecurringGiftId(g));
            rows.add(recurringGiftToMap(g));
        }

        ModelMap map = new ModelMap("rows", rows);
        map.put("totalRows", result.getRowCount());
        return map;
    }


}
