package com.mpower.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.mpower.domain.Email;
import com.mpower.domain.Person;
import com.mpower.service.EmailService;
import com.mpower.service.PersonService;
import com.mpower.service.SessionServiceImpl;
import com.mpower.service.SiteService;
import com.mpower.type.PageType;

public class EmailManagerEditFormController extends SimpleFormController {

    private EmailService emailService;

    private PersonService personService;

    private SiteService siteService;

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public void setSiteService(SiteService siteService) {
        this.siteService = siteService;
    }

    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map refData = new HashMap();
        String personId = request.getParameter("personId");
        refData.put("person", personService.readPersonById(Long.valueOf(personId)));
        return refData;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        String personId = request.getParameter("personId");
        String emailId = request.getParameter("emailId");
        Email email = null;
        if (emailId == null) {
            Person person = personService.readPersonById(Long.valueOf(personId));
            email = new Email();
            email.setPerson(person);
        } else {
            Email originalEmail = emailService.readEmail(Long.valueOf(emailId));
            try {
                email = (Email) BeanUtils.cloneBean(originalEmail);
                email.setId(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (isFormSubmission(request)) {
            Map<String, String> fieldLabelMap = siteService.readFieldLabels(SessionServiceImpl.lookupUserSiteName(), PageType.valueOf(getCommandName()), SessionServiceImpl.lookupUserRoles(), request.getLocale());
            email.setFieldLabelMap(fieldLabelMap);

            Map<String, Object> valueMap = siteService.readFieldValues(SessionServiceImpl.lookupUserSiteName(), PageType.valueOf(getCommandName()), SessionServiceImpl.lookupUserRoles(), email);
            email.setFieldValueMap(valueMap);
        }
        return email;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Email email = (Email) command;
        emailService.saveEmail(email);
        String personIdString = request.getParameter("personId");
        Long personId = Long.valueOf(personIdString);
        List<Email> emails = emailService.readEmails(personId);
        Person person = personService.readPersonById(personId);
        ModelAndView mav = new ModelAndView("emailManager");
        mav.addObject("emails", emails);
        List<Email> currentEmails = emailService.readCurrentEmails(personId, Calendar.getInstance(), false);
        mav.addObject("currentEmails", currentEmails);
        List<Email> currentCorrespondenceEmails = emailService.readCurrentEmails(personId, Calendar.getInstance(), true);
        mav.addObject("currentCorrespondenceEmails", currentCorrespondenceEmails);
        email = new Email();
        email.setPerson(person);
        mav.addObject("redirect:/emailManager.htm?personId=" + personIdString, errors.getModel());
        mav.addObject("person", person);
        mav.addObject("email", email);
        mav.addObject(personIdString);
        return mav;
    }
}
