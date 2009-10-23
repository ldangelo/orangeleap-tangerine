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

package com.orangeleap.tangerine.json.controller.admin.siteDefaults;

import com.orangeleap.tangerine.domain.customization.EntityDefault;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.service.customization.FieldService;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineMessageAccessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
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
@RequestMapping("/manageSiteDefaults.json")
public class ManageSiteDefaultsController {

    @Resource(name = "siteService")
    private SiteService siteService;

    @Resource(name = "fieldService")
    private FieldService fieldService;

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET)
    protected ModelMap getSiteDefaults(HttpServletRequest request) throws Exception {
        checkPrivileges(request);
        List<EntityDefault> defaults = siteService.readEntityDefaults();
        if (defaults != null) {
            findEditableEntityDefaults(defaults);
        }
        ModelMap map = new ModelMap();
        List<Map<String, Object>> returnList = resolveEntityDefaultsMap(defaults);
        map.put("rows", returnList);
        map.put("totalRows", returnList.size());
        return map;
    }

    private void findEditableEntityDefaults(List<EntityDefault> defaults) {
    	Iterator<EntityDefault> it = defaults.iterator();
    	while (it.hasNext()) {
    		EntityDefault ed = it.next();
    		if (ed.getConditionExp() != null || ed.getDefaultValue().contains(":")) { // TODO: make these defaults editable 
    			it.remove();
    		}
    	}
    }

    private List<Map<String, Object>> resolveEntityDefaultsMap(List<EntityDefault> defaults) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (EntityDefault aDefault : defaults) {
            Map<String, Object> defaultMap = new HashMap<String, Object>();
            defaultMap.put(StringConstants.ID, aDefault.getId());

            String resolvedEntityType = TangerineMessageAccessor.getMessage(aDefault.getEntityType());
            defaultMap.put("entityType", StringUtils.hasText(resolvedEntityType) ? resolvedEntityType : aDefault.getEntityType());

            String fieldDefEntityType = aDefault.getEntityType().equals(EntityType.distributionLine.toString()) ? StringConstants.DISTRIBUTION_LINES :
                    (aDefault.getEntityType().equals(EntityType.giftInKindDetail.toString()) ? "details" : aDefault.getEntityType());
            String fieldDefId = new StringBuilder(fieldDefEntityType).append(".").append(aDefault.getEntityFieldName()).toString();
            FieldDefinition fieldDefinition = fieldService.readFieldDefinition(fieldDefId);
            defaultMap.put("label", fieldDefinition != null && StringUtils.hasText(fieldDefinition.getDefaultLabel()) ?
                    fieldDefinition.getDefaultLabel() : aDefault.getEntityFieldName());
            
            defaultMap.put("entityName", aDefault.getEntityFieldName());
            defaultMap.put("entityValue", aDefault.getDefaultValue());

            mapList.add(defaultMap);
        }
        return mapList;
    }

    @RequestMapping(method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    protected ModelMap saveSiteDefaults(HttpServletRequest request, String rows) throws Exception {
        checkPrivileges(request);
        JSONArray jsonArray = JSONArray.fromObject(rows);
        List<DynaBean> beans = (List<DynaBean>) JSONSerializer.toJava(jsonArray);

        List<EntityDefault> defaults = siteService.readEntityDefaults();
        for (DynaBean bean : beans) {
            Long id = null;
            if (bean.getDynaClass().getDynaProperty(StringConstants.ID) != null && bean.get(StringConstants.ID) != null) {
                String idStr = bean.get(StringConstants.ID).toString();
                if (NumberUtils.isDigits(idStr)) {
                    id = new Long(idStr);
                }
            }
            if (id != null) {
                for (EntityDefault aDefault : defaults) {
                    if (aDefault.getId().equals(id)) {
                        String value = null;
                        if (bean.getDynaClass().getDynaProperty("entityValue") != null && bean.get("entityValue") != null) {
                            value = bean.get("entityValue").toString();
                        }
                        if (value != null) {
                            aDefault.setDefaultValue(value);
                            siteService.updateEntityDefault(aDefault);
                        }
                    }
                }
            }
        }

        ModelMap map = new ModelMap();
        List<Map<String, Object>> returnList = resolveEntityDefaultsMap(defaults);
        map.put("rows", returnList);
        map.put("totalRows", returnList.size());
        map.put("success", Boolean.TRUE.toString().toLowerCase());
        return map;
    }

    private void checkPrivileges(HttpServletRequest request) {
        Map<String, AccessType> pageAccess = (Map<String, AccessType>) WebUtils.getSessionAttribute(request, "pageAccess");
        if (pageAccess.get("/siteSettings.htm") != AccessType.ALLOWED) {
            throw new RuntimeException("You are not authorized to access this page");
        }
    }
}
