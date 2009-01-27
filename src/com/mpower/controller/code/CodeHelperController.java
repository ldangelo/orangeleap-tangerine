package com.mpower.controller.code;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.mpower.domain.customization.Code;
import com.mpower.service.CodeService;
import com.mpower.service.impl.SessionServiceImpl;

public class CodeHelperController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    public static final String VIEW = "view";

    private CodeService codeService;
    private String tableView;
    private String autoCompleteView;
    private String resultsOnlyView;

    public void setCodeService(CodeService codeService) {
        this.codeService = codeService;
    }

    public void setTableView(String tableView) {
        this.tableView = tableView;
    }

    public void setAutoCompleteView(String autoCompleteView) {
        this.autoCompleteView = autoCompleteView;
    }

    public void setResultsOnlyView(String resultsOnlyView) {
        this.resultsOnlyView = resultsOnlyView;
    }

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String searchString = request.getParameter("q");
        Boolean showInactive;
        if (StringUtils.equalsIgnoreCase(request.getParameter("inactive"), "all")) {
            showInactive = null;
        } 
        else {
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
        if (logger.isDebugEnabled()) {
            logger.debug("handleRequestInternal: searchString = " + searchString + " showInactive = " + showInactive + " description = " + description + " codeType = " + codeType);
        }
        List<Code> codes;
        if (description != null) {
            codes = codeService.readCodes(SessionServiceImpl.lookupUserSiteName(), codeType, searchString, description, showInactive);
        } 
        else {
            codes = codeService.readCodes(SessionServiceImpl.lookupUserSiteName(), codeType, searchString);
        }
        String view = super.getViewName();
        if ("table".equals(request.getParameter(VIEW))) {
            view = this.tableView;
        }
        if ("autoComplete".equals(request.getParameter(VIEW))) {
            view = this.autoCompleteView;
        }
        if ("resultsOnly".equals(request.getParameter(VIEW))) {
            view = this.resultsOnlyView;
        }
        return new ModelAndView(view, "codes", codes);
    }

}
