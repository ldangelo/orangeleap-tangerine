package com.orangeleap.tangerine.controller.code;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.util.StringConstants;

public class CodeHelperController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    public static final String VIEW = "view";
    public static final String CODES = "codes";

    @Resource(name="picklistItemService")
    private PicklistItemService picklistItemService;
    private String autoCompleteView;
    private String resultsOnlyView;

    public void setAutoCompleteView(String autoCompleteView) {
        this.autoCompleteView = autoCompleteView;
    }

    public void setResultsOnlyView(String resultsOnlyView) {
        this.resultsOnlyView = resultsOnlyView;
    }

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String codeValue = request.getParameter("q");
        Boolean showInactive;
        if (StringUtils.equalsIgnoreCase(request.getParameter("inactive"), "all")) {
            showInactive = null;
        } 
        else {
            showInactive = Boolean.valueOf(request.getParameter("inactive"));
        }
        if (GenericValidator.isBlankOrNull(codeValue)) {
            codeValue = request.getParameter("value");
        }
        if (codeValue == null) {
            codeValue = StringConstants.EMPTY;
        }
        String codeDescription = request.getParameter("description");
        String codeType = request.getParameter("type");
        if (logger.isTraceEnabled()) {
            logger.trace("handleRequestInternal: codeValue = " + codeValue + " showInactive = " + showInactive + " codeDescription = " + codeDescription + " codeType = " + codeType);
        }
        List<PicklistItem> items;
        if (codeDescription != null) {
        	items = picklistItemService.findCodeByDescription(codeType, codeDescription, showInactive);
        } 
        else {
        	items = picklistItemService.findCodeByValue(codeType, codeValue, showInactive);
        }
        String view = super.getViewName();
        if ("autoComplete".equals(request.getParameter(VIEW))) {
            view = this.autoCompleteView;
        }
        if (isResultsOnlyView(request) != null) {
            view = isResultsOnlyView(request);
        }
        return new ModelAndView(view, CODES, items);
    }
    
    protected String isResultsOnlyView(HttpServletRequest request) {
        if ("resultsOnly".equals(request.getParameter(VIEW))) {
            return this.resultsOnlyView;
        }
        return null;
    }
}
