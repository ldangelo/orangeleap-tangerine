package com.orangeleap.tangerine.web.customization.tag.inputs.impl.display;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.orangeleap.tangerine.web.customization.FieldVO;
import com.orangeleap.tangerine.web.customization.tag.inputs.AbstractInput;

@Component("dateDisplayInput")
public class DateDisplayInput extends AbstractInput {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    public String getDateFormat() {
        return "MM / dd / yyyy";
    }

    @Override
    public String handleField(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div id=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "\" class=\"readOnlyField " + checkForNull(fieldVO.getEntityAttributes()) + "\">");
        SimpleDateFormat sdf = new SimpleDateFormat(getDateFormat());
        
        boolean isSet = false;
        if (fieldVO.getFieldValue() != null) {
            try {
                String formattedDate = sdf.format((Date)fieldVO.getFieldValue());
                sb.append(formattedDate);
                isSet = true;
            }
            catch (Exception ex) {
                logger.warn("handleField: could not format date = " + fieldVO.getFieldValue());
            }
        }
        if (isSet == false) {
            sb.append("&nbsp;");
        }
        sb.append("</div>");
        return sb.toString();
    }
}
