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

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller used by the sidebar to get the accounts for
 * which the current user is the Account Manager
 * @version 1.0
 */
@Controller
public class MyAccountsController {

    @Resource(name="siteService")
    private SiteService siteService;

    @Resource(name="constituentService")
    private ConstituentService constituentService;

    @Resource(name="giftService")
    private GiftService giftService;
    
    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;

    @SuppressWarnings("unchecked")
	@RequestMapping("/myAccounts.json")
    public ModelMap getAllAccounts(HttpSession session) {

        List<Map> response = new ArrayList<Map>();

        Constituent constituent = constituentService.readConstituentById(tangerineUserHelper.lookupUserId());
        if (constituent == null) {
            return new ModelMap();
        }
        
        String acctString = constituent.getCustomFieldValue("individual.accountManagerFor");

        if (acctString != null) {
            String[] accounts = acctString.split(StringConstants.CUSTOM_FIELD_SEPARATOR);

            for (String account : accounts) {

                Long acctId = Long.parseLong(account);
                Constituent client = constituentService.readConstituentById(acctId);
                Map<String, Object> map = giftService.readNumGiftsTotalAmount(client.getId());

                response.add(fromConstituent(client, (BigDecimal) map.get(StringConstants.AMOUNT), (Integer) map.get("giftCount")));
            }
        }

        return new ModelMap("data", response);
    }

    @SuppressWarnings("unchecked")
    private Map fromConstituent(Constituent constituent, BigDecimal amount, int gifts) {

        Map<String,Object> ret = new HashMap<String,Object>();
        ret.put("id", constituent.getId());
        ret.put("accountNumber", constituent.getAccountNumber());
        ret.put("first", constituent.getFirstName());
        ret.put("last", constituent.getLastName());
        ret.put("orgName", constituent.getOrganizationName());
        ret.put("majorDonor", constituent.isMajorDonor());
        ret.put("lapsedDonor", constituent.isLapsedDonor());
        ret.put("amount", amount);
        ret.put("gifts", gifts);
        return ret;
    }


}
