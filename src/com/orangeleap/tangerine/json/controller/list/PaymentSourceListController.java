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

import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.service.PaymentSourceService;
import com.orangeleap.tangerine.service.customization.PageCustomizationService;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.util.TangerinePagedListHolder;
import com.orangeleap.tangerine.util.StringConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.support.MutableSortDefinition;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;

@Controller
public class PaymentSourceListController {

    @Resource(name = "paymentSourceService")
    private PaymentSourceService paymentSourceService;

    @Resource(name = "pageCustomizationService")
    private PageCustomizationService pageCustomizationService;

    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;

    @SuppressWarnings("unchecked")
    @RequestMapping("/paymentSourceList.json")
    public ModelMap listPaymentSources(HttpServletRequest request) {

//        List<SectionDefinition> sectionDefs = pageCustomizationService.readSectionDefinitionsByPageTypeRoles(PageType.PaymentSourceList, tangerineUserHelper.lookupUserRoles());
//        if (sectionDefs != null) {
//            SectionDefinition sectionDef = sectionDefs.get(0); // Should only be 1
//            List<SectionField> sectionFields = pageCustomizationService.readSectionFieldsBySection(sectionDef);
//            for (SectionField thisField : sectionFields) {
//
//            }
//        }

        List<Map> list = new ArrayList<Map>();
        String constituentId = request.getParameter("constituentId");
        String sortField = request.getParameter("sort");
        boolean isAsc = "ASC".equals(request.getParameter("dir"));

        List<PaymentSource> sources = paymentSourceService.readAllPaymentSourcesACHCreditCard(Long.parseLong(constituentId));

        for (PaymentSource paymentSource : sources) {
            list.add(paymentSourceToMap(paymentSource, constituentId));
        }
        int count = sources.size();

        MutableSortDefinition sortDef = new MutableSortDefinition(sortField, true, isAsc);
        TangerinePagedListHolder pagedListHolder = new TangerinePagedListHolder(list, sortDef);
        pagedListHolder.resort();

        ModelMap map = new ModelMap("rows", list);
        map.put("totalRows", count);

        return map;
    }

    private Map<String, Object> paymentSourceToMap(PaymentSource paymentSource, String constituentId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", paymentSource.getId());
        map.put("constituentId", constituentId);
        map.put("paymentProfile", paymentSource.getProfile());
        map.put("paymentType", paymentSource.getPaymentType());
        if (PaymentSource.ACH.equals(paymentSource.getPaymentType())) {
            map.put("holderName", paymentSource.getAchHolderName());
            map.put("routingNumber", paymentSource.getAchRoutingNumberDisplay());
        }
        else if (PaymentSource.CREDIT_CARD.equals(paymentSource.getPaymentType())) {
            map.put("holderName", paymentSource.getCreditCardHolderName());
            map.put("creditCardType", paymentSource.getCreditCardType());
            SimpleDateFormat sdf = new SimpleDateFormat(StringConstants.CREDIT_CARD_EXP_DISPLAY_FORMAT);
            map.put("creditCardExpiration", sdf.format(paymentSource.getCreditCardExpiration()));
        }
        map.put("lastFourDigits", paymentSource.getLastFourDigits());
        map.put("active", !paymentSource.isInactive());

        return map;
    }
}