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

package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.lookups;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.type.LayoutType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.AbstractFieldHandler;
import org.apache.commons.logging.Log;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.util.Collection;
import java.util.List;

public class CodeHandler extends AbstractFieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());
	protected final PicklistItemService picklistItemService;

	public CodeHandler(ApplicationContext applicationContext) {
		super(applicationContext);
		picklistItemService = (PicklistItemService) applicationContext.getBean("picklistItemService");
	}

	protected String resolveCodeValue(String picklistNameId, Object defaultDisplayValue) {
	    if (logger.isTraceEnabled()) {
	        logger.trace("resolve: picklistNameId = " + picklistNameId + " defaultDisplayValue = " + defaultDisplayValue);
	    }
	    String val = defaultDisplayValue == null ? StringConstants.EMPTY : defaultDisplayValue.toString();
	    PicklistItem code = picklistItemService.getPicklistItemByDefaultDisplayValue(picklistNameId, val);
	    if (code != null) {
	        val = code.getValueDescription();
	    }
	    return val;
	}

	@Override
	protected void doHandler(HttpServletRequest request, HttpServletResponse response, PageContext pageContext,
	                       SectionDefinition sectionDefinition, List<SectionField> sectionFields, SectionField currentField,
	                       TangerineForm form, String formFieldName, Object fieldValue, StringBuilder sb) {
		createLookupWrapperBegin(sb);
		createDisplayInput(request, pageContext, sectionDefinition, currentField, form, formFieldName, fieldValue, sb);
		createHiddenInput(formFieldName, fieldValue, sb);
		createLookup(sb);
		createLookupWrapperEnd(sb);
	}

    protected void createLookupWrapperBegin(StringBuilder sb) {
        sb.append("<div class=\"lookupWrapper\">");
    }

	protected String resolveCodeLookupName(SectionDefinition sectionDefinition, SectionField currentField,
	                                     TangerineForm form) {
		String fieldPropertyName;

		if (LayoutType.isGridType(sectionDefinition.getLayoutType()) &&
				PropertyAccessorFactory.forBeanPropertyAccess(form.getDomainObject()).getPropertyValue(currentField.getFieldDefinition().getFieldName()) instanceof Collection &&
				currentField.getSecondaryFieldDefinition() != null) {
			fieldPropertyName = currentField.getSecondaryFieldDefinition().getFieldName();
		}
		else {
			fieldPropertyName = currentField.getFieldPropertyName();
		}
		return fieldPropertyName;
	}

	protected Object getCodeDisplayValue(SectionDefinition sectionDefinition, SectionField currentField,
	                                     TangerineForm form, String formFieldName, Object fieldValue) {
		String fieldPropertyName = resolveCodeLookupName(sectionDefinition, currentField, form);
		return resolveCodeValue(fieldPropertyName, fieldValue);
	}

    protected void createDisplayInput(HttpServletRequest request, PageContext pageContext, SectionDefinition sectionDefinition,
                                      SectionField currentField, TangerineForm form, String formFieldName, Object fieldValue, StringBuilder sb) {
        sb.append("<input value=\"").append(checkForNull(getCodeDisplayValue(sectionDefinition, currentField, form, formFieldName, fieldValue))).
		        append("\" class=\"text code ").append(resolveEntityAttributes(currentField));
	    writeErrorClass(pageContext, formFieldName, sb); // TODO: fix for errors

        sb.append("\" ");
	    getDisplayAttributes(sectionDefinition, currentField, form, formFieldName, sb);
	    
        sb.append("name=\"display-").append(formFieldName).append("\" id=\"display-").append(formFieldName).append("\"/>");
    }

    protected void createHiddenInput(String formFieldName, Object fieldValue, StringBuilder sb) {
        sb.append("<input type=\"hidden\" name=\"").append(formFieldName).append("\" id=\"hidden-");
	    sb.append(formFieldName).append("\" value=\"").append(checkForNull(fieldValue)).append("\"/>");
    }

    protected void createLookup(StringBuilder sb) {
        sb.append("<a class=\"lookupLink\" href=\"javascript:void(0)\" ");
        sb.append("onclick=\"").append(getLookupClickHandler()).append("\" ");
        String lookupMsg = getMessage("lookup");
        sb.append("alt=\"").append(lookupMsg).append("\" title=\"").append(lookupMsg).append("\">").append(lookupMsg).append("</a>");
    }

    protected void createLookupWrapperEnd(StringBuilder sb) {
        sb.append("</div>");
    }

    protected String getLookupClickHandler() {
        return "Lookup.loadCodePopup(this)";
    }

    protected void getDisplayAttributes(SectionDefinition sectionDefinition, SectionField currentField, TangerineForm form,
                                        String formFieldName, StringBuilder sb) {
	    String thisCode = resolveCodeLookupName(sectionDefinition, currentField, form);
        sb.append("lookup=\"").append(thisCode).append("\" codeType=\"").append(thisCode).append("\" ");
    }

    @Override
    public Object resolveDisplayValue(HttpServletRequest request, BeanWrapper beanWrapper, SectionField currentField) {
        return resolveCodeValue(currentField.getFieldPropertyName(), beanWrapper.getPropertyValue(currentField.getFieldPropertyName()));  
    }
}