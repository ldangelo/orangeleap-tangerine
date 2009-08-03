/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.controller;

import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.service.AddressService;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.EmailService;
import com.orangeleap.tangerine.service.PhoneService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.web.common.TangerineCustomDateEditor;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class TangerineFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name="constituentService")
    protected ConstituentService constituentService;

    @Resource(name="addressService")
    protected AddressService addressService;

    @Resource(name="phoneService")
    protected PhoneService phoneService;

    @Resource(name="emailService")
    protected EmailService emailService;

    @Resource(name="siteService")
    protected SiteService siteService;

    @Resource(name="tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;

    public Long getIdAsLong(HttpServletRequest request, String id) {
        if (logger.isTraceEnabled()) {
            logger.trace("getIdAsLong: id = " + id);
        }
        String paramId = request.getParameter(id);
        if (StringUtils.hasText(paramId)) {
            return Long.valueOf(request.getParameter(id));
        }
        return null;
    }

    protected Long getConstituentId(HttpServletRequest request) {
        return this.getIdAsLong(request, StringConstants.CONSTITUENT_ID);
    }

    protected String getConstituentIdString(HttpServletRequest request) {
        return request.getParameter(StringConstants.CONSTITUENT_ID);
    }

	protected Constituent getConstituent(HttpServletRequest request) {
	    Long constituentId = getConstituentId(request);
	    Constituent constituent = null;
	    if (constituentId != null) {
	        constituent = constituentService.readConstituentById(constituentId); // TODO: do we need to check if the user can view this constituent (authorization)?
	    }
	    if (constituent == null) {
	        throw new IllegalArgumentException("The constituent ID was not found");
	    }
	    return constituent;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void onBind(HttpServletRequest request, Object command, BindException e) throws Exception {
		TangerineForm form = (TangerineForm) command;
		convertFormToDomain(request, form, new TreeMap<String, Object>(request.getParameterMap()));
	}

	/**
	 * Converts field names and values from the form bean to the domain object using Spring DataBinders.
	 * Override for specific conversion rules (lists, nested beans, etc).
	 * @param request
	 * @param form
	 * @param paramMap
	 * @throws Exception
	 */
	protected void convertFormToDomain(HttpServletRequest request, TangerineForm form, Map<String, Object> paramMap)
			throws Exception {
		form.setFieldMap(new TreeMap<String, Object>());

		ServletRequestDataBinder binder = new ServletRequestDataBinder(form.getDomainObject());
		initBinder(request, binder);

		BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(form.getDomainObject());

		MutablePropertyValues propertyValues = new MutablePropertyValues();
		for (Object obj : paramMap.keySet()) {
			String escapedFormFieldName = obj.toString();
			String fieldName = TangerineForm.unescapeFieldName(escapedFormFieldName);

			Object val;
			if (beanWrapper.getPropertyType(fieldName) != null && beanWrapper.getPropertyType(fieldName).isArray()) {
				val = request.getParameterValues(escapedFormFieldName);
			}
			else {
				val = request.getParameter(escapedFormFieldName);
			}
			form.addField(escapedFormFieldName, val);
			propertyValues.addPropertyValue(fieldName, val);
		}
		binder.bind(propertyValues);
	}

	@Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
        binder.registerCustomEditor(Date.class, new TangerineCustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true)); 
		binder.registerCustomEditor(String.class, new NoneStringTrimmerEditor(true));
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        request.setAttribute(StringConstants.COMMAND_OBJECT, this.getCommandName()); // To be used by input.jsp to check for errors
        AbstractEntity entity = findEntity(request);

        this.createFieldMaps(request, entity);
        TangerineForm form = new TangerineForm();
        form.setDomainObject(entity);
        form.setFieldMapFromEntity(entity);

	    request.setAttribute("domainObjectName", StringUtils.uncapitalize(entity.getClass().getSimpleName()));
        return form;
    }

    protected void createFieldMaps(HttpServletRequest request, AbstractEntity entity) {
		List<String> roles = tangerineUserHelper.lookupUserRoles();
		siteService.readFieldInfo(PageType.findByUrl(request.getServletPath()), roles, request.getLocale(), entity);
    }

	protected void rebindEntityValuesToForm(HttpServletRequest request, TangerineForm form, AbstractEntity entity) {
		createFieldMaps(request, entity);
		form.setFieldMapFromEntity(entity);
	}

    protected String appendSaved(String url) {
        return new StringBuilder(url).append("&").append(StringConstants.SAVED_EQUALS_TRUE).toString();
    }

	protected void bindDomainErrorsToForm(HttpServletRequest request, BindException formErrors, BindException domainErrors,
	                                      TangerineForm form, AbstractEntity entity) {
		bindDomainErrorsToForm(formErrors, domainErrors);
		rebindEntityValuesToForm(request, form, entity);
	}

	protected void bindDomainErrorsToForm(BindException formErrors, BindException domainErrors) {
		if (domainErrors != null && domainErrors.hasErrors()) {
			for (Object thisError : domainErrors.getAllErrors()) {
				if (thisError instanceof FieldError) {
					FieldError domainFieldError = (FieldError) thisError;
					formErrors.rejectValue(new StringBuilder(StringConstants.FIELD_MAP_START).
							append(TangerineForm.escapeFieldName(domainFieldError.getField())).
							append(StringConstants.FIELD_MAP_END).toString(),
							domainFieldError.getCode(),
							domainFieldError.getArguments(),
							domainFieldError.getDefaultMessage());
				}
				else {
					formErrors.addError((ObjectError) thisError);
				}
			}
		}
	}

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        return new ModelAndView(appendSaved(new StringBuilder().append(getSuccessView()).append("?").append(StringConstants.CONSTITUENT_ID).append("=").append(getConstituentId(request)).toString()));
    }

    protected abstract AbstractEntity findEntity(HttpServletRequest request);
}