package com.orangeleap.tangerine.controller.constituent;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.orangeleap.tangerine.controller.NoneStringTrimmerEditor;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.SessionService;
import com.orangeleap.tangerine.util.TangerinePagedListHolder;

public class ConstituentSearchFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="constituentService")
    private ConstituentService constituentService;

    @Resource(name="sessionService")
    private SessionService sessionService;

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(String.class, new NoneStringTrimmerEditor(true));
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        if (logger.isTraceEnabled()) {
            logger.trace("formBackingObject:");
        }
        Constituent p = new Constituent();
        p.setSite(sessionService.lookupSite());
        return p;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        if (logger.isTraceEnabled()) {
            logger.trace("onSubmit:");
        }
        Constituent constituent = (Constituent) command;
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(constituent);
        Map<String, Object> params = new HashMap<String, Object>();
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String param = enu.nextElement();
            if (!param.equalsIgnoreCase("page") && !param.equalsIgnoreCase("view") && !param.equalsIgnoreCase("sort") && StringUtils.trimToNull(request.getParameter(param)) != null) {
                if (bw.isReadableProperty(param)){
                    params.put(param, bw.getPropertyValue(param));
                }
            }
        }

        List<Constituent> constituentList = constituentService.searchConstituents(params);
        String sort = request.getParameter("sort");
        String ascending = request.getParameter("ascending");
        Boolean sortAscending;
        if (StringUtils.trimToNull(ascending) != null) {
            sortAscending = new Boolean(ascending);
        } else {
            sortAscending = new Boolean(true);
        }
        MutableSortDefinition sortDef = new MutableSortDefinition(sort,true,sortAscending);
        TangerinePagedListHolder pagedListHolder = new TangerinePagedListHolder(constituentList, sortDef);
        pagedListHolder.resort();
        pagedListHolder.setMaxLinkedPages(3);
        pagedListHolder.setPageSize(10);
        String page = request.getParameter("page");

        Integer pg = 0;
        if (!StringUtils.isBlank(page)) {
            pg = Integer.valueOf(page);
        }

        pagedListHolder.setPage(pg);

        ModelAndView mav = new ModelAndView(getSuccessView());
        mav.addObject("pagedListHolder", pagedListHolder);
        mav.addObject("currentSort", sort);
        mav.addObject("currentAscending", sortAscending);
        mav.addObject("constituent", constituent);
        mav.addObject("constituentList", constituentList);
        mav.addObject("constituentListSize", constituentList.size());
        return mav;
    }
}
