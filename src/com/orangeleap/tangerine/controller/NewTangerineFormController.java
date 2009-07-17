package com.orangeleap.tangerine.controller;

import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
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
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class NewTangerineFormController extends SimpleFormController {

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

    protected String pageType;

    /**
     * The default page type is the commandName.  Override for specific page types TODO: remove for commandName
     * @return pageType, the commandName
     */
    protected String getPageType() {
        return StringUtils.hasText(pageType) ? pageType: getCommandName();
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

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

	@Override
	@SuppressWarnings("unchecked")
	protected void onBind(HttpServletRequest request, Object command, BindException e) throws Exception {
		TangerineForm form = (TangerineForm) command;
		convertFormToDomain(request, form, request.getParameterMap());
	}

	protected void convertFormToDomain(HttpServletRequest request, TangerineForm form, Map<String, Object> paramMap)
			throws Exception {
		form.setFieldMap(new TreeMap<String, Object>(paramMap));

		ServletRequestDataBinder binder = new ServletRequestDataBinder(form.getDomainObject());
		initBinder(request, binder);

		BeanWrapper bean = PropertyAccessorFactory.forBeanPropertyAccess(form.getDomainObject());

		for (String escapedFormFieldName : form.getFieldMap().keySet()) {
			String fieldName = TangerineForm.unescapeFieldName(escapedFormFieldName);

			/* Check for grid rows */
			String gridCollectionName = TangerineForm.getGridCollectionName(fieldName);
			if (StringUtils.hasText(gridCollectionName)) {
				if (bean.isReadableProperty(gridCollectionName) &&
						bean.getPropertyValue(gridCollectionName) instanceof Collection) {

				}
			}
			else {
				MutablePropertyValues propertyValues = new MutablePropertyValues();
				propertyValues.addPropertyValue(fieldName, form.getFieldMap().get(escapedFormFieldName));
				binder.bind(propertyValues);
			}
		}

	}

	@Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		/** Not allowed to be overridden - binding done in TangerineForm */
        super.initBinder(request, binder);
        binder.registerCustomEditor(Date.class, new TangerineCustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true)); // TODO: custom date format
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        request.setAttribute(StringConstants.COMMAND_OBJECT, this.getCommandName()); // To be used by input.jsp to check for errors
        request.setAttribute("pageType", this.getPageType());
        AbstractEntity entity = findEntity(request);
        entity.setDefaults();

        this.createFieldMaps(request, entity);
        TangerineForm form = new TangerineForm();
        form.setDomainObject(entity);
        form.setFieldMapFromEntity(entity);
        return form;
    }

    protected void createFieldMaps(HttpServletRequest request, AbstractEntity entity) {
//        if (!isFormSubmission(request)) {
            List<String> roles = tangerineUserHelper.lookupUserRoles();

            Map<String, String> labelMap = new HashMap<String, String>();
            Map<String, Object> valueMap = new HashMap<String, Object>();
            Map<String, FieldDefinition> typeMap = new HashMap<String, FieldDefinition>();

            siteService.readFieldInfo(PageType.valueOf(this.getPageType()), roles, request.getLocale(), entity, labelMap, valueMap, typeMap);

            entity.setFieldLabelMap(labelMap);
            entity.setFieldValueMap(valueMap);
            entity.setFieldTypeMap(typeMap);
//        }
    }

    protected String appendSaved(String url) {
        return new StringBuilder(url).append("&").append(StringConstants.SAVED_EQUALS_TRUE).toString();
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        return new ModelAndView(appendSaved(new StringBuilder().append(getSuccessView()).append("?").append(StringConstants.CONSTITUENT_ID).append("=").append(getConstituentId(request)).toString()));
    }

    protected abstract AbstractEntity findEntity(HttpServletRequest request);
}