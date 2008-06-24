package com.mpower.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.mpower.entity.Person;
import com.mpower.entity.User;
import com.mpower.service.PersonService;
import com.mpower.web.common.SessionUtils;

public class PersonSearchFormController extends SimpleFormController {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	private PersonService personService;

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		logger.info("**** in formBackingObj");

		Person p = new Person();	
		User user = SessionUtils.lookupUser(request);
		p.setSite(user.getSite());
		return p;
	}

	public ModelAndView onSubmit(Object command, BindException errors)
			throws ServletException {

		logger.info("**** in onSubmit()");

		Map<String, String> params = new HashMap<String, String>();
		Person p = (Person) command;
		
		params.put("lastName", p.getLastName());
		params.put("firstName", p.getFirstName());
        params.put("middleName", p.getMiddleName());
        params.put("email", p.getEmail());
        params.put("organizationName", p.getOrganizationName());

		System.out.println("*** map has: " + params);

		List<Person> personList = personService.readPersons(1l, params);
		System.out.println("**** list size: " + personList.size());
		System.out.println("**** Person List" + personList);

		// Adding errors.getModel() to our ModelAndView is a "hack" to allow our
		// form to post results back to the same page.  We need to get the
		// command from errors and then add our search results to the model.
		ModelAndView mav = new ModelAndView(getSuccessView(), errors.getModel());
		mav.addObject("personList", personList);
		mav.addObject("personListSize", personList.size());
		return mav;
	}
}
