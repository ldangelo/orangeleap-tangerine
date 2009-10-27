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

import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import static com.orangeleap.tangerine.util.StringConstants.GROUP;
import static com.orangeleap.tangerine.util.StringConstants.ID;
import static com.orangeleap.tangerine.util.StringConstants.LABEL;
import static com.orangeleap.tangerine.util.StringConstants.NAME;
import static com.orangeleap.tangerine.util.StringConstants.VALUE;
import com.orangeleap.tangerine.util.TangerineMessageAccessor;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manageSiteSettings.json")
public class ManageSiteSettingsController {

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
    public ModelMap getSiteSettings(HttpServletRequest request) throws Exception {
        checkAccess(request);
        ModelMap modelMap = new ModelMap();
        Site site = siteService.readSite(tangerineUserHelper.lookupUserSiteName());

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        addSiteSettings(site, returnList);
        modelMap.put(StringConstants.ROWS, returnList);
        modelMap.put(StringConstants.TOTAL_ROWS, returnList.size());
        return modelMap;
    }

    private void addSiteSettings(Site site, List<Map<String, Object>> returnList) {
        String paymentProcessingMsg = TangerineMessageAccessor.getMessage("paymentProcessing");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ID, "merchantNumber");
        map.put(LABEL, TangerineMessageAccessor.getMessage("merchantNumber"));
        map.put(VALUE, site.getMerchantNumber());
        map.put(GROUP, paymentProcessingMsg);
        returnList.add(map);

        map = new HashMap<String, Object>();
        map.put(ID, "merchantBin");
        map.put(LABEL, TangerineMessageAccessor.getMessage("merchantBin"));
        map.put(VALUE, site.getMerchantBin());
        map.put(GROUP, paymentProcessingMsg);
        returnList.add(map);

        map = new HashMap<String, Object>();
        map.put(ID, "merchantTerminalId");
        map.put(LABEL, TangerineMessageAccessor.getMessage("merchantTerminalId"));
        map.put(VALUE, site.getMerchantTerminalId());
        map.put(GROUP, paymentProcessingMsg);
        returnList.add(map);

        map = new HashMap<String, Object>();
        map.put(ID, "achSiteNumber");
        map.put(LABEL, TangerineMessageAccessor.getMessage("achSiteNumber"));
        map.put(VALUE, site.getAchSiteNumber());
        map.put(GROUP, paymentProcessingMsg);
        returnList.add(map);

        map = new HashMap<String, Object>();
        map.put(ID, "achMerchantId");
        map.put(LABEL, TangerineMessageAccessor.getMessage("achMerchantId"));
        map.put(VALUE, site.getAchMerchantId());
        map.put(GROUP, paymentProcessingMsg);
        returnList.add(map);

        map = new HashMap<String, Object>();
        map.put(ID, "achRuleNumber");
        map.put(LABEL, TangerineMessageAccessor.getMessage("achRuleNumber"));
        map.put(VALUE, site.getAchRuleNumber());
        map.put(GROUP, paymentProcessingMsg);
        returnList.add(map);

        map = new HashMap<String, Object>();
        map.put(ID, "achCompanyName");
        map.put(LABEL, TangerineMessageAccessor.getMessage("achCompanyName"));
        map.put(VALUE, site.getAchCompanyName());
        map.put(GROUP, paymentProcessingMsg);
        returnList.add(map);

        map = new HashMap<String, Object>();
        map.put(ID, "achTestModeForm");
        map.put(LABEL, TangerineMessageAccessor.getMessage("achTestModeForm"));
        map.put(VALUE, site.getAchTestModeForm());
        map.put(GROUP, paymentProcessingMsg);
        returnList.add(map);

        String emailServerMsg = TangerineMessageAccessor.getMessage("emailServer");
        map = new HashMap<String, Object>();
        map.put(ID, "smtpServerName");
        map.put(LABEL, TangerineMessageAccessor.getMessage("smtpServerName"));
        map.put(VALUE, site.getSmtpServerName());
        map.put(GROUP, emailServerMsg);
        returnList.add(map);

        map = new HashMap<String, Object>();
        map.put(ID, "smtpAccountName");
        map.put(LABEL, TangerineMessageAccessor.getMessage("smtpAccountName"));
        map.put(VALUE, site.getSmtpAccountName());
        map.put(GROUP, emailServerMsg);
        returnList.add(map);

        map = new HashMap<String, Object>();
        map.put(ID, "smtpPassword");
        map.put(LABEL, TangerineMessageAccessor.getMessage("smtpPassword"));
        map.put(VALUE, MASK);
        map.put(GROUP, emailServerMsg);
        returnList.add(map);

        map = new HashMap<String, Object>();
        map.put(ID, "smtpFromAddress");
        map.put(LABEL, TangerineMessageAccessor.getMessage("smtpFromAddress"));
        map.put(VALUE, site.getSmtpFromAddress());
        map.put(GROUP, emailServerMsg);
        returnList.add(map);

        String guruConnectionMsg = TangerineMessageAccessor.getMessage("guruConnection");
        map = new HashMap<String, Object>();
        map.put(ID, "jasperUserId");
        map.put(LABEL, TangerineMessageAccessor.getMessage("jasperUserId"));
        map.put(VALUE, site.getJasperUserId());
        map.put(GROUP, guruConnectionMsg);
        returnList.add(map);

        map = new HashMap<String, Object>();
        map.put(ID, "jasperPassword");
        map.put(LABEL, TangerineMessageAccessor.getMessage("jasperPassword"));
        map.put(VALUE, MASK);
        map.put(GROUP, guruConnectionMsg);
        returnList.add(map);
    }

    @RequestMapping(method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ModelMap saveSiteSettings(HttpServletRequest request, String rows) throws Exception {
        checkAccess(request);
        Site site = siteService.readSite(tangerineUserHelper.lookupUserSiteName());
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(site);

        JSONArray jsonArray = JSONArray.fromObject(rows);
        List<DynaBean> beans = (List<DynaBean>) JSONSerializer.toJava(jsonArray);
        for (DynaBean bean : beans) {
            if (bean.getDynaClass().getDynaProperty(ID) != null && bean.get(ID) != null) {
                String fieldName = (String) bean.get(ID);
                if ( ! NAME.equals(fieldName) && ! "active".equals(fieldName) && ! "createDate".equals(fieldName) && bw.isWritableProperty(fieldName)) {
                    String fieldValue = null;
                    if (bean.getDynaClass().getDynaProperty(VALUE) != null && bean.get(VALUE) != null) {
                        fieldValue = (String) bean.get(VALUE);
                        if (StringConstants.EMPTY.equals(fieldValue)) {
                            fieldValue = null;
                        }
                    }
                    if (("smtpPassword".equals(fieldName) || "jasperPassword".equals(fieldName))) {
                        if ( ! MASK.equals(fieldValue)) {
                            bw.setPropertyValue(fieldName, fieldValue);
                        }
                    }
                    else {
                        bw.setPropertyValue(fieldName, fieldValue);
                    }
                }
            }
        }

        siteService.updateSite(site);
        ModelMap map = new ModelMap();

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        addSiteSettings(site, returnList);
        map.put(StringConstants.ROWS, returnList);
        map.put(StringConstants.TOTAL_ROWS, returnList.size());
        map.put(StringConstants.SUCCESS, Boolean.TRUE.toString().toLowerCase());
        return map;
    }
}
