package com.orangeleap.tangerine.controller.importexport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

public class ImportExportController extends ParameterizableViewController {

    protected final Log logger = OLLogger.getLog(getClass());

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView(super.getViewName());
        return mav;
    }
}
