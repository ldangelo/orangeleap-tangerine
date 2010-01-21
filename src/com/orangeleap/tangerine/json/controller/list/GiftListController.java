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

import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.AdjustedGiftService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.common.SortInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class GiftListController extends TangerineJsonListController {

    @Resource(name = "giftService")
    private GiftService giftService;

    @Resource(name = "adjustedGiftService")
    private AdjustedGiftService adjustedGiftService;

    @SuppressWarnings("unchecked")
    @RequestMapping("/giftList.json")
    public ModelMap getGiftList(HttpServletRequest request, SortInfo sort) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Long constituentId = new Long(request.getParameter(StringConstants.CONSTITUENT_ID));

        long giftId = getNodeId(request);
        String unresolvedSortField = sort.getSort();
        int count;
        if (giftId == 0) {
            List<SectionField> sectionFields = findSectionFields("giftList");
            resolveSortFieldName(sectionFields, sort);
            List<Gift> gifts = giftService.readAllGiftsByConstituentId(constituentId, sort, request.getLocale());
            addListFieldsToMap(request, sectionFields, gifts, list, true, false);

            Map<Long,Long> adjustGiftCountMap = adjustedGiftService.countAdjustedGiftsByOriginalGiftId(gifts);
            setParentNodeAttributes(list, adjustGiftCountMap, StringConstants.GIFT, false);
            count = giftService.readCountByConstituentId(constituentId);
        }
        else {
            List<SectionField> sectionFields = findSectionFields("adjustedGiftList");
            resolveSortFieldName(sectionFields, sort);
            List<AdjustedGift> adjustedGifts = adjustedGiftService.readAllAdjustedGiftsByConstituentGiftId(constituentId, giftId, sort, request.getLocale());
            addListFieldsToMap(request, sectionFields, adjustedGifts, list, true, false);
            setChildNodeAttributes(list, giftId, StringConstants.GIFT, StringConstants.ADJUSTED_GIFT, false);
            count = adjustedGiftService.readCountByConstituentGiftId(constituentId, giftId);
        }

        sort.setSort(unresolvedSortField);
        ModelMap map = new ModelMap("rows", list);
        map.put("totalRows", count);
        map.put("success", Boolean.TRUE);
        return map;
    }
}
