package com.mpower.controller.constituent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.mpower.controller.TangerineFormController;
import com.mpower.domain.model.AbstractEntity;
import com.mpower.domain.model.Person;
import com.mpower.service.exception.PersonValidationException;
import com.mpower.util.StringConstants;

public class PersonFormController extends TangerineFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
        binder.registerCustomEditor(Person.class, new PersonEditor());
    }

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        String personId = super.getConstituentIdString(request);
        if (personId == null) {
            personId = request.getParameter("id");
        }
        Person person = null;
        if (personId == null) {
            person = personService.createDefaultConstituent();
        } 
        else {
            person = personService.readConstituentById(new Long(personId));
        }
        return person;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Person p = (Person) command;
        if (logger.isDebugEnabled()) {
            logger.debug("onSubmit: person = " + p.getFullName());
        }
        
        boolean saved = true;
        Person current = null;
        try {
            current = personService.maintainConstituent(p);
        } 
        catch (PersonValidationException e) {
            saved = false;
        	current = p;
            e.createMessages(errors);
        }

        ModelAndView mav = null;
        if (saved) {
            mav = new ModelAndView(super.appendSaved(new StringBuilder(super.getSuccessView()).append("?").append(StringConstants.PERSON_ID).append("=").append(current.getId()).toString()));
        }
        else {
            mav = super.onSubmit(command, errors);
            mav.setViewName(super.getFormView());
            mav.addObject("person", current);
        }
        return mav;
    }
}
