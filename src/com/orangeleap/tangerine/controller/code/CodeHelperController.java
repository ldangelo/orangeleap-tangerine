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

public class CodeHelperController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    public static final String VIEW = "view";

    @Resource(name="picklistItemService")
    private PicklistItemService picklistItemService;
    private String tableView;
    private String autoCompleteView;
    private String resultsOnlyView;

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
        List<PicklistItem> items;
        if (description != null) {
        	items = picklistItemService.getPicklistItems(codeType, searchString, description, showInactive);
        } 
        else {
        	items = picklistItemService.getPicklistItems(codeType, searchString, "", showInactive);
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
        return new ModelAndView(view, "codes", items);
    }

}
