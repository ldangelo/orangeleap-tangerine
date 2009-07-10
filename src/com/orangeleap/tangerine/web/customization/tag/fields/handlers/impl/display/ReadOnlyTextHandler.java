package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.display;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.AbstractFieldHandler;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.util.List;

public class ReadOnlyTextHandler extends AbstractFieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

	public ReadOnlyTextHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Override
	protected void doHandler(HttpServletRequest request, HttpServletResponse response, PageContext pageContext,
	                      SectionDefinition sectionDefinition, List<SectionField> sectionFields, SectionField currentField,
	                      TangerineForm form, String formFieldName, Object fieldValue, StringBuilder sb) {
		sb.append("<div id=\"").append(formFieldName).append("\" class=\"readOnlyField ");
		sb.append(resolveEntityAttributes(currentField)).append("\">");
		if (fieldValue == null || ! StringUtils.hasText(fieldValue.toString())) {
		    sb.append("&nbsp;");
		}
		else {
		    sb.append(fieldValue);
		}
		sb.append("</div>");
	}

}