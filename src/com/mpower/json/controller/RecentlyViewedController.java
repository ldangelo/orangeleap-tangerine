package com.mpower.json.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.annotation.Resource;

import com.mpower.util.TangerineUserHelper;
import com.mpower.dao.interfaces.RecentlyViewedDao;
import com.mpower.service.RecentlyViewedService;

import java.util.List;
import java.util.Map;

/**
 * This controller provides some simple methods to read
 * and update the Most Recently Used (MRU) list of accounts,
 * which is typically displayed via the History menu item
 * at the top of pages
 * @version 1.0
 */
@Controller
public class RecentlyViewedController {

    @Resource(name="recentlyViewedService")
    private RecentlyViewedService recentlyViewedService;

    @RequestMapping("/mruUpdate.json")
    public ModelMap updateMRU(String accountNumber) {
        Long longAcct = -1L;
        try {
            longAcct = Long.valueOf(accountNumber);
        } catch(NumberFormatException nex) {
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
