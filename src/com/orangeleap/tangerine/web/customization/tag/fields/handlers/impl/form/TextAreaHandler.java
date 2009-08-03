package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.form;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.AbstractFieldHandler;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.util.List;

/**
 * User: alexlo
 * Date: Jul 6, 2009
 * Time: 12:45:45 PM
 */
public class TextAreaHandler extends AbstractFieldHandler {

	public TextAreaHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Override
	protected void doHandler(HttpServletRequest request, HttpServletResponse response, PageContext pageContext,
	                                  SectionDefinition sectionDefinition, List<SectionField> sectionFields,
	                     SectionField currentField, TangerineForm form, String formFieldName, Object fieldValue, StringBuilder sb) {
		sb.append("<textarea rows=\"7\" cols=\"30\" class=\"text ");
		sb.append(resolveEntityAttributes(currentField));

		writeErrorClass(pageContext, formFieldName, sb);

		sb.append("\" ");

		writeDisabled(currentField, form, sb);

		sb.append("name=\"").append(formFieldName).append("\" id=\"").append(formFieldName).append("\">");
		sb.append(checkForNull(fieldValue));
		sb.append("</textarea>");
	}
}
