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

import static com.orangeleap.tangerine.util.StringConstants.GROUP;
import static com.orangeleap.tangerine.util.StringConstants.ID;
import static com.orangeleap.tangerine.util.StringConstants.LABEL;
import static com.orangeleap.tangerine.util.StringConstants.VALUE;

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

import com.orangeleap.tangerine.domain.rollup.RollupAttribute;
import com.orangeleap.tangerine.domain.rollup.RollupSeries;
import com.orangeleap.tangerine.domain.rollup.RollupValue;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.rollup.RollupService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineMessageAccessor;

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


    @RequestMapping(method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ModelMap getGiftSummary(HttpServletRequest request) throws Exception {
    	
        ModelMap modelMap = new ModelMap();
        Long constituentId = Long.valueOf(request.getParameter("constituentId"));
        if (null == constituentService.readConstituentById(constituentId)) return null; // checks constituent id is in site.

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        addViewData(constituentId, returnList);
        modelMap.put(StringConstants.ROWS, returnList);
        modelMap.put(StringConstants.TOTAL_ROWS, returnList.size());
        return modelMap;
        
    }

    private void addViewData(Long constituentId, List<Map<String, Object>> returnList) {
    	Map<RollupAttribute, Map<RollupSeries, List<RollupValue>>> m = rollupService.readGiftViewRollupValuesByConstituentId(constituentId);
    	
/*
        String paymentProcessingMsg = TangerineMessageAccessor.getMessage("paymentProcessing");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ID, "merchantNumber");
        map.put(LABEL, TangerineMessageAccessor.getMessage("merchantNumber"));
        map.put(VALUE, "");
        map.put(GROUP, paymentProcessingMsg);
        map.put(CLASS, "string");
        map.put(MAX_LEN, "255");
        returnList.add(map);
*/
        

      
    }

}
