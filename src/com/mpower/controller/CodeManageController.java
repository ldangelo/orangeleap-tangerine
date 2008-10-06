package com.mpower.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.mpower.service.CodeService;
import com.mpower.service.SessionServiceImpl;

public class CodeManageController implements Controller {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());
	
    private CodeService codeService;

    public void setCodeService(CodeService codeService) {
        this.codeService = codeService;
    }

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("codes");
    	List<String> codeTypes = codeService.listCodeTypes(SessionServiceImpl.lookupUserSiteName());
    	mav.addObject("codeTypes", codeTypes);
		return mav;
	}

}
