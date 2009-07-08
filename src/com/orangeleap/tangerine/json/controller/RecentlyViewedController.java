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

import com.orangeleap.tangerine.service.RecentlyViewedService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * This controller provides some simple methods to read
 * and update the Most Recently Used (MRU) list of accounts,
 * which is typically displayed via the History menu item
 * at the top of pages
 *
 * @version 1.0
 */
@Controller
public class RecentlyViewedController {

    @Resource(name = "recentlyViewedService")
    private RecentlyViewedService recentlyViewedService;

    @RequestMapping("/mruUpdate.json")
    public ModelMap updateMRU(String accountNumber) {
        Long longAcct = -1L;
        try {
            longAcct = Long.valueOf(accountNumber);
        } catch (NumberFormatException nex) {
            // if we can't parse the number, ignore for now and send
            // back the default list
            return getMRU();
        }

        List<Map> rows = recentlyViewedService.addRecentlyViewed(longAcct);
        return new ModelMap("rows", rows);

    }

    @RequestMapping("/mruList.json")
    public ModelMap getMRU() {

        List<Map> rows = recentlyViewedService.getRecentlyViewed();
        return new ModelMap("rows", rows);
    }
}
