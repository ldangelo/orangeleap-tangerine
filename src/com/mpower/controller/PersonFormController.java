package com.mpower.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.mpower.domain.entity.Person;
import com.mpower.service.PersonService;

public class PersonFormController extends SimpleFormController {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	private PersonService personService;

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		String personId = request.getParameter("personId");

		Person person = null;

		if (personId == null) {
			// create person
			// TODO: get current user's site
			person = personService.createDefaultPerson(1L);
		} else {
			// lookup person
			person = personService.readPersonById(new Long(personId));
		}

		return person;
	}

	public ModelAndView onSubmit(Object command, BindException errors)
			throws ServletException {

		Person p = (Person) command;

		logger.info("**** p's first name is: " + p.getFirstName());

		Person current = personService.maintainPerson(p);

		ModelAndView mav = new ModelAndView(getSuccessView(), errors.getModel());
		mav.addObject("saved", true);
		mav.addObject("id", current.getId());
		return mav;
	}
}
