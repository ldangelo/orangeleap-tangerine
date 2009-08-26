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

import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.service.AddressService;
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
public class AddressListController extends TangerineJsonListController {

    @Resource(name = "addressService")
    private AddressService addressService;

    @SuppressWarnings("unchecked")
    @RequestMapping("/addressList.json")
    public ModelMap listAddresses(HttpServletRequest request, SortInfo sort) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Long constituentId = new Long(request.getParameter(StringConstants.CONSTITUENT_ID));
        List<Address> addresses = addressService.readAllAddressesByConstituentId(constituentId, sort, request.getLocale());
        List<SectionField> sectionFields = findSectionFields("addressList");
        addListFieldsToMap(request, sectionFields, addresses, list);

        int count = addressService.readCountByConstituentId(constituentId);

        ModelMap map = new ModelMap("rows", list);
        map.put("totalRows", count);

        return map;
    }
}