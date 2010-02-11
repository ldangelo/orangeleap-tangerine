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

package com.orangeleap.tangerine.controller.lookup;

import com.orangeleap.tangerine.domain.QueryLookup;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.service.QueryLookupService;
import com.orangeleap.tangerine.service.customization.MessageService;
import com.orangeleap.tangerine.service.customization.PageCustomizationService;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.type.MessageResourceType;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerinePagedListHolder;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.FieldHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.FieldHandlerHelper;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.HtmlUtils;

/**
 * Only accountNumber, firstName, lastName, and organizationName are supported query field names for query lookups.
 */
public class QueryLookupController extends SimpleFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "queryLookupService")
    protected QueryLookupService queryLookupService;

    @Resource(name = "pageCustomizationService")
    protected PageCustomizationService pageCustomizationService;

	@Resource(name = "messageService")
	protected MessageService messageService;
	
    @Resource(name = "tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;

    @Resource(name = "fieldHandlerHelper")
    private FieldHandlerHelper fieldHandlerHelper;

    private static final String QUERY_LOOKUP = "queryLookup";

	protected String findFieldDef(HttpServletRequest request) {
        return getParameter(request, "fieldDef");
    }

    protected List executeQueryLookup(HttpServletRequest request, String fieldDef) {
		Map<String, String> queryParams = findQueryParams(request);
		List objects = queryLookupService.executeQueryLookup(fieldDef, queryParams);
        request.setAttribute("objects", objects);
        return objects;
    }

    protected QueryLookup doQueryLookup(HttpServletRequest request, String fieldDef) {
        QueryLookup queryLookup = queryLookupService.readQueryLookup(fieldDef);
        request.setAttribute(QUERY_LOOKUP, queryLookup);
        return queryLookup;
    }

    protected void performQuery(HttpServletRequest request) {
        String fieldDef = findFieldDef(request);

        QueryLookup queryLookup = doQueryLookup(request, fieldDef);
        if (logger.isDebugEnabled()) {
            logger.debug("performQuery: fieldDef = " + fieldDef);
        }
        List objects = executeQueryLookup(request, fieldDef);
        sortPaginate(request, objects, queryLookup);
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        performQuery(request);
        return new ModelAndView(super.getSuccessView());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {
        String fieldDef = findFieldDef(request);
        QueryLookup queryLookup = doQueryLookup(request, fieldDef);
        if (logger.isDebugEnabled()) {
            logger.debug("showForm: fieldDef = " + fieldDef);
        }
	    final Map<String, String> fieldMap = findLookupSectionFields(request, QUERY_LOOKUP, queryLookup);
        request.setAttribute("fieldMap", fieldMap);
	    if (queryLookup.getEntityType() == EntityType.constituent) {
	        request.setAttribute("forIndividuals", fieldMap.containsKey(StringConstants.LAST_NAME));
		    request.setAttribute("forOrganizations", fieldMap.containsKey(StringConstants.ORGANIZATION_NAME));
	    }
        request.setAttribute("fieldDef", fieldDef);
        request.setAttribute("showOtherField", Boolean.valueOf(request.getParameter("showOtherField")));
        return super.showForm(request, response, errors, controlModel);
    }

    @SuppressWarnings("unchecked")
    protected Map<String, String> findLookupSectionFields(HttpServletRequest request, String pageName, QueryLookup queryLookup) {
        List<SectionField> sectionFields = findSectionFields(pageName, queryLookup);
        Map<String, String> fieldMap = new ListOrderedMap();
        if (sectionFields != null) {
            for (SectionField field : sectionFields) {
	            if (queryLookup.getEntityType() == EntityType.constituent) {
					if (queryLookupService.isSupportedConstituentFieldName(field.getFieldPropertyName())) {
						fieldMap.put(getFieldPropertyName(field), getFieldLabel(request, field));
					}
					else {
						if (logger.isWarnEnabled()) {
							logger.warn("findLookupSectionFields: unsupported query lookup section field = " + field.getFieldPropertyName());
						}
					}
	            }
	            else {
		            fieldMap.put(getFieldPropertyName(field), getFieldLabel(request, field));
	            }
            }
        }
        return fieldMap;
    }

    protected List<SectionField> findSectionFields(String pageName, QueryLookup queryLookup) {
        List<SectionField> sectionFields = null;
        List<SectionDefinition> sectionDefs = pageCustomizationService.readSectionDefinitionsByPageTypeRoles(PageType.valueOf(pageName), tangerineUserHelper.lookupUserRoles());
        for (SectionDefinition sectionDefinition : sectionDefs) {
            if (sectionDefinition.getSectionName().equals(queryLookup.getSectionName())) {
                sectionFields = pageCustomizationService.readSectionFieldsBySection(sectionDefinition);
                break;
            }
        }
        return sectionFields;
    }

    protected String getFieldPropertyName(SectionField sectionField) {
        String fieldPropertyName = sectionField.getFieldPropertyName();

        if ((sectionField.isCompoundField() && sectionField.getSecondaryFieldDefinition().isCustom()) ||
                (!sectionField.isCompoundField() && sectionField.getFieldDefinition().isCustom())) {
            fieldPropertyName += StringConstants.DOT_VALUE;
        }
        return fieldPropertyName;
    }

    protected String getFieldLabel(HttpServletRequest request, SectionField sectionField) {
        String labelText = messageService.lookupMessage(MessageResourceType.FIELD_LABEL, sectionField.getFieldLabelName(), request.getLocale());
        if (!org.springframework.util.StringUtils.hasText(labelText)) {
            if (!sectionField.isCompoundField()) {
                labelText = sectionField.getFieldDefinition().getDefaultLabel();
            }
            else {
                labelText = sectionField.getSecondaryFieldDefinition().getDefaultLabel();
            }
        }
        return labelText;
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
     * @param request
     * @param objects
     * @param queryLookup
     */
    @SuppressWarnings("unchecked")
    protected void sortPaginate(HttpServletRequest request, List objects, QueryLookup queryLookup) {
	    final Boolean sortAscending = Boolean.TRUE;
	    final List<SectionField> sectionFields = findSectionFields(QUERY_LOOKUP, queryLookup);
	    if (queryLookup.getEntityType() == EntityType.constituent) {
		    TangerinePagedListHolder pagedListHolder = new TangerinePagedListHolder();
		    pagedListHolder.doSort(objects, StringConstants.ACCOUNT_NUMBER, "displayValue");
	    }
	    else {
			String searchOption = getParameter(request, "searchOption");
			if (searchOption == null) {
				searchOption = queryLookup.getQueryLookupParams().get(0).getName();
			}

			MutableSortDefinition sortDef = new MutableSortDefinition(searchOption, true, sortAscending);
			TangerinePagedListHolder pagedListHolder = new TangerinePagedListHolder(objects, sortDef);
			pagedListHolder.resort();
	    }

        final List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

        for (final Object obj : objects) {
            final Map<String, Object> map = new HashMap<String, Object>();
            final BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
            map.put(StringConstants.ID, beanWrapper.getPropertyValue(StringConstants.ID));
            map.put(StringConstants.ENTITY_NAME, StringUtils.uncapitalize(beanWrapper.getWrappedClass().getSimpleName()));

            final StringBuilder acctNameSb = new StringBuilder();
	        final StringBuilder nameSb = new StringBuilder();
	        if (queryLookup.getEntityType() == EntityType.constituent && ! hasAccountNumberField(sectionFields)) {
		        acctNameSb.append(beanWrapper.getPropertyValue(StringConstants.ACCOUNT_NUMBER)).append(" - ");
	        }

            for (SectionField field : sectionFields) {
                String fieldPropertyName = getFieldPropertyName(field);
                if (beanWrapper.isReadableProperty(fieldPropertyName)) {
                    Object fieldValue = beanWrapper.getPropertyValue(fieldPropertyName);
                    FieldHandler fieldHandler = fieldHandlerHelper.lookupFieldHandler(field.getFieldType());
                    if (fieldHandler != null) {
                        Object displayValue = fieldHandler.resolveDisplayValue(request, beanWrapper, field, fieldValue);
                        if (displayValue != null) {
                            acctNameSb.append(displayValue).append(" ");
	                        nameSb.append(displayValue).append(" ");
                        }
                    }

                }
            }
            map.put("displayValue", nameSb.toString());
	        map.put("accountName", acctNameSb.toString());
            results.add(map);
        }

        request.setAttribute("results", results);
    }

	protected boolean hasAccountNumberField(final List<SectionField> sectionFields) {
		boolean hasAcctNum = false;
		for (SectionField field : sectionFields) {
			if (StringConstants.ACCOUNT_NUMBER.equals(field.getFieldPropertyName())) {
				hasAcctNum = true;
				break;
			}
		}
		return hasAcctNum;
	}

    protected String getParameter(HttpServletRequest request, String parameterName) {
        return StringUtils.trimToNull(HtmlUtils.htmlUnescape(request.getParameter(parameterName)));
    }
}
