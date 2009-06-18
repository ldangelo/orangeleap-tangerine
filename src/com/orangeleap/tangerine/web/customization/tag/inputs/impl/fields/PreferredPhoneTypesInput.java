package com.orangeleap.tangerine.web.customization.tag.inputs.impl.fields;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.FieldVO;
import com.orangeleap.tangerine.web.customization.tag.inputs.AbstractInput;

@Component("preferredPhoneTypesInput")
public class PreferredPhoneTypesInput extends AbstractInput {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Override
    public String handleField(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO) {
        StringBuilder sb = new StringBuilder();
        createSelectBegin(request, response, pageContext, fieldVO, sb);
        String selectedRef = createOptions(request, response, pageContext, fieldVO, sb);
        createSelectEnd(request, response, pageContext, fieldVO, sb);
        createSelectedRef(request, response, pageContext, fieldVO, sb, selectedRef);
        return sb.toString();
    }

    protected void createSelectBegin(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<select name=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldName()) + "\" class=\"");
        if (fieldVO.isCascading()) {
            sb.append("picklist ");
        }
        sb.append(checkForNull(fieldVO.getEntityAttributes()));
        sb.append("\" id=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "\" references=\"" + checkForNull(fieldVO.getUniqueReferenceValues()) + "\">");
    }
    
    protected String createOptions(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        if (fieldVO.isRequired() == false) {
            sb.append("<option value=\"none\">").append(getMessage("none")).append("</option>");
        }
        String selectedRef = StringConstants.EMPTY;
        List<String> codes = fieldVO.getCodes();
        List<String> refValues = fieldVO.getReferenceValues();
        List<Object> displayValues = fieldVO.getDisplayValues();
        if (codes != null) {
            for (int i = 0; i < codes.size(); i++) {
                String thisCode = codes.get(i);
                sb.append("<option value=\"" + StringEscapeUtils.escapeHtml(thisCode) + "\" ");
                if (refValues != null && i >= 0 && i < refValues.size()) {
                    sb.append("reference=\"" + StringEscapeUtils.escapeHtml(refValues.get(i)) + "\" ");
                }
                if (thisCode.equals(fieldVO.getFieldValue())) {
                    sb.append("selected=\"selected\" ");
                    if (refValues != null && i >= 0 && i < refValues.size()) {
                        selectedRef = refValues.get(i);
                    }
                }
                sb.append(">");
                
                if (displayValues != null && i >= 0 && i < displayValues.size()) {
                    sb.append(StringEscapeUtils.escapeHtml(displayValues.get(i).toString()));
                }
                sb.append("</option>");
            }
        }
        return selectedRef;
    }
    
    protected void createSelectEnd(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("</select>");
    }
    
    protected void createSelectedRef(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb, String selectedRef) {
        sb.append("<div style=\"display:none\" id=\"selectedRef-" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "\">");
        
        if (fieldVO.isRequired() && StringUtils.hasText(selectedRef) == false) {
            List<String> refValues = fieldVO.getReferenceValues();
            if (refValues != null && refValues.size() >= 1) {
                selectedRef = refValues.get(0);
            }
            if (StringUtils.hasText(selectedRef)) {
                sb.append(StringEscapeUtils.escapeHtml(selectedRef));
            }
        }
        sb.append("</div>");
    }
}
