package com.orangeleap.tangerine.web.customization.tag.inputs.impl.fields;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.stereotype.Component;

import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.FieldVO;
import com.orangeleap.tangerine.web.customization.tag.inputs.AbstractInput;

@Component("textInput")
public class TextInput extends AbstractInput {

    /** Logger for this class and subclasses */
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
