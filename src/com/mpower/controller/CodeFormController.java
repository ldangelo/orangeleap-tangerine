package com.mpower.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.mpower.domain.customization.Code;
import com.mpower.service.CodeService;
import com.mpower.service.SessionService;
import com.mpower.service.SessionServiceImpl;

public class CodeFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private CodeService codeService;

    private SessionService sessionService;

    public void setCodeService(CodeService codeService) {
        this.codeService = codeService;
    }

    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        String codeId = request.getParameter("codeId");
        String codeType = request.getParameter("codeType");
        if (codeId == null) {
            Code code = new Code();
            code.setType(codeService.readCodeType(codeType,SessionServiceImpl.lookupUserSiteName()));
            //if (isFormSubmission(request)) {
            //    code.setSite(sessionService.lookupSite());
            //}
            return code;
        } else {
            return codeService.readCodeById(new Long(codeId));
        }
    }

    @Override
    public ModelAndView onSubmit(Object command, BindException errors) throws ServletException {
        Code code = (Code) command;
        code.setType(codeService.readCodeType(code.getCodeType(),SessionServiceImpl.lookupUserSiteName()));
        Code newCode = codeService.maintainCode(code);

        ModelAndView mav = new ModelAndView(getSuccessView());
        mav.addObject("code", newCode);
        return mav;
    }
}
