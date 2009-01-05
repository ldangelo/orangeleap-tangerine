package com.mpower.controller.code;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.mpower.service.CodeService;
import com.mpower.service.impl.SessionServiceImpl;

public class CodeManageController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private CodeService codeService;

    public void setCodeService(CodeService codeService) {
        this.codeService = codeService;
    }

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView(super.getViewName());
        List<String> codeTypes = codeService.listCodeTypes(SessionServiceImpl.lookupUserSiteName());
        mav.addObject("codeTypes", codeTypes);
        return mav;
    }

}
