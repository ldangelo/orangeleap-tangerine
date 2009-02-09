package com.mpower.json.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mpower.domain.Gift;
import com.mpower.domain.Person;
import com.mpower.security.MpowerAuthenticationToken;
import com.mpower.service.GiftService;
import com.mpower.service.PersonService;
import com.mpower.service.SiteService;

/**
 * Controller used by the sidebar to get the accounts for
 * which the current user is the Account Manager
 * @version 1.0
 */
@Controller
public class MyAccountsController {

    @Resource(name="siteService")
    private SiteService siteService;

    @Resource(name="personService")
    private PersonService personService;

    @Resource(name="giftService")
    private GiftService giftService;

    @SuppressWarnings("unchecked")
	@RequestMapping("/myAccounts.json")
    public ModelMap getAllAccounts(HttpSession session) {

        List<Map> response = new ArrayList<Map>();

        MpowerAuthenticationToken mPowerAuthenticationToken = (MpowerAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        siteService.createSiteAndUserIfNotExist(mPowerAuthenticationToken.getSite());
        Person person = personService.readPersonById(mPowerAuthenticationToken.getPersonId());
        if (person == null) return new ModelMap();
        
        String acctString = person.getCustomFieldValue("individual.accountManagerFor");

        if (acctString != null) {
            String[] accounts = acctString.split(",");

            for (String account : accounts) {

                Long acctId = Long.parseLong(account);
                Person client = personService.readPersonById(acctId);

                BigDecimal totalGiving = new BigDecimal(0);

                List<Gift> giftList = giftService.readGifts(client.getId());
                for (Gift gft : giftList) {
                    totalGiving = totalGiving.add(gft.getAmount() == null ? BigDecimal.ZERO : gft.getAmount());
                }


                response.add(fromPerson(client, totalGiving, giftList.size()));
            }
        }

        ModelMap model = new ModelMap("data", response);

        return model;
    }

    private Map fromPerson(Person person, BigDecimal amount, int gifts) {

        Map<String,Object> ret = new HashMap<String,Object>();
        ret.put("id", person.getId());
        ret.put("first", person.getFirstName());
        ret.put("last", person.getLastName());
        ret.put("orgName", person.getOrganizationName());
        ret.put("majorDonor", person.isMajorDonor());
        ret.put("lapsedDonor", person.isLapsedDonor());
        ret.put("amount", amount);
        ret.put("gifts", gifts);
        return ret;
    }


}
