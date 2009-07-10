package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.lookups;

import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.form.TextHandler;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.controller.TangerineForm;
import org.springframework.context.ApplicationContext;
import org.apache.commons.lang.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.util.List;

/**
 * User: alexlo
 * Date: Jul 6, 2009
 * Time: 12:39:38 PM
 */
public class LookupHandler extends TextHandler {

	public LookupHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Override
	protected void doHandler(HttpServletRequest request, HttpServletResponse response, PageContext pageContext,
	                                  SectionDefinition sectionDefinition, List<SectionField> sectionFields,
	                     SectionField currentField, TangerineForm form, String formFieldName, Object fieldValue, StringBuilder sb) {
		super.doHandler(request, response, pageContext, sectionDefinition, sectionFields, currentField, form, formFieldName, fieldValue, sb);
		sb.append("<a class=\"lookupLink jqModal\" href=\"javascript:void(0)\">");
		sb.append(getMessage("lookup"));
		sb.append("</a>");
	}

	@Override
	protected String getCssClass() {
	    return new StringBuilder(StringEscapeUtils.escapeHtml(super.getCssClass())).append(" lookup ").toString();
	}
	
}
