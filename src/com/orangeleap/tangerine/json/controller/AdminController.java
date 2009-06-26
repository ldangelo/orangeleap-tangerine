package com.orangeleap.tangerine.json.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.orangeleap.tangerine.service.AdminService;

import javax.annotation.Resource;

/**
 * Controller used for JSON requests oriented around user maintenance
 * (change password, etc...)
 */
@Controller
public class AdminController {

    @Resource(name="adminService")
    private AdminService adminService;

    @SuppressWarnings("unchecked")
    @RequestMapping("/changePassword.json")
    public ModelMap changePassword(String password) {

        ModelMap ret = new ModelMap();

        if(password == null || password.trim().length() == 0) {
            ret.put("success",false);
            ret.put("error","New Password cannot be blank");
            return ret;
        }

        try {
            adminService.setPassword(password);
            ret.put("success",true);
        } catch(Exception ex) {
            ret.put("success", false);
            ret.put("error", ex.getMessage());
        }

        return ret;
    }

}
