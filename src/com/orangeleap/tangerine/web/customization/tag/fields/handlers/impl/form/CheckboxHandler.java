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

package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.form;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.AbstractFieldHandler;
import org.springframework.beans.BeanWrapper;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.util.List;

/**
 * User: alexlo
 * Date: Jul 6, 2009
 * Time: 1:41:26 PM
 */
public class CheckboxHandler extends AbstractFieldHandler {

	public CheckboxHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Override
	protected void doHandler(HttpServletRequest request, HttpServletResponse response, PageContext pageContext,
	                      SectionDefinition sectionDefinition, List<SectionField> sectionFields, SectionField currentField,
	                      TangerineForm form, String formFieldName, Object fieldValue, StringBuilder sb) {

		sb.append("<input type=\"hidden\" name=\"_").append(formFieldName).append("\"/>");
		sb.append("<input type=\"checkbox\" value=\"true\" class=\"checkbox ").append(resolveEntityAttributes(currentField));
		sb.append("\" name=\"").append(formFieldName).append("\" ");
		sb.append("id=\"").append(formFieldName).append("\" ");
		
		if (fieldValue != null &&
				((fieldValue instanceof Boolean && Boolean.TRUE.equals(fieldValue)) || "true".equalsIgnoreCase(fieldValue.toString()))) {
		    sb.append("checked=\"true\" ");
		}

		writeDisabled(currentField, form, sb);

		sb.append("/>");
	}

    @Override
    public Object resolveDisplayValue(HttpServletRequest request, BeanWrapper beanWrapper, SectionField currentField, Object fieldValue) {
        String displayValue = "false";
        if (fieldValue != null &&
                ((fieldValue instanceof Boolean && Boolean.TRUE.equals(fieldValue)) || "true".equalsIgnoreCase(fieldValue.toString()))) {
            displayValue = "true";
        }
        return displayValue;
    }
}
