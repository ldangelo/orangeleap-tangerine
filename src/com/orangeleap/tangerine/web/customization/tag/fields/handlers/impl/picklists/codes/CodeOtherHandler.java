package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.picklists.codes;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.util.StringConstants;
import org.springframework.beans.BeanWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * User: alexlo
 * Date: Jul 7, 2009
 * Time: 11:16:31 AM
 */
public class CodeOtherHandler extends CodeHandler {

	public CodeOtherHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Override
	protected void getDisplayAttributes(SectionDefinition sectionDefinition, SectionField currentField, TangerineForm form, String formFieldName, StringBuilder sb) {
	    super.getDisplayAttributes(sectionDefinition, currentField, form, formFieldName, sb);
		sb.append(" otherFieldId=\"").append(resolveOtherFormFieldName(formFieldName)).append("\" ");
	}

	@Override
	protected String getLookupClickHandler() {
	    return "Lookup.loadCodePopup(this, true)";
	}

	@Override
	protected Object getCodeDisplayValue(SectionDefinition sectionDefinition, SectionField currentField,
	                                  TangerineForm form, String formFieldName, Object fieldValue) {
		Object displayValue;
		if (fieldValue != null && StringUtils.hasText(fieldValue.toString())) {
			displayValue = super.getCodeDisplayValue(sectionDefinition, currentField, form, formFieldName, fieldValue);
		}
		else {
			String otherFormFieldName = resolveOtherFormFieldName(formFieldName);
			displayValue = form.getFieldValueFromUnescapedFieldName(otherFormFieldName);
		}
		return displayValue;
	}

    @Override
    public Object resolveDisplayValue(HttpServletRequest request, BeanWrapper beanWrapper, SectionField currentField, Object fieldValue) {
        Object displayValue;
        if (fieldValue != null && StringUtils.hasText(fieldValue.toString())) {
            displayValue = super.resolveDisplayValue(request, beanWrapper, currentField, fieldValue);
        }
        else {
            String otherFieldName = resolvedUnescapedPrefixedFieldName(StringConstants.OTHER_PREFIX, currentField.getFieldPropertyName());
            displayValue = beanWrapper.getPropertyValue(otherFieldName);
            if (displayValue instanceof CustomField) {
                displayValue = ((CustomField) displayValue).getValue();
            }
        }
        return displayValue == null ? StringConstants.EMPTY : displayValue;
    }
}
