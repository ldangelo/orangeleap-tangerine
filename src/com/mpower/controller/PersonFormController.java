package com.mpower.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.mpower.domain.Person;
import com.mpower.service.PersonService;
import com.mpower.service.SessionServiceImpl;
import com.mpower.service.SiteService;
import com.mpower.service.exception.PersonValidationException;
import com.mpower.type.EntityType;

public class PersonFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PersonService personService;

    private SiteService siteService;

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public void setSiteService(SiteService siteService) {
        this.siteService = siteService;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        String personId = request.getParameter("personId");
        Person person = null;
        if (personId == null) {
            person = personService.createDefaultPerson(SessionServiceImpl.lookupUserSiteName());
        } else {
            person = personService.readPersonById(new Long(personId));
        }
        person.setRequiredFieldMap(siteService.readRequiredFields(SessionServiceImpl.lookupUserSiteName(), EntityType.person));
        person.setValidationMap(siteService.readValidations(SessionServiceImpl.lookupUserSiteName(), EntityType.person));

        return person;
    }

    @Override
    public ModelAndView onSubmit(Object command, BindException errors) throws ServletException {
        Person p = (Person) command;
        logger.info("**** p's first name is: " + p.getFirstName());
        Person current = null;
        try {
            current = personService.maintainPerson(p);
        } catch (PersonValidationException e) {
            e.createMessages(errors);
        }

        // TODO: Adding errors.getModel() to our ModelAndView is a "hack" to allow our
        // form to post results back to the same page. We need to get the
        // command from errors and then add our search results to the model.
        ModelAndView mav = new ModelAndView(getSuccessView(), errors.getModel());
        mav.addObject("saved", true);
        mav.addObject("id", current.getId());
        return mav;
    }
}
