package com.mpower.controller.lookup;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.mpower.domain.QueryLookup;
import com.mpower.service.QueryLookupService;
import com.mpower.service.impl.SessionServiceImpl;

public class QueryLookupController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    protected QueryLookupService queryLookupService;

    public void setQueryLookupService(QueryLookupService queryLookupService) {
        this.queryLookupService = queryLookupService;
    }
    
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Map<String, String> queryParams = findQueryParams(request);

        String fieldDef = StringUtils.trimToNull(request.getParameter("fieldDef"));
        Boolean showOtherField = Boolean.valueOf(request.getParameter("showOtherField"));
        QueryLookup queryLookup = queryLookupService.readQueryLookup(SessionServiceImpl.lookupUserSiteName(), fieldDef);
        List<Object> objects = queryLookupService.executeQueryLookup(SessionServiceImpl.lookupUserSiteName(), fieldDef,
                queryParams);
        Map<String, Object> map = new HashMap<String, Object>(3);
        map.put("objects", objects);
        map.put("queryLookup", queryLookup);
        map.put("showOtherField", showOtherField);
        
        sortPaginate(request, map, objects);
        return new ModelAndView(super.getSuccessView(), map);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {
        String fieldDef = StringUtils.trimToNull(request.getParameter("fieldDef"));
        QueryLookup queryLookup = queryLookupService.readQueryLookup(SessionServiceImpl.lookupUserSiteName(), fieldDef);
        request.setAttribute("fieldDef", fieldDef);
        request.setAttribute("queryLookup", queryLookup);
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
            String paramValue = StringUtils.trimToNull(request.getParameter(param));
            if (paramValue != null && !param.equalsIgnoreCase("fieldDef") && !param.equalsIgnoreCase("view") && !param.equalsIgnoreCase("resultsOnly")) {
                queryParams.put(param, paramValue);
            }
        }
        return queryParams;
    }

    /**
     * TODO: move to another class or an interceptor or an annotation
     * @param request
     * @param mav
     * @param objects
     */
    protected void sortPaginate(HttpServletRequest request, Map<String, Object> map, List<Object> objects) {
        String sort = request.getParameter("sort");
        String ascending = request.getParameter("ascending");
        Boolean sortAscending;
        if (StringUtils.trimToNull(ascending) != null) {
            sortAscending = new Boolean(ascending);
        } 
        else {
            sortAscending = new Boolean(true);
        }
        MutableSortDefinition sortDef = new MutableSortDefinition(sort, true,sortAscending);
        PagedListHolder pagedListHolder = new PagedListHolder(objects, sortDef);
        pagedListHolder.resort();
        pagedListHolder.setMaxLinkedPages(3);
        pagedListHolder.setPageSize(50);
        String page = request.getParameter("page");

        Integer pg = 0;
        if (!StringUtils.isBlank(page)) {
            pg = Integer.valueOf(page);
        }

        pagedListHolder.setPage(pg);
        map.put("pagedListHolder", pagedListHolder);
        map.put("currentSort", sort);
        map.put("currentAscending", sortAscending);
    }

}
