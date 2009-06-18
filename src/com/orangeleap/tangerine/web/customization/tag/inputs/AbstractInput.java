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
