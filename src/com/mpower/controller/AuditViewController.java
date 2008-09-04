package com.mpower.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.mpower.domain.Audit;
import com.mpower.service.AuditService;
import com.mpower.service.SessionServiceImpl;

public class AuditViewController implements Controller {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	private AuditService auditService;

	public void setAuditService(AuditService auditService) {
		this.auditService = auditService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Audit> audits;
		audits = auditService.allAuditHistoryForSite(SessionServiceImpl.lookupUserSiteName());

		ModelAndView mav = new ModelAndView("siteAudit");
		mav.addObject("audits", audits);
		return mav;
	}

}
