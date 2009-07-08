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

import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.service.PledgeService;
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
public class PledgeListController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    private final static Map<String, Object> NAME_MAP = new HashMap<String, Object>();

    static {
        NAME_MAP.put("id", "c.GIFT_ID");
        NAME_MAP.put("constituentId", "c.CONSTITUENT_ID");
        NAME_MAP.put("status", "c.PLEDGE_STATUS");
        NAME_MAP.put("amountpergift", "c.AMOUNT_PER_GIFT");
        NAME_MAP.put("amounttotal", "c.AMOUNT_TOTAL");
        NAME_MAP.put("amountpaid", "c.AMOUNT_PAID");
        NAME_MAP.put("amountremaining", "c.AMOUNT_REMAINING");
    }

    private Map<String, Object> pledgeToMap(Pledge c) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", c.getId());
        map.put("constituentId", c.getConstituent().getId());
        map.put("status", c.getPledgeStatus());
        map.put("amountpergift", c.getAmountPerGift());
        map.put("amounttotal", c.getAmountTotal());
        map.put("amountpaid", c.getAmountPaid());
        map.put("amountremaining", c.getAmountRemaining());

        return map;

    }

    @Resource(name = "pledgeService")
    private PledgeService pledgeService;

    @SuppressWarnings("unchecked")
    @RequestMapping("/pledgeList.json")
    public ModelMap getPledgeList(HttpServletRequest request, SortInfo sortInfo) {

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
        PaginatedResult result = pledgeService.readPaginatedPledgesByConstituentId(Long.valueOf(constituentId), sortInfo);

        List<Pledge> list = result.getRows();

        for (Pledge g : list) {
            rows.add(pledgeToMap(g));
        }

        ModelMap map = new ModelMap("rows", rows);
        map.put("totalRows", result.getRowCount());
        return map;
    }


}
