package com.orangeleap.tangerine.controller.lookup;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.HtmlUtils;

import com.orangeleap.tangerine.domain.QueryLookup;
import com.orangeleap.tangerine.service.QueryLookupService;

public class QueryLookupController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="queryLookupService")
    protected QueryLookupService queryLookupService;
    
    protected String findFieldDef(HttpServletRequest request) {
        return getParameter(request, "fieldDef");
    }
    
    protected List<Object> executeQueryLookup(HttpServletRequest request, String fieldDef) {
        Map<String, String> queryParams = findQueryParams(request);
        List<Object> objects = queryLookupService.executeQueryLookup(fieldDef, queryParams);
        request.setAttribute("objects", objects);
        return objects;
    }
    
    protected QueryLookup doQueryLookup(HttpServletRequest request, String fieldDef) {
        QueryLookup queryLookup = queryLookupService.readQueryLookup(fieldDef);
        request.setAttribute("queryLookup", queryLookup);
        return queryLookup;
    }
    
    protected void performQuery(HttpServletRequest request, HttpServletResponse response) {
        String fieldDef = findFieldDef(request);
        
        QueryLookup queryLookup = doQueryLookup(request, fieldDef);
        if (logger.isDebugEnabled()) {
            logger.debug("performQuery: fieldDef = " + fieldDef );
        }
        List<Object> objects = executeQueryLookup(request, fieldDef);
        sortPaginate(request, objects, queryLookup);
    }
    
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        performQuery(request, response);
        return new ModelAndView(super.getSuccessView());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {
        String fieldDef = findFieldDef(request);
        QueryLookup queryLookup = doQueryLookup(request, fieldDef);
        if (logger.isDebugEnabled()) {
            logger.debug("showForm: fieldDef = " + fieldDef );
        }
        request.setAttribute("fieldDef", fieldDef);
        request.setAttribute("showOtherField", Boolean.valueOf(request.getParameter("showOtherField")));
        return super.showForm(request, response, errors, controlModel);
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        return "";
    }

    @SuppressWarnings("unchecked")
    protected Map<String, String> findQueryParams(HttpServletRequest request) {
        Map<String, String> queryParams = new HashMap<String, String>();
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String param = enu.nextElement();
            String paramValue = getParameter(request, param);
            queryParams.put(param, paramValue);
        }
        return queryParams;
    }

    /**
     * TODO: move to another class or an interceptor or an annotation
     * @param request
     * @param objects
     * @param queryLookup
     */
    protected void sortPaginate(HttpServletRequest request, List<Object> objects, QueryLookup queryLookup) {
        String searchOption = getParameter(request, "searchOption");
        if (searchOption == null) {
            searchOption = queryLookup.getQueryLookupParams().get(0).getName();
        }
        
        Boolean sortAscending = new Boolean(true);
        MutableSortDefinition sortDef = new MutableSortDefinition(searchOption, true, sortAscending);
        PagedListHolder pagedListHolder = new PagedListHolder(objects, sortDef);
        pagedListHolder.resort();

        request.setAttribute("results", pagedListHolder.getSource());
    }
    
    protected String getParameter(HttpServletRequest request, String parameterName) {
        return StringUtils.trimToNull(HtmlUtils.htmlUnescape(request.getParameter(parameterName)));
    }
}
