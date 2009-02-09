package com.mpower.controller.constituent;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.mpower.controller.NoneStringTrimmerEditor;
import com.mpower.domain.Person;
import com.mpower.service.PersonService;
import com.mpower.service.SessionService;
import com.mpower.service.impl.SessionServiceImpl;

public class PersonSearchFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PersonService personService;

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    private SessionService sessionService;

    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(String.class, new NoneStringTrimmerEditor(true));
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        logger.info("**** in formBackingObject");
        Person p = new Person();
        p.setSite(sessionService.lookupSite());
        return p;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
            BindException errors) throws Exception {
        logger.info("**** in onSubmit()");
        Person person = (Person) command;
        BeanWrapper bw = new BeanWrapperImpl(person);
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

        List<Person> personList = personService.readPersons(SessionServiceImpl.lookupUserSiteName(), params);
        String sort = request.getParameter("sort");
        String ascending = request.getParameter("ascending");
        Boolean sortAscending;
        if (StringUtils.trimToNull(ascending) != null) {
            sortAscending = new Boolean(ascending);
        } else {
            sortAscending = new Boolean(true);
        }
        MutableSortDefinition sortDef = new MutableSortDefinition(sort,true,sortAscending);
        PagedListHolder pagedListHolder = new PagedListHolder(personList,sortDef);
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
        mav.addObject("person", person);
        mav.addObject("personList", personList);
        mav.addObject("personListSize", personList.size());
        return mav;
    }
}
