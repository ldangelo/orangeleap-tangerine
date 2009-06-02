package com.orangeleap.tangerine.web.customization.tag.inputs.impl.lookups;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.orangeleap.tangerine.web.customization.FieldVO;
import com.orangeleap.tangerine.web.customization.tag.inputs.AbstractInput;

@Component("codeInput")
public class CodeInput extends AbstractInput {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public String handleField(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO) {
        StringBuilder sb = new StringBuilder();
        createLookupWrapperBegin(request, response, pageContext, fieldVO, sb);
        createDisplayInput(request, response, pageContext, fieldVO, sb);
        createHiddenInput(request, response, pageContext, fieldVO, sb);
        createLookup(request, response, pageContext, fieldVO, sb);
        createLookupWrapperEnd(request, response, pageContext, fieldVO, sb);
        return sb.toString();
    }

    protected void createLookupWrapperBegin(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<div class=\"lookupWrapper\">");
    }
    
    protected void createDisplayInput(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<input value=\"" + checkForNull(fieldVO.getDisplayValue()) + "\" class=\"text code " + checkForNull(fieldVO.getEntityAttributes()));
        writeErrorClass(request, pageContext, sb);
        sb.append("\" " + getDisplayAttributes(fieldVO));
        sb.append("name=\"display-" + StringEscapeUtils.escapeHtml(fieldVO.getFieldName()) + "\" id=\"display-" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "\"/>");
    }
    
    protected void createHiddenInput(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<input type=\"hidden\" name=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldName()) + "\" id=\"hidden-" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "\" value=\"" + checkForNull(fieldVO.getFieldValue()) + "\"/>");
    }
    
    protected void createLookup(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<a class=\"lookupLink\" href=\"javascript:void(0)\" ");
        sb.append("onclick=\"" + getLookupClickHandler() + "\" ");
        String lookupMsg = getMessage("lookup");
        sb.append("alt=\"").append(lookupMsg).append("\" title=\"").append(lookupMsg).append("\">").append(lookupMsg).append("</a>");
    }
    
    protected void createLookupWrapperEnd(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("</div>");
    }
    
    protected String getLookupClickHandler() {
        return "Lookup.loadCodePopup(this)";
    }
    
    protected String getDisplayAttributes(FieldVO fieldVO) {
        return "lookup=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldName()) + "\" codeType=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldName()) + "\" ";
    }
}
