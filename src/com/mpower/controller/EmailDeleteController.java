package com.mpower.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.mpower.domain.Email;
import com.mpower.service.EmailService;

public class EmailDeleteController implements Controller {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private EmailService emailService;

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String emailId = request.getParameter("emailId");
        String personId = request.getParameter("personId");
        Email email = emailService.readEmail(Long.valueOf(emailId));
        email.setInactive(true);
        emailService.saveEmail(email);
        ModelAndView mav = new ModelAndView("redirect:/emailManager.htm?personId=" + personId);
        return mav;
    }
}
