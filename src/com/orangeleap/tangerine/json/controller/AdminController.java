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

import com.orangeleap.tangerine.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Controller used for JSON requests oriented around user maintenance
 * (change password, etc...)
 */
@Controller
public class AdminController {

    @Resource(name = "adminService")
    private AdminService adminService;

    @SuppressWarnings("unchecked")
    @RequestMapping("/changePassword.json")
    public ModelMap changePassword(String password) {

        ModelMap ret = new ModelMap();

        if (password == null || password.trim().length() == 0) {
            ret.put("success", false);
            ret.put("error", "New Password cannot be blank");
            return ret;
        }

        try {
            adminService.setPassword(password);
            ret.put("success", true);
        } catch (Exception ex) {
            ret.put("success", false);
            ret.put("error", ex.getMessage());
        }

        return ret;
    }

}
