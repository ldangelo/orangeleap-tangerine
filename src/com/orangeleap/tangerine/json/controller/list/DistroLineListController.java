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
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
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

    @SuppressWarnings("unchecked")
    @RequestMapping("/distroLineList.json")
    public ModelMap getDistroLineList(HttpServletRequest request, SortInfo sort) {

    	Long constituentId = new Long(request.getParameter(StringConstants.CONSTITUENT_ID));
        String constituentReferenceCustomField = request.getParameter("constituentReferenceCustomField"); // e.g. null for hard gifts, or "onBehalfOf" for soft gifts, or other reference-type custom field on the distro line 
        String pageName = request.getParameter("pageName"); // pagename, e.g. "softGift" 

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        long giftId = getNodeId(request);
        String unresolvedSortField = sort.getSort();
        int count = 0;
        if (giftId == 0) {
            List<SectionField> sectionFields = findSectionFields(pageName+"List");
            resolveSortFieldName(sectionFields, sort);
            // Returns gifts with only one matching distro line of interest; may return multiple gifts with the same id but with different distro lines
            //List<Gift> gifts = giftService.readGiftDistroLinesByConstituentId(constituentId, constituentReferenceCustomField, sort, request.getLocale()); 
            //addListFieldsToMap(request, sectionFields, gifts, list, true);

            //Map<Long,Long> adjustGiftCountMap = adjustedGiftService.countAdjustedGiftDistroLinesByOriginalGiftId(gifts, constituentReferenceCustomField);
            //setParentNodeAttributes(list, adjustGiftCountMap, StringConstants.GIFT);
           // count = giftService.readGiftDistroLinesCountByConstituentId(constituentId, constituentReferenceCustomField);
        }
        else {
            List<SectionField> sectionFields = findSectionFields(pageName+"AdjustmentList");
            resolveSortFieldName(sectionFields, sort);
            //List<AdjustedGift> adjustedGifts = adjustedGiftService.readAdjustedGiftDistroLinesByConstituentId(constituentId, constituentReferenceCustomField, giftId, sort, request.getLocale());
            //addListFieldsToMap(request, sectionFields, adjustedGifts, list, true);
            setChildNodeAttributes(list, giftId, StringConstants.GIFT, StringConstants.ADJUSTED_GIFT);
            //count = adjustedGiftService.readAdjustedGiftDistroLinesCountByConstituentGiftId(constituentId, constituentReferenceCustomField, giftId);
        }

        sort.setSort(unresolvedSortField);
        ModelMap map = new ModelMap("rows", list);
        map.put("totalRows", count);
        map.put("success", Boolean.TRUE);
        return map;
    }
}
