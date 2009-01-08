package com.mpower.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.mpower.domain.Person;
import com.mpower.domain.Viewable;
import com.mpower.service.PersonService;
import com.mpower.service.SiteService;
import com.mpower.service.impl.SessionServiceImpl;
import com.mpower.type.PageType;
import com.mpower.util.StringConstants;

public abstract class TangerineFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    protected PersonService personService;
    protected SiteService siteService;
    protected String pageType;

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public void setSiteService(SiteService siteService) {
        this.siteService = siteService;
    }

    /**
     * The default page type is the commandName.  Override for specific page types
     * @return pageType, the commandName
     */
    protected String getPageType() {
        return StringUtils.hasText(pageType) ? pageType: getCommandName();
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    protected Long getPersonId(HttpServletRequest request) {
        return Long.valueOf(request.getParameter(StringConstants.PERSON_ID));
    }

    protected Person getPerson(HttpServletRequest request) {
        return personService.readPersonById(getPersonId(request)); // TODO: do we need to check if the user can view this person (authorization)?
    }

    @SuppressWarnings("unchecked")
    protected void addPersonToReferenceData(HttpServletRequest request, Map refData) {
        refData.put(StringConstants.PERSON, getPerson(request));
    }

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true)); // TODO: custom date format
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        Viewable viewable = findViewable(request);
        this.createFieldMaps(request, viewable);
        return viewable;
    }

    @Override
    protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
        request.setAttribute(StringConstants.COMMAND_OBJECT, this.getCommandName()); // To be used by input.jsp to check for errors
        super.onBindAndValidate(request, command, errors);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map refData = new HashMap();
        this.addPersonToReferenceData(request, refData);
        addRefData(request, this.getPersonId(request), refData);

        return refData;
    }

    protected void createFieldMaps(HttpServletRequest request, Viewable viewable) {
        if (isFormSubmission(request)) {
            Map<String, String> fieldLabelMap = siteService.readFieldLabels(SessionServiceImpl.lookupUserSiteName(), PageType.valueOf(this.getPageType()), SessionServiceImpl.lookupUserRoles(), request.getLocale());
            viewable.setFieldLabelMap(fieldLabelMap);

            Map<String, Object> valueMap = siteService.readFieldValues(SessionServiceImpl.lookupUserSiteName(), PageType.valueOf(this.getPageType()), SessionServiceImpl.lookupUserRoles(), viewable);
            viewable.setFieldValueMap(valueMap);
        }
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        save(request, response, command, errors);
        return new ModelAndView(getSuccessView() + "?" + StringConstants.PERSON_ID + "=" + getPersonId(request));
    }

    protected abstract Viewable findViewable(HttpServletRequest request);

    @SuppressWarnings("unchecked")
    protected abstract void addRefData(HttpServletRequest request, Long personId, Map refData);

    protected abstract void save(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception;
}
