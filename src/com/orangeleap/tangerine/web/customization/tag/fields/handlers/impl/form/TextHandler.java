package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.form;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.AbstractFieldHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.util.List;

public class TextHandler extends AbstractFieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

	public TextHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Override
	protected void doHandler(HttpServletRequest request, HttpServletResponse response, PageContext pageContext,
	                                  SectionDefinition sectionDefinition, List<SectionField> sectionFields,
	                     SectionField currentField, TangerineForm form, String formFieldName, Object fieldValue, StringBuilder sb) {
		sb.append("<input value=\"").append(checkForNull(fieldValue)).append("\" class=\"").append(StringEscapeUtils.escapeHtml(getCssClass()));
		sb.append(" ").append(resolveEntityAttributes(currentField));

		writeErrorClass(pageContext, formFieldName, sb);

		sb.append("\" ");

        writeTabIndex(currentField, sb);
		writeDisabled(currentField, form, sb);

		sb.append("size=\"").append(getSize()).append("\" maxLength=\"").append(getMaxLength()).append("\" ");
		sb.append("name=\"").append(formFieldName).append("\" id=\"").append(formFieldName).append("\" type=\"text\"/>");
	}

    protected String getCssClass() {
        return "text";
    }

    protected String getSize() {
        return StringConstants.EMPTY;
    }

    public String getMaxLength() {
        return StringConstants.EMPTY;
    }

}