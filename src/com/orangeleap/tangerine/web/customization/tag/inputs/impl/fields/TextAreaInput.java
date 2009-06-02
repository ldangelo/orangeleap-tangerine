package com.orangeleap.tangerine.web.customization.tag.inputs.impl.fields;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.orangeleap.tangerine.web.customization.FieldVO;
import com.orangeleap.tangerine.web.customization.tag.inputs.AbstractInput;

@Component("textAreaInput")
public class TextAreaInput extends AbstractInput {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public String handleField(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO) {
        StringBuilder sb = new StringBuilder();
        sb.append("<textarea rows=\"5\" cols=\"30\" class=\"text ");
        sb.append(checkForNull(fieldVO.getEntityAttributes()));
        writeErrorClass(request, pageContext, sb);
        sb.append("\" ");
        if (fieldVO.isDisabled()) {
            sb.append("disabled=\"true\" ");
        }
        sb.append("name=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldName()) + "\" id=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "\">");
        sb.append(checkForNull(fieldVO.getDisplayValue()));
        sb.append("</textarea>");
        return sb.toString();
    }
}
