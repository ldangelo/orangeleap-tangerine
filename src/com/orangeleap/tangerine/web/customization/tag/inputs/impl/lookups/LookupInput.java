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

package com.orangeleap.tangerine.web.customization.tag.inputs.impl.lookups;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Component;

import com.orangeleap.tangerine.web.customization.FieldVO;
import com.orangeleap.tangerine.web.customization.tag.inputs.impl.fields.TextInput;

@Component("lookupInput")
public class LookupInput extends TextInput {

    @Override
    protected void createInput(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        super.createInput(request, response, pageContext, fieldVO, sb);
        sb.append("<a class=\"lookupLink jqModal\" href=\"javascript:void(0)\">");
        sb.append(getMessage("lookup"));
        sb.append("</a>");
    }

    @Override
    protected String getCssClass() {
        return new StringBuilder(StringEscapeUtils.escapeHtml(super.getCssClass())).append(" lookup ").toString();
    }
}
