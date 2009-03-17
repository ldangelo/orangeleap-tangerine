package com.orangeleap.tangerine.json.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.orangeleap.tangerine.service.AuditService;
import com.orangeleap.tangerine.web.common.SortInfo;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.Audit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * This controller handles JSON requests for populating
 * the grid of audit events.
 * @version 1.0
 */
@Controller
public class AuditListController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

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
    @RequestMapping("/auditList.json")
    public ModelMap getAuditEvents(HttpServletRequest request, SortInfo sortInfo) {

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
            if (EntityType.valueOf(entityType) == EntityType.person) {
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
        map.put("description", audit.getDescription());
        map.put("objectType", audit.getEntityType());
        map.put("objectId", audit.getObjectId());
        map.put("personId", audit.getConstituentId());

        return map;

    }



}
