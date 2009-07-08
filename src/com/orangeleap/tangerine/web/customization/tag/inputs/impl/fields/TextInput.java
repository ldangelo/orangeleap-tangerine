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
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.FieldVO;
import com.orangeleap.tangerine.web.customization.tag.inputs.AbstractInput;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

@Component("textInput")
public class TextInput extends AbstractInput {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Override
    public String handleField(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO) {
        StringBuilder sb = new StringBuilder();
        createInput(request, response, pageContext, fieldVO, sb);
        return sb.toString();
    }

    protected void createInput(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<input value=\"" + checkForNull(fieldVO.getFieldValue()) + "\" class=\"" + StringEscapeUtils.escapeHtml(getCssClass()) + " " + checkForNull(fieldVO.getEntityAttributes()));
        writeErrorClass(request, pageContext, sb);
        sb.append("\" ");
        if (fieldVO.isDisabled()) {
            sb.append("disabled=\"true\" ");
        }
        sb.append("size=\"" + getSize() + "\" maxLength=\"" + getMaxLength() + "\" ");
        sb.append("name=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldName()) + "\" id=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "\" type=\"text\"/>");
    }

    protected String getCssClass() {
        return "text";
    }

    protected String getSize() {
        return StringConstants.EMPTY;
    }

    public String getMaxLength() {
        return StringConstants.EMPTY;
    }

}
