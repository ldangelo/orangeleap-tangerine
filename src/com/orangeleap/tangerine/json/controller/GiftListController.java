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

import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

/**
 * This controller handles JSON requests for populating
 * the grid of gifts.
 * @version 1.0
 */
@Controller
public class GiftListController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private final static Map<String, Object> NAME_MAP = new HashMap<String, Object>();

    static {
        NAME_MAP.put("id", "g.GIFT_ID");
        NAME_MAP.put("date", "g.DONATION_DATE");
        NAME_MAP.put("personId", "g.CONSTITUENT_ID");
        NAME_MAP.put("amount", "g.AMOUNT");
        NAME_MAP.put("currencyCode", "g.CURRENCY_CODE");
        NAME_MAP.put("paymentStatus", "g.PAYMENT_STATUS");
        NAME_MAP.put("comments", "g.COMMENTS");
        NAME_MAP.put("refNumber", "g.PAYMENT_TXREFNUM");
        NAME_MAP.put("authcode", "g.AUTH_CODE");
    }
    
    private Map<String,Object> giftToMap(Gift g) {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", g.getId());
        map.put("date", formatter.format(g.getDonationDate()) );
        map.put("personId", g.getPerson().getId());
        map.put("amount", g.getAmount());
        map.put("currencyCode", g.getCurrencyCode());
        map.put("paymentStatus", g.getPaymentStatus());
        map.put("comments", g.getComments());
        map.put("refNumber", g.getTxRefNum());
        map.put("authcode", g.getAuthCode());
    
        return map;

    }

    @Resource(name="giftService")
    private GiftService giftService;

    @SuppressWarnings("unchecked")
    @RequestMapping("/giftList.json")
    public ModelMap getGiftList(HttpServletRequest request, SortInfo sortInfo) {

        List<Map> rows = new ArrayList<Map>();

        // if we're not getting back a valid column name, possible SQL injection,
        // so send back an empty list.
        if(!sortInfo.validateSortField(NAME_MAP.keySet())) {
            logger.warn("getGiftList called with invalid sort column: [" + sortInfo.getSort() + "]");
            return new ModelMap("rows", rows);
        }

        // set the sort to the valid column name, based on the map
        sortInfo.setSort( (String) NAME_MAP.get(sortInfo.getSort()) );

        String personId = request.getParameter("personId");
        PaginatedResult result = giftService.readPaginatedMonetaryGifts(Long.valueOf(personId), sortInfo); 

        List<Gift> list = result.getRows();

        for(Gift g : list) {
            rows.add( giftToMap(g) );
        }

        ModelMap map = new ModelMap("rows", rows);
        map.put("totalRows", result.getRowCount());
        return map;
    }
    


}
