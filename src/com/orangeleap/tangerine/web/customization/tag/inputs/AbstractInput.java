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

package com.orangeleap.tangerine.web.customization.tag.inputs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.stereotype.Component;

import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineMessageAccessor;
import com.orangeleap.tangerine.web.customization.FieldVO;

@Component
public abstract class AbstractInput {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    protected String getMessage(String code) {
        return getMessage(code, null);
    }
    
    protected String getMessage(String code, String[] args) {
        return TangerineMessageAccessor.getMessage(code, args);
    }
    
    public String checkForNull(Object obj) {
        return obj == null ? StringConstants.EMPTY : StringEscapeUtils.escapeHtml(obj.toString());
    }
    
    public void writeErrorClass(HttpServletRequest request, PageContext pageContext, StringBuilder sb) {
        if (pageContext.getAttribute(StringConstants.ERROR_CLASS) != null) {
            sb.append(" ").append(pageContext.getAttribute(StringConstants.ERROR_CLASS));
        }
    }
    
    public abstract String handleField(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO);
}
