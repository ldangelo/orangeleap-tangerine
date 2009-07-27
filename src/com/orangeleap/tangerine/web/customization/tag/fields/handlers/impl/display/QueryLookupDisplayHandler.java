package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.display;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.lookups.MultiQueryLookupHandler;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import javax.servlet.jsp.PageContext;

public class QueryLookupDisplayHandler extends MultiQueryLookupHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

	public QueryLookupDisplayHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Override
	protected String getSideCssClass(Object fieldValue) {
		return new StringBuilder(super.getSideCssClass(fieldValue)).append(" queryLookupLi").toString();
	}

	@Override
	protected void createMultiLookupOptions(PageContext pageContext, SectionField currentField, TangerineForm form, String formFieldName,
	                                     Object fieldValue, StringBuilder sb) {
		if (fieldValue != null && StringUtils.hasText(fieldValue.toString())) {
			super.createMultiLookupOptions(pageContext, currentField, form, formFieldName, fieldValue, sb);
		}
		else {
			String otherFormFieldName = resolveOtherFormFieldName(formFieldName);
			Object otherFieldValue = form.getFieldValueFromUnescapedFieldName(otherFormFieldName);

			if (otherFieldValue != null && StringUtils.hasText(otherFieldValue.toString())) {
				fieldValue = otherFieldValue;
			}
			super.createMultiLookupOptions(pageContext, currentField, form, formFieldName, fieldValue, sb);
		}
	}

	@Override
	protected String getContainerCssClass() {
		return "readOnly";
	}
}