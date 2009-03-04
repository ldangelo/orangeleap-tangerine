package com.mpower.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.mpower.domain.model.AbstractEntity;
import com.mpower.domain.model.customization.FieldDefinition;
import com.mpower.service.ConstituentService;
import com.mpower.service.SiteService;
import com.mpower.type.PageType;
import com.mpower.util.StringConstants;
import com.mpower.util.TangerineUserHelper;

public abstract class TangerineFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="constituentService")
    protected ConstituentService personService;

    @Resource(name="siteService")
    protected SiteService siteService;    
    
    @Resource(name="tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;

    protected String pageType;    

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

    public Long getIdAsLong(HttpServletRequest request, String id) {
        if (logger.isDebugEnabled()) {
            logger.debug("getIdAsLong: id = " + id);
        }
        String paramId = request.getParameter(id);
        if (StringUtils.hasText(paramId)) {
            return Long.valueOf(request.getParameter(id));
        }
        return null;
    }

    protected Long getConstituentId(HttpServletRequest request) {
        return this.getIdAsLong(request, StringConstants.PERSON_ID);
    }

    protected String getConstituentIdString(HttpServletRequest request) {
        return request.getParameter(StringConstants.PERSON_ID);
    }

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true)); // TODO: custom date format
        binder.registerCustomEditor(String.class, new NoneStringTrimmerEditor(true));
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        request.setAttribute(StringConstants.COMMAND_OBJECT, this.getCommandName()); // To be used by input.jsp to check for errors
        AbstractEntity entity = findEntity(request);
        this.createFieldMaps(request, entity);
        return entity;
    }

    protected void createFieldMaps(HttpServletRequest request, AbstractEntity entity) {
        if (isFormSubmission(request)) {
            List<String> roles = tangerineUserHelper.lookupUserRoles();
            
            Map<String, String> fieldLabelMap = siteService.readFieldLabels(PageType.valueOf(this.getPageType()), roles, request.getLocale());
            entity.setFieldLabelMap(fieldLabelMap);

            Map<String, Object> valueMap = siteService.readFieldValues(PageType.valueOf(this.getPageType()), roles, entity);
            entity.setFieldValueMap(valueMap);

            Map<String, FieldDefinition> typeMap = siteService.readFieldTypes(PageType.valueOf(this.getPageType()), roles);
            entity.setFieldTypeMap(typeMap);
        }
    }
    
    protected String appendSaved(String url) {
        return new StringBuilder(url).append("&").append(StringConstants.SAVED_EQUALS_TRUE).toString();
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        return new ModelAndView(appendSaved(new StringBuilder().append(getSuccessView()).append("?").append(StringConstants.PERSON_ID).append("=").append(getConstituentId(request)).toString()));
    }

    protected abstract AbstractEntity findEntity(HttpServletRequest request);
}
