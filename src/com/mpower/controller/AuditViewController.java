package com.mpower.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.mpower.domain.Audit;
import com.mpower.domain.Person;
import com.mpower.service.AuditService;
import com.mpower.service.PersonService;
import com.mpower.service.SessionServiceImpl;
import com.mpower.type.EntityType;

public class AuditViewController implements Controller {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	private AuditService auditService;

	private String viewName;

	public final void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public void setAuditService(AuditService auditService) {
		this.auditService = auditService;
	}

	private PersonService personService;

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String entityType = request.getParameter("object");
		String objectId = request.getParameter("id");
		List<Audit> audits = null;
		ModelAndView mav = new ModelAndView(viewName);
		if (GenericValidator.isBlankOrNull(entityType) || GenericValidator.isBlankOrNull(objectId)) {
			audits = auditService.allAuditHistoryForSite(SessionServiceImpl.lookupUserSiteName());
		} else {
			if (EntityType.valueOf(entityType) == EntityType.person) {
				Person person = personService.readPersonById(Long.valueOf(objectId));
				mav.addObject("person", person);
				audits = auditService.auditHistoryForPerson(person.getId());
			} else {
				audits = auditService.auditHistoryForEntity(SessionServiceImpl.lookupUserSiteName(), EntityType
						.valueOf(entityType), Long.valueOf(objectId));
			}
		}

		mav.addObject("audits", audits);

		return mav;
	}

}
