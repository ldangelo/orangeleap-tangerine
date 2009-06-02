package com.orangeleap.tangerine.web.customization.tag.inputs.impl.display;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.orangeleap.tangerine.web.customization.FieldVO;
import com.orangeleap.tangerine.web.customization.tag.inputs.AbstractInput;

@Component("readOnlyTextInput")
public class ReadOnlyTextInput extends AbstractInput {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public String handleField(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div id=\"" + fieldVO.getFieldId() + "\" class=\"readOnlyField " + checkForNull(fieldVO.getEntityAttributes()) + "\">");
        if (fieldVO.getDisplayValue() == null || StringUtils.hasText(fieldVO.getDisplayValue().toString()) == false) {
            sb.append("&nbsp;");
        }
        else {
            sb.append(fieldVO.getDisplayValue());
        }
        sb.append("</div>");
        return sb.toString();
    }
}
