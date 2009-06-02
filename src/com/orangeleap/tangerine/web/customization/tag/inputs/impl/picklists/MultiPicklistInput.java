package com.orangeleap.tangerine.web.customization.tag.inputs.impl.picklists;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.orangeleap.tangerine.web.customization.FieldVO;
import com.orangeleap.tangerine.web.customization.tag.inputs.AbstractInput;

public class MultiPicklistInput extends AbstractInput {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public String handleField(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO) {
        return null;
    }

    protected void createTop(HttpServletRequest request, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<div class=\"lookupScrollTop");
        writeErrorClass(request, pageContext, sb);
        sb.append("\"></div>");
    }
    
    protected void createContainerBegin(HttpServletRequest request, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<div class=\"lookupScrollContainer");
        writeErrorClass(request, pageContext, sb);
    }
    
    protected void createMultiPicklistBegin(HttpServletRequest request, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<div class=\"multiPicklist multiLookupField " + checkForNull(fieldVO.getEntityAttributes()) + "\" id=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "\" references=\"" + checkForNull(fieldVO.getUniqueReferenceValues()) + "\">");
    }
    
    protected void createLeft(HttpServletRequest request, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<div class=\"lookupScrollLeft\"></div>");
    }
    
    protected void createMultiPicklistOptions(HttpServletRequest request, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        List<String> codes = fieldVO.getCodes();
        if (codes != null) {
            for (int x = 0; x < codes.size(); x++) {
                String thisCode = codes.get(x);
                sb.append("<div class=\"multiPicklistOption multiOption\" style=\"");
                if (fieldVO.isHasField(thisCode) == false) {
                    sb.append("display:none");
                }
                sb.append("\" id=\"option-" + StringEscapeUtils.escapeHtml(thisCode) + "\" selectedId=\"" + StringEscapeUtils.escapeHtml(thisCode) + "\" reference=\"" + checkForNull(fieldVO.getReferenceValues().get(x)) + "\">");
                sb.append(StringEscapeUtils.escapeHtml(fieldVO.getDisplayValues().get(x).toString()));
                sb.append("</div>");
            }
        }
    }
    
    protected void createLabelTextInput(HttpServletRequest request, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<input type=\"hidden\" name=\"labelText\" id=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "-labelText\" value=\"" + StringEscapeUtils.escapeHtml(fieldVO.getLabelText()) + "\"/>");
    }
    
    protected void createRight(HttpServletRequest request, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<div class=\"lookupScrollRight\"></div>");
    }
    
    protected void createMultiPicklistEnd(HttpServletRequest request, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("</div>");
    }
    
    protected void createHiddenInput(HttpServletRequest request, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<input type=\"hidden\" name=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldName()) + "\" id=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "\" value=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldValuesString()) + "\" additionalFieldId=\"" + StringEscapeUtils.escapeHtml(fieldVO.getAdditionalFieldId()) + "\"/>");
    }
    
    protected void createContainerEnd(HttpServletRequest request, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("</div>");
    }
    
    protected void createBottom(HttpServletRequest request, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<div class=\"lookupScrollBottom");
        writeErrorClass(request, pageContext, sb);
        sb.append("\"></div>");
    }
}
