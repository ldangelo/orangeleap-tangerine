package com.orangeleap.tangerine.json.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.service.ConstituentService;

/**
 * Retrieves the JSON data used for populating the constituent
 * contact information accordion panel. Also used for updating
 * constituent information.
 * The keys in the Maps match to the values expected in the
 * JavaScript code, so don't change them without updating the
 * Readers.
 * @version 1.0
 */
@Controller
public class ContactInfoController {

    @Resource(name="constituentService")
    private ConstituentService constituentService;

    @SuppressWarnings("unchecked")
    @RequestMapping("/addresses.json")
    public ModelMap getMailingAddresses(long id) {

        List<Map> response = new ArrayList<Map>();

        Person constituent = constituentService.readConstituentById(id);
        if (constituent == null) {
            return new ModelMap();
        }

        List<Address> addresses = constituent.getAddresses();

        for(Address addr : addresses) {

            Map<String, Object> map = new HashMap<String,Object>();
            map.put("id", addr.getId());
            map.put("street1", addr.getAddressLine1());
            map.put("street2", addr.getAddressLine2());
            map.put("city", addr.getCity());
            map.put("state", addr.getStateProvince());
            map.put("zip", addr.getPostalCode());
            map.put("comment", (addr.getComments() == null ? "None":addr.getComments()) );
            map.put("active", !addr.isInactive());
            map.put("primary", false); // not defined yet in domain, so default to false
            response.add(map);
        }

        return new ModelMap("data", response);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/phones.json")
    public ModelMap getPhoneNumbers(long id) {

        List<Map> response = new ArrayList<Map>();

        Person person = constituentService.readConstituentById(id);
        if (person == null) {
            return new ModelMap();
        }

        List<Phone> phones = person.getPhones();

        for(Phone phone : phones) {

            Map<String,Object> map = new HashMap<String,Object>();
            map.put("id", phone.getId());
            map.put("type", phone.getPhoneType());
            map.put("number", phone.getNumber());
            map.put("comment", (phone.getComments() == null ? "None" : phone.getComments()));
            map.put("active", !phone.isInactive());
            map.put("primary", false); // not defined yet in domain, so default to false
            response.add(map);
        }

        return new ModelMap("data", response);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/emails.json")
    public ModelMap getEmailAddresses(long id) {

        List<Map> response = new ArrayList<Map>();

        Person person = constituentService.readConstituentById(id);
        if (person == null) {
            return new ModelMap();
        }

        List<Email> emails = person.getEmails();

        for(Email email : emails) {

            Map<String,Object> map = new HashMap<String,Object>();
            map.put("id", email.getId());
            map.put("type", email.getEmailType());
            map.put("address", email.getEmailAddress());
            map.put("comment", (email.getComments() == null ? "None" : email.getComments()));
            map.put("active", !email.isInactive());
            map.put("primary", false); // not defined yet in domain, so default to false
            response.add(map);
        }

        return new ModelMap("data", response);
    }



}
