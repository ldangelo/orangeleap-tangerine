package com.mpower.controller.importexport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.mpower.service.SiteService;

public class ImportExportController extends ParameterizableViewController {

    protected final Log logger = LogFactory.getLog(getClass());

	
	private SiteService siteService;

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView(super.getViewName());
        return mav;
    }
}
