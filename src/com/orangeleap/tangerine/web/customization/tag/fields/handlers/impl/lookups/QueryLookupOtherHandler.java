package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.lookups;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.beans.BeanWrapper;

import javax.servlet.http.HttpServletRequest;

public class QueryLookupOtherHandler extends QueryLookupHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

	public QueryLookupOtherHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Override
	protected String createOptionText(HttpServletRequest request, SectionField currentField, TangerineForm form, String formFieldName,
		                  Object fieldValue, StringBuilder sb) {
		Object otherFieldValue = null;
		if (fieldValue != null && StringUtils.hasText(fieldValue.toString())) {
			super.createOptionText(request, currentField, form, formFieldName, fieldValue, sb);
		}
		else {
			String otherFormFieldName = resolveOtherFormFieldName(formFieldName);
			otherFieldValue = form.getFieldValueFromUnescapedFieldName(otherFormFieldName);

			if (otherFieldValue != null && StringUtils.hasText(otherFieldValue.toString())) {
				sb.append("<span>").append(otherFieldValue).append("</span>");
			}
		}
		return otherFieldValue == null ? StringConstants.EMPTY : otherFieldValue.toString();
	}

	@Override
    protected String getQueryClickHandler() {
        return "Lookup.loadQueryLookup(this, true)";
    }

	@Override
	protected void createHiddenField(SectionField currentField, String formFieldName, Object fieldValue, StringBuilder sb) {
	    sb.append("<input type=\"hidden\" name=\"").append(formFieldName).append("\" value=\"").append(checkForNull(fieldValue)).append("\" id=\"").append(formFieldName).append("\" ");
		sb.append("otherFieldId=\"").append(resolveOtherFormFieldName(formFieldName)).append("\" ");
		sb.append("/>");
	}

    @Override
    public Object resolveDisplayValue(HttpServletRequest request, BeanWrapper beanWrapper, SectionField currentField, Object fieldValue) {
        Object displayValue = StringConstants.EMPTY;
        if (fieldValue != null && StringUtils.hasText(fieldValue.toString())) {
            displayValue = super.resolveDisplayValue(request, beanWrapper, currentField, fieldValue);
        }
        else {
            String otherFieldName = resolvedUnescapedPrefixedFieldName(StringConstants.OTHER_PREFIX, currentField.getFieldPropertyName());
            Object otherFieldValue = beanWrapper.getPropertyValue(otherFieldName);
            if (otherFieldValue instanceof CustomField) {
                otherFieldValue = ((CustomField) otherFieldValue).getValue();
            }

            if (otherFieldValue != null && StringUtils.hasText(otherFieldValue.toString())) {
                displayValue = otherFieldValue;
            }
        }
        return displayValue;
    }
}