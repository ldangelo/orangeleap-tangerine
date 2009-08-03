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
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.service.EmailService;
import com.orangeleap.tangerine.service.customization.PageCustomizationService;
import com.orangeleap.tangerine.web.common.SortInfo;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.util.TangerinePagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.support.MutableSortDefinition;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmailListController {

    @Resource(name = "emailService")
    private EmailService emailService;

    @Resource(name = "pageCustomizationService")
    private PageCustomizationService pageCustomizationService;

    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;

    @SuppressWarnings("unchecked")
    @RequestMapping("/emailList.json")
    public ModelMap listEmails(HttpServletRequest request) {

//        List<SectionDefinition> sectionDefs = pageCustomizationService.readSectionDefinitionsByPageTypeRoles(PageType.emailList, tangerineUserHelper.lookupUserRoles());
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

        List<Email> emails = emailService.readByConstituentId(Long.parseLong(constituentId));

        for (Email email : emails) {
            list.add(emailToMap(email, constituentId));
        }
        int count = emails.size();

        MutableSortDefinition sortDef = new MutableSortDefinition(sortField, true, isAsc);
        TangerinePagedListHolder pagedListHolder = new TangerinePagedListHolder(list, sortDef);
        pagedListHolder.resort();

        ModelMap map = new ModelMap("rows", list);
        map.put("totalRows", count);

        return map;
    }

    private Map<String, Object> emailToMap(Email email, String constituentId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", email.getId());
        map.put("constituentId", constituentId);
        map.put("emailAddress", email.getEmailAddress());
        map.put("emailDisplay", email.getEmailDisplay());
        map.put("receiveCorrespondence", email.isReceiveCorrespondence());
        map.put("current", emailService.isCurrent(email));
        map.put("primary", email.isPrimary());
        map.put("active", !email.isInactive());

        return map;
    }
}