package com.mpower.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.mpower.domain.customization.Code;
import com.mpower.service.CodeService;
import com.mpower.service.SessionServiceImpl;

public class CodeHelperController implements Controller {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	private CodeService codeService;

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String searchString = request.getParameter("q");
		Boolean showInactive;
		if (StringUtils.equalsIgnoreCase(request.getParameter("inactive"), "all")) {
			showInactive = null;
		} else {
			showInactive = Boolean.valueOf(request.getParameter("inactive"));
		}
		if (GenericValidator.isBlankOrNull(searchString)) {
			searchString = request.getParameter("value");
		}
		if (searchString == null) {
			searchString = "";
		}
		String description = request.getParameter("description");
		String codeType = request.getParameter("type");
		List<Code> codes;
		if (description != null) {
			codes = codeService.readCodes(SessionServiceImpl.lookupUserSiteName(), codeType, searchString, description,
					showInactive);
		} else {
			codes = codeService.readCodes(SessionServiceImpl.lookupUserSiteName(), codeType, searchString);
		}
		ModelAndView mav = new ModelAndView("codeHelper");
		mav.addObject("codes", codes);
		return mav;
	}

}
