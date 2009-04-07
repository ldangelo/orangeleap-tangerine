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
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.orangeleap.tangerine.domain.CommunicationHistory;
import com.orangeleap.tangerine.service.CommunicationHistoryService;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

/**
 * This controller handles JSON requests for populating
 * the grid of touch points.
 * @version 1.0
 */
@Controller
public class TouchPointListController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private final static Map<String, Object> NAME_MAP = new HashMap<String, Object>();

    static {
        NAME_MAP.put("id", "ch.COMMUNICATION_HISTORY_ID");
        NAME_MAP.put("date", "ch.RECORD_DATE");
        NAME_MAP.put("personId", "ch.PERSON_ID");
        NAME_MAP.put("type", "ch.ENTRY_TYPE");
        NAME_MAP.put("comments", "ch.COMMENTS");
    }
    
    private Map<String,Object> communicationHistoryToMap(CommunicationHistory ch) {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", ch.getId());
        map.put("date", formatter.format(ch.getRecordDate()) );
        map.put("personId", ch.getPerson().getId());
        map.put("type", ch.getEntryType());
        map.put("comments", ch.getComments());
    
        return map;

    }

    @Resource(name="communicationHistoryService")
    private CommunicationHistoryService communicationHistoryService;

    @SuppressWarnings("unchecked")
    @RequestMapping("/communicationHistoryList.json")
    public ModelMap getCommunicationHistory(HttpServletRequest request, SortInfo sortInfo) {

        List<Map> rows = new ArrayList<Map>();

        // if we're not getting back a valid column name, possible SQL injection,
        // so send back an empty list.
        if(!sortInfo.validateSortField(NAME_MAP.keySet())) {
            logger.warn("getCommunicationHistory called with invalid sort column: [" + sortInfo.getSort() + "]");
            return new ModelMap("rows", rows);
        }

        // set the sort to the valid column name, based on the map
        sortInfo.setSort( (String) NAME_MAP.get(sortInfo.getSort()) );

        String personId = request.getParameter("personId");
        PaginatedResult result = communicationHistoryService.readCommunicationHistoryByConstituent(Long.valueOf(personId), sortInfo); 

        List<CommunicationHistory> list = result.getRows();

        for(CommunicationHistory ch : list) {
            rows.add( communicationHistoryToMap(ch) );
        }

        ModelMap map = new ModelMap("rows", rows);
        map.put("totalRows", result.getRowCount());
        return map;
    }
    


}
