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
import java.util.HashMap;
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

//        List<Map> list = new ArrayList<Map>();
//        String constituentId = request.getParameter("constituentId");
//        String sortField = request.getParameter("sort");
//        boolean isAsc = "ASC".equals(request.getParameter("dir"));
//
//        List<Address> addresses = addressService.readByConstituentId(Long.parseLong(constituentId));
//
//        for (Address address : addresses) {
//            list.add(addressToMap(address, constituentId));
//        }
//        int count = addresses.size();
//
//        MutableSortDefinition sortDef = new MutableSortDefinition(sortField, true, isAsc);
//        TangerinePagedListHolder pagedListHolder = new TangerinePagedListHolder(list, sortDef);
//        pagedListHolder.resort();

        ModelMap map = new ModelMap("rows", list);
        map.put("totalRows", count);

        return map;
    }

    private Map<String, Object> addressToMap(Address address, String constituentId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", address.getId());
        map.put("constituentId", constituentId);
        map.put("addressLine1", address.getAddressLine1());
        map.put("city", address.getCity());
        map.put("stateProvince", address.getStateProvince());
        map.put("postalCode", address.getPostalCode());
        map.put("country", address.getCountry());
        map.put("receiveCorrespondence", address.isReceiveCorrespondence());
        map.put("current", addressService.isCurrent(address));
        map.put("primary", address.isPrimary());
        map.put("active", !address.isInactive());

        return map;
    }
}