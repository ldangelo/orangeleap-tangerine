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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.mpower.domain.QueryLookup;
import com.mpower.service.QueryLookupService;
import com.mpower.service.impl.SessionServiceImpl;

public class QueryLookupController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private QueryLookupService queryLookupService;

    public void setQueryLookupService(QueryLookupService queryLookupService) {
        this.queryLookupService = queryLookupService;
    }

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> queryParams = findQueryParams(request);

        // List<String> displayColumns = new ArrayList<String>();
        // displayColumns.add("lastName");
        // displayColumns.add("firstName");
        String fieldDef = StringUtils.trimToNull(request.getParameter("fieldDef"));
        QueryLookup queryLookup = queryLookupService.readQueryLookup(SessionServiceImpl.lookupUserSiteName(), fieldDef);
        List<Object> objects = queryLookupService.executeQueryLookup(SessionServiceImpl.lookupUserSiteName(), fieldDef,
                queryParams);
        ModelAndView mav = new ModelAndView(super.getViewName());
        mav.addObject("objects", objects);
        mav.addObject("queryLookup", queryLookup);
        sortPaginate(request, mav, objects);

        // mav.addObject("displayColumns", displayColumns);
        // mav.addObject("parameterMap", request.getParameterMap());
        return mav;
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
    protected void sortPaginate(HttpServletRequest request, ModelAndView mav, List<Object> objects) {
        String sort = request.getParameter("sort");
        String ascending = request.getParameter("ascending");
        Boolean sortAscending;
        if (StringUtils.trimToNull(ascending) != null) {
            sortAscending = new Boolean(ascending);
        } else {
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
        mav.addObject("pagedListHolder", pagedListHolder);
        mav.addObject("currentSort", sort);
        mav.addObject("currentAscending", sortAscending);
    }

}
