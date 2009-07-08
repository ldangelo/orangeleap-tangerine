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

package com.orangeleap.tangerine.web.customization.tag.inputs.impl.fields;

import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.web.customization.FieldVO;
import com.orangeleap.tangerine.web.customization.tag.inputs.AbstractInput;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

@Component("checkboxInput")
public class CheckboxInput extends AbstractInput {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Override
    public String handleField(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO) {
        StringBuilder sb = new StringBuilder();
        sb.append("<input type=\"hidden\" name=\"_" + StringEscapeUtils.escapeHtml(fieldVO.getFieldName()) + "\"/>");
        sb.append("<input type=\"checkbox\" value=\"true\" class=\"checkbox " + checkForNull(fieldVO.getEntityAttributes()) + "\" name=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldName()) + "\" ");
        sb.append("id=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "\" ");
        if (fieldVO.getFieldValue() != null &&
                ((fieldVO.getFieldValue() instanceof Boolean && Boolean.TRUE.equals(fieldVO.getFieldValue())) || "true".equalsIgnoreCase(fieldVO.getFieldValue().toString()))) {
            sb.append("checked=\"true\" ");
        }
        if (fieldVO.isDisabled()) {
            sb.append("disabled=\"true\" ");
        }
        sb.append("/>");
        return sb.toString();
    }
}
