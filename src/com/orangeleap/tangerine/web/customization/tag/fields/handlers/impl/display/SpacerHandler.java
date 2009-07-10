package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.display;

import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.AbstractFieldHandler;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.controller.TangerineForm;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.util.List;

/**
 * User: alexlo
 * Date: Jul 7, 2009
 * Time: 4:27:14 PM
 */
public class SpacerHandler extends AbstractFieldHandler {

	public SpacerHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Override
	protected void writeLabel(SectionField sectionField, PageContext pageContext, StringBuilder sb) {
		sb.append("<label class=\"desc\"></label>");  // empty label text for spacers
	}

	@Override
	protected void doHandler(HttpServletRequest request, HttpServletResponse response, PageContext pageContext,
	                      SectionDefinition sectionDefinition, List<SectionField> sectionFields, SectionField currentField,
	                      TangerineForm form, String formFieldName, Object fieldValue, StringBuilder sb) {
		sb.append("&nbsp;");
	}
}
