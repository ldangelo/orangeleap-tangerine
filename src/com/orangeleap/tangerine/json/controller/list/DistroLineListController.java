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

package com.orangeleap.tangerine.json.controller.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.AdjustedGiftService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.common.SortInfo;

@Controller
public class DistroLineListController extends TangerineJsonListController {

    @Resource(name = "giftService")
    private GiftService giftService;

    @Resource(name = "adjustedGiftService")
    private AdjustedGiftService adjustedGiftService;

    
    
    @RequestMapping("/softGiftList.json")
    public ModelMap getSoftGiftList(HttpServletRequest request, SortInfo sort) {
    	return getDistroLineList(request, sort, "onBehalfOf", "softGift");
    }
    
//    @RequestMapping("/tributeGiftList.json")
//    public ModelMap getTributeGiftList(HttpServletRequest request, SortInfo sort) {
//    	return getDistroLineList(request, sort, "tributeReference", "tributeGift");
//    }
    
    
    
    // Use for distro-line type-lists
    @SuppressWarnings("unchecked")
	private ModelMap getDistroLineList(HttpServletRequest request, SortInfo sort, String constituentReferenceCustomField, String pageNamePrefix) {

    	Long constituentId = new Long(request.getParameter(StringConstants.CONSTITUENT_ID));
        
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        long giftId = getNodeId(request);
        String unresolvedSortField = sort.getSort();
        int count = 0;
        if (giftId == 0) {
            List<SectionField> sectionFields = findSectionFields(pageNamePrefix+"List");
            resolveSortFieldName(sectionFields, sort);
            // Returns gifts with one and only one filtered matching distro line of interest (where custom field constituentReferenceCustomField = constituent id)
            // Multiple lines with same gift id may be returned since this is by distro line id, not gift id.
            List<Gift> gifts = giftService.readGiftDistroLinesByConstituentId(constituentId, constituentReferenceCustomField, sort, request.getLocale()); 
            addListFieldsToMap(request, sectionFields, getDistroLineList(gifts), list, true);

            Map<Long,Long> adjustGiftCountMap = new HashMap<Long,Long>(); //adjustedGiftService.countAdjustedGiftDistroLinesByOriginalGiftId(gifts, constituentReferenceCustomField);
            setParentNodeAttributes(list, adjustGiftCountMap, StringConstants.GIFT);
            count = giftService.readGiftDistroLinesCountByConstituentId(constituentId, constituentReferenceCustomField);
        }
        else {
//            List<SectionField> sectionFields = findSectionFields(pageNamePrefix+"AdjustmentList");
//            resolveSortFieldName(sectionFields, sort);
//            List<AdjustedGift> adjustedGifts = adjustedGiftService.readAdjustedGiftDistroLinesByConstituentId(constituentId, constituentReferenceCustomField, giftId, sort, request.getLocale());
//            addListFieldsToMap(request, sectionFields, adjustedGifts, list, true);
//            setChildNodeAttributes(list, giftId, StringConstants.GIFT, StringConstants.ADJUSTED_GIFT);
//            count = adjustedGiftService.readAdjustedGiftDistroLinesCountByConstituentGiftId(constituentId, constituentReferenceCustomField, giftId);
        }

        sort.setSort(unresolvedSortField);
        ModelMap map = new ModelMap("rows", list);
        map.put("totalRows", count);
        map.put("success", Boolean.TRUE);
        return map;
    }
    
    // These gifts only have one distro line each.
    private List<DistributionLine> getDistroLineList(List<Gift> gifts) {
    	List<DistributionLine> result = new ArrayList<DistributionLine>();
    	for (Gift g: gifts) {
    		if (g.getDistributionLines() != null && g.getDistributionLines().size() > 0) {
	    		DistributionLine dl = g.getDistributionLines().get(0);
	    		dl.setGift(g);
	    		result.add(dl);
    		}    		
    	}
    	return result;
    }
}
