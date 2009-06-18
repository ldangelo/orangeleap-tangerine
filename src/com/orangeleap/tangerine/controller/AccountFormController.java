package com.orangeleap.tangerine.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.orangeleap.tangerine.domain.PasswordChange;
import com.orangeleap.tangerine.service.ldap.LdapService;

public class AccountFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    private LdapService ldapService;

    public void setLdapService(LdapService ldapService) {
        this.ldapService = ldapService;
    }

    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        logger.info("**** in formBackingObject");
        return new PasswordChange();
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        logger.info("**** in onSubmit");
        PasswordChange pwChange = (PasswordChange) command;
        ldapService.changePassword(pwChange.getCurrentPassword(), pwChange.getNewPassword());
        // TODO: Adding errors.getModel() to our ModelAndView is a "hack" to allow our
        // form to post results back to the same page. We need to get the
        // command from errors and then add our search results to the model.
        ModelAndView mav = new ModelAndView(getSuccessView(), errors.getModel());
        mav.addObject("saved", true);
        return mav;
    }
}
