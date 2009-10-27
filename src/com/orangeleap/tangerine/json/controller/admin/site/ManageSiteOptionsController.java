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

package com.orangeleap.tangerine.json.controller.admin.site;

import com.orangeleap.tangerine.domain.SiteOption;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manageSiteOptions.json")
public class ManageSiteOptionsController {

    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "siteService")
    private SiteService siteService;

    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;

    private final static String MASK = "*****";

    // TODO: move to a filter or something and throw an UnauthorizedAccessException
    @SuppressWarnings("unchecked")
    private void checkAccess(HttpServletRequest request) {
        Map<String, AccessType> pageAccess = (Map<String, AccessType>) WebUtils.getSessionAttribute(request, "pageAccess");
        if (pageAccess.get("/siteSettings.htm") != AccessType.ALLOWED) {
            throw new RuntimeException("You are not authorized to access this page");
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ModelMap getSiteOptions(HttpServletRequest request) throws Exception {
        checkAccess(request);
        ModelMap modelMap = new ModelMap();
        List<SiteOption> options = siteService.getSiteOptions();

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        addSiteOptions(options, returnList);
        modelMap.put(StringConstants.ROWS, returnList);
        modelMap.put(StringConstants.TOTAL_ROWS, returnList.size());
        return modelMap;
    }

    private void addSiteOptions(List<SiteOption> options, List<Map<String, Object>> returnList) {
        for (SiteOption option : options) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(StringConstants.ID, option.getOptionName());
            map.put(StringConstants.NAME, option.getOptionName());
            map.put("nameRO", option.getOptionNameReadOnly());
            map.put("desc", option.getOptionDesc());
            map.put(StringConstants.VALUE, option.getOptionValue());
            map.put("valRO", option.getOptionValueReadOnly());
            returnList.add(map);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ModelMap saveSiteOptions(HttpServletRequest request, String rows) throws Exception {
        checkAccess(request);
        List<SiteOption> options = siteService.getSiteOptions();

        JSONArray jsonArray = JSONArray.fromObject(rows);
        List<DynaBean> beans = (List<DynaBean>) JSONSerializer.toJava(jsonArray);
        Iterator<DynaBean> beanIterator = beans.iterator();
        Iterator<SiteOption> optionIterator = options.iterator();
        while (optionIterator.hasNext()) {
            SiteOption option = optionIterator.next();
            boolean found = false;
            while (beanIterator.hasNext()) {
                DynaBean bean = beanIterator.next();
                if (bean.getDynaClass().getDynaProperty(StringConstants.ID) != null &&
                        bean.get(StringConstants.ID) != null && 
                        option.getOptionName().equals(bean.get(StringConstants.ID))) {
                    if (bean.getDynaClass().getDynaProperty(StringConstants.NAME) != null && bean.get(StringConstants.NAME) != null) {
                        option.setOptionName((String) bean.get(StringConstants.NAME));
                    }
                    if (bean.getDynaClass().getDynaProperty("desc") != null && bean.get("desc") != null) {
                        option.setOptionDesc((String) bean.get("desc"));
                    }
                    if (bean.getDynaClass().getDynaProperty(StringConstants.VALUE) != null && bean.get(StringConstants.VALUE) != null) {
                        option.setOptionValue((String) bean.get(StringConstants.VALUE));
                    }
                    found = true;
                    beanIterator.remove();
                }
            }
            if (found) {
                siteService.maintainSiteOption(option);
            }
            else {
                siteService.deleteSiteOptionById(option.getId());
                optionIterator.remove();
            }
        }

        // The following are new options
        beanIterator = beans.iterator();
        while (beanIterator.hasNext()) {
            DynaBean bean = beanIterator.next();
            if (bean.getDynaClass().getDynaProperty(StringConstants.ID) != null &&
                    bean.get(StringConstants.ID) != null) {
                SiteOption option = new SiteOption();
                if (bean.getDynaClass().getDynaProperty(StringConstants.NAME) != null && bean.get(StringConstants.NAME) != null) {
                    option.setOptionName((String) bean.get(StringConstants.NAME));
                }
                if (bean.getDynaClass().getDynaProperty("desc") != null && bean.get("desc") != null) {
                    option.setOptionDesc((String) bean.get("desc"));
                }
                if (bean.getDynaClass().getDynaProperty(StringConstants.VALUE) != null && bean.get(StringConstants.VALUE) != null) {
                    option.setOptionValue((String) bean.get(StringConstants.VALUE));
                }
                siteService.maintainSiteOption(option);
                options.add(option);
            }
        }

        ModelMap map = new ModelMap();

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        addSiteOptions(options, returnList);
        map.put(StringConstants.ROWS, returnList);
        map.put(StringConstants.TOTAL_ROWS, returnList.size());
        map.put(StringConstants.SUCCESS, Boolean.TRUE.toString().toLowerCase());
        return map;
    }
}