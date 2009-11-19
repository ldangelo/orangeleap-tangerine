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

import static com.orangeleap.tangerine.util.StringConstants.ID;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.rollup.RollupAttribute;
import com.orangeleap.tangerine.domain.rollup.RollupSeries;
import com.orangeleap.tangerine.domain.rollup.RollupValue;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.rollup.RollupService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;

@Controller
@RequestMapping("/giftSummary.json")
public class GiftSummaryController {

    protected final Log logger = OLLogger.getLog(getClass());
    
    @Resource(name = "constituentService")
    private ConstituentService constituentService;

    @Resource(name = "rollupService")
    private RollupService rollupService;

    
    private final static String CLASS = "class";
    private final static String MAX_LEN = "maxLen";
    
    private final static String ATTRIBUTE = "attribute";
    private final static String SERIES = "series";
    private final static String START_DATE = "startDate";
    private final static String END_DATE = "endDate";
    private final static String CURRENCY_CODE = "currencyCode";
    private final static String COUNT = "count";
    private final static String SUM = "sum";
    private final static String MIN = "min";
    private final static String MAX = "max";
    private final static String AVG = "avg";


    @RequestMapping(method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ModelMap getGiftSummary(HttpServletRequest request) throws Exception {
    	
        ModelMap modelMap = new ModelMap();
        
        Long constituentId = new Long(request.getParameter(StringConstants.CONSTITUENT_ID));
        if (null == constituentService.readConstituentById(constituentId)) return null; // checks constituent id is in site.

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        addViewData(constituentId, returnList);
        modelMap.put(StringConstants.ROWS, returnList);
        modelMap.put(StringConstants.TOTAL_ROWS, returnList.size());
        return modelMap;
        
    }

    private void addViewData(Long constituentId, List<Map<String, Object>> returnList) {

    	Map<RollupAttribute, Map<RollupSeries, List<RollupValue>>> data = rollupService.readGiftViewRollupValuesByConstituentId(constituentId);
    	
    	int i = 0;
    	for (Map.Entry<RollupAttribute, Map<RollupSeries, List<RollupValue>>> me : data.entrySet()) {
    		RollupAttribute ra = me.getKey();
    		Map<RollupSeries, List<RollupValue>> seriesmap = me.getValue();
        	for (Map.Entry<RollupSeries, List<RollupValue>> me2 : seriesmap.entrySet()) {
        		RollupSeries rs = me2.getKey();
        		List<RollupValue> rolluplist = me2.getValue();
            	for (RollupValue rv : rolluplist) {

                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put(ID, "" + i++);
                    map.put(CLASS, "string");
                    map.put(MAX_LEN, "255");

                    map.put(ATTRIBUTE, ra.getAttributeDesc());
                    map.put(SERIES, rs.getSeriesDesc());
                    map.put(START_DATE, rv.getStartDate().equals(CustomField.PAST_DATE)?null:rv.getStartDate());
                    map.put(END_DATE, rv.getEndDate().equals(CustomField.FUTURE_DATE)?null:rv.getEndDate());
                    map.put(CURRENCY_CODE, rv.getCurrencyCode());
                    map.put(COUNT, rv.getCountValue());
                    map.put(SUM, rv.getSumValue());
                    map.put(MIN, rv.getMinValue());
                    map.put(MAX, rv.getMaxValue());
                    BigDecimal avg = rv.getSumValue().movePointRight(2).divideToIntegralValue(rv.getCountValue()).movePointLeft(2);
                    map.put(AVG, avg);
                	returnList.add(map);
            		
            	}
        	}    		
    	}

    }
    
}
