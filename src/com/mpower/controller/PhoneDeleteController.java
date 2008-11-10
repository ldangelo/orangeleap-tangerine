package com.mpower.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.mpower.domain.Phone;
import com.mpower.service.PhoneService;

public class PhoneDeleteController implements Controller {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PhoneService phoneService;

    public void setPhoneService(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String phoneId = request.getParameter("phoneId");
        String personId = request.getParameter("personId");
        Phone phone = phoneService.readPhone(Long.valueOf(phoneId));
        phone.setInactive(true);
        phoneService.savePhone(phone);
        ModelAndView mav = new ModelAndView("redirect:/phoneManager.htm?personId=" + personId);
        return mav;
    }
}
