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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.validator.GenericValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import com.orangeleap.tangerine.domain.Audit;
import com.orangeleap.tangerine.service.AuditService;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.util.HttpUtil;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

/**
 * This controller handles JSON requests for populating
 * the grid of audit events.
 * @version 1.0
 */
@Controller
public class AuditListController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    private final static Map<String, Object> NAME_MAP = new HashMap<String, Object>();

    static {
        NAME_MAP.put("id", "AUDIT_ID");
        NAME_MAP.put("date", "DATE");
        NAME_MAP.put("user", "USER");
        NAME_MAP.put("type", "AUDIT_TYPE");
        NAME_MAP.put("description", "DESCRIPTION");
        NAME_MAP.put("objecttype", "ENTITY_TYPE");
        NAME_MAP.put("objectid","OBJECT_ID");
    }

    @Resource(name="auditService")
    private AuditService auditService;
    
	@SuppressWarnings("unchecked")
	public static boolean accessAllowed(HttpServletRequest request) {
		Map<String, AccessType> pageAccess = (Map<String, AccessType>)WebUtils.getSessionAttribute(request, "pageAccess");
		return pageAccess.get("/siteAudit.htm") == AccessType.ALLOWED;
	}

    @SuppressWarnings("unchecked")
    @RequestMapping("/auditList.json")
    public ModelMap getAuditEvents(HttpServletRequest request, SortInfo sortInfo) {
    	
    	if (!accessAllowed(request)) {
			return null;
		} 

        List<Map> rows = new ArrayList<Map>();

        // if we're not getting back a valid column name, possible SQL injection,
        // so send back an empty list.
        if(!sortInfo.validateSortField(NAME_MAP.keySet())) {
            logger.warn("getAuditEvents called with invalid sort column: [" + sortInfo.getSort() + "]");
            return new ModelMap("rows", rows);
        }

        // set the sort to the valid column name, based on the map
        sortInfo.setSort( (String) NAME_MAP.get(sortInfo.getSort()) );

        String entityType = request.getParameter("object");
        String objectId = request.getParameter("id");
        PaginatedResult result = null;
        if (GenericValidator.isBlankOrNull(entityType) || GenericValidator.isBlankOrNull(objectId)) {
            result = auditService.allAuditHistoryForSite(sortInfo);
        } else {
            if (EntityType.valueOf(entityType) == EntityType.constituent) {
                result = auditService.auditHistoryForConstituent(Long.valueOf(objectId), sortInfo);
            } else {
                result = auditService.auditHistoryForEntity(EntityType.valueOf(entityType).name(),
                        Long.valueOf(objectId), sortInfo);
            }
        }

        List<Audit> list = result.getRows();

        for(Audit audit : list) {
            rows.add( auditEventToMap(audit) );
        }

        ModelMap map = new ModelMap("rows", rows);
        map.put("totalRows", result.getRowCount());
        return map;
    }

    private Map<String,Object> auditEventToMap(Audit audit) {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // keys should map with the NAME_MAP constant
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", audit.getId());
        map.put("date", formatter.format(audit.getDate()) );
        map.put("user", audit.getUser());
        map.put("type", audit.getAuditType().name());
        map.put("description", HttpUtil.jsEscape(audit.getDescription()));
        map.put("objectType", "communicationhistory".equals(audit.getEntityType()) ? "touch point" : audit.getEntityType());
        map.put("objectId", audit.getObjectId());
        map.put("constituentId", audit.getConstituentId());

        return map;

    }



}
