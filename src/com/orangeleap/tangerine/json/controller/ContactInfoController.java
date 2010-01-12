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
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.util.HttpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Retrieves the JSON data used for populating the constituent
 * contact information accordion panel. Also used for updating
 * constituent information.
 * The keys in the Maps match to the values expected in the
 * JavaScript code, so don't change them without updating the
 * Readers.
 *
 * @version 1.0
 */
@Controller
public class ContactInfoController {

    @Resource(name = "constituentService")
    private ConstituentService constituentService;

    @SuppressWarnings("unchecked")
    @RequestMapping("/addresses.json")
    public ModelMap getMailingAddresses(long id) {

        List<Map> response = new ArrayList<Map>();

        Constituent constituent = constituentService.readConstituentById(id);
        if (constituent == null) {
            return new ModelMap();
        }

        List<Address> addresses = constituent.getAddresses();

        for (Address addr : addresses) {

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", addr.getId());
            map.put("street1", addr.getAddressLine1());
            map.put("street2", addr.getAddressLine2());
            map.put("city", addr.getCity());
            map.put("state", addr.getStateProvince());
            map.put("zip", addr.getPostalCode());
            map.put("comment", (addr.getComments() == null ? "None" : addr.getComments()));
            map.put("active", !addr.isInactive());
            map.put("primary", addr.isPrimary());
            response.add(map);
        }

        return new ModelMap("data", response);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/phones.json")
    public ModelMap getPhoneNumbers(long id) {

        List<Map> response = new ArrayList<Map>();

        Constituent constituent = constituentService.readConstituentById(id);
        if (constituent == null) {
            return new ModelMap();
        }

        List<Phone> phones = constituent.getPhones();

        for (Phone phone : phones) {

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", phone.getId());
            map.put("number", phone.getNumber());
            map.put("comment", (phone.getComments() == null ? "None" : phone.getComments()));
            map.put("active", !phone.isInactive());
            map.put("primary", phone.isPrimary());
            map.put("type", phone.getCustomFieldValue("phoneType"));

            response.add(map);
        }

        return new ModelMap("data", response);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/emails.json")
    public ModelMap getEmailAddresses(long id) {

        List<Map> response = new ArrayList<Map>();

        Constituent constituent = constituentService.readConstituentById(id);
        if (constituent == null) {
            return new ModelMap();
        }

        List<Email> emails = constituent.getEmails();

        for (Email email : emails) {

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", email.getId());
            map.put("address", email.getEmailAddress());
            map.put("comment", (email.getComments() == null ? "None" : HttpUtil.escapeDoubleQuoteReturns(email.getComments())));
            map.put("active", !email.isInactive());
            map.put("primary", email.isPrimary());
            map.put("type", email.getCustomFieldValue("emailType"));

            response.add(map);
        }

        return new ModelMap("data", response);
    }


}
