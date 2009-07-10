package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.display;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.AbstractFieldHandler;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DateDisplayHandler extends AbstractFieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    public String getDateFormat() {
        return "MM / dd / yyyy";
    }

	public DateDisplayHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Override
	protected void doHandler(HttpServletRequest request, HttpServletResponse response, PageContext pageContext,
	                      SectionDefinition sectionDefinition, List<SectionField> sectionFields, SectionField currentField,
	                      TangerineForm form, String formFieldName, Object fieldValue, StringBuilder sb) {
		sb.append("<div id=\"").append(formFieldName).append("\" class=\"readOnlyField ").append(resolveEntityAttributes(currentField)).append("\">");
		SimpleDateFormat sdf = new SimpleDateFormat(getDateFormat());

		boolean isSet = false;
		if (fieldValue != null && fieldValue instanceof Date) {
		    try {
		        String formattedDate = sdf.format((Date) fieldValue);
		        sb.append(formattedDate);
		        isSet = true;
		    }
		    catch (Exception ex) {
		        logger.warn("doHandler: could not format date = " + fieldValue);
		    }
		}
		if ( ! isSet) {
		    sb.append("&nbsp;");
		}
		sb.append("</div>");
	}
}