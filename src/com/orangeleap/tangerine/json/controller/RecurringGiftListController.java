package com.orangeleap.tangerine.json.controller;

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

import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

/**
 * This controller handles JSON requests for populating
 * the grid of pledges.
 * @version 1.0
 */
@Controller
public class RecurringGiftListController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private final static Map<String, Object> NAME_MAP = new HashMap<String, Object>();

    static {
        NAME_MAP.put("id", "rg.GIFT_ID");
        NAME_MAP.put("personId", "rg.CONSTITUENT_ID");
        NAME_MAP.put("status", "rg.RECURRING_GIFT_STATUS");
        NAME_MAP.put("amountpergift", "rg.AMOUNT_PER_GIFT");
        NAME_MAP.put("amounttotal", "rg.AMOUNT_TOTAL");
        NAME_MAP.put("amountpaid", "rg.AMOUNT_PAID");
        NAME_MAP.put("amountremaining", "rg.AMOUNT_REMAINING");
    }
    
    private Map<String,Object> recurringGiftToMap(RecurringGift rg) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", rg.getId());
        map.put("personId", rg.getPerson().getId());
        map.put("status", rg.getRecurringGiftStatus());
        map.put("amountpergift", rg.getAmountPerGift());
        map.put("amounttotal", rg.getAmountTotal());
        map.put("amountpaid", rg.getAmountPaid());
        map.put("amountremaining", rg.getAmountRemaining());
        
        return map;

    }

    @Resource(name="recurringGiftService")
    private RecurringGiftService recurringGiftService;

    @SuppressWarnings("unchecked")
    @RequestMapping("/recurringGiftList.json")
    public ModelMap getRecurringGiftList(HttpServletRequest request, SortInfo sortInfo) {

        List<Map> rows = new ArrayList<Map>();

        // if we're not getting back a valid column name, possible SQL injection,
        // so send back an empty list.
        if(!sortInfo.validateSortField(NAME_MAP.keySet())) {
            logger.warn("getPledgeList called with invalid sort column: [" + sortInfo.getSort() + "]");
            return new ModelMap("rows", rows);
        }

        // set the sort to the valid column name, based on the map
        sortInfo.setSort( (String) NAME_MAP.get(sortInfo.getSort()) );

        String personId = request.getParameter("personId");
        PaginatedResult result = recurringGiftService.readPaginatedRecurringGiftsByConstituentId(Long.valueOf(personId), sortInfo); 

        List<RecurringGift> list = result.getRows();

        for(RecurringGift g : list) {
        	
//        	g.setGifts(giftService.readGiftsByRecurringGiftId(g));
            rows.add( recurringGiftToMap(g) );
        }

        ModelMap map = new ModelMap("rows", rows);
        map.put("totalRows", result.getRowCount());
        return map;
    }
    


}
