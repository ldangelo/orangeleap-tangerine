package com.mpower.json.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.annotation.Resource;

import com.mpower.service.PersonService;
import com.mpower.domain.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Controller used by the sidebar to get the accounts for
 * which the current user is the Account Manager
 * @version 1.0
 */
@Controller
public class MyAccountsController {

    @Resource(name="personService")
    private PersonService personService;

    @RequestMapping("/myAccounts.json")
    public ModelMap getAllAccounts(HttpSession session) {

        List<Map> response = new ArrayList<Map>();

        //normally would retreive the ID from the DB, but for demo, assume
        //we're adam smith
        Person person = personService.readPersonById(1L);
        String acctString = person.getCustomFieldValue("individual.accountManagerFor");

        if (acctString != null) {
            String[] accounts = acctString.split(",");

            for (String account : accounts) {

                Long acctId = Long.parseLong(account);
                Person client = personService.readPersonById(acctId);
                response.add(fromPerson(client));
            }
        }

        ModelMap model = new ModelMap("data", response);

        return model;
    }

    private Map fromPerson(Person person) {

        Map<String,Object> ret = new HashMap<String,Object>();
        ret.put("id", person.getId());
        ret.put("first", person.getFirstName());
        ret.put("last", person.getLastName());
        ret.put("majorDonor", person.isMajorDonor());
        ret.put("lapsedDonor", person.isLapsedDonor());
        return ret;
    }


}
