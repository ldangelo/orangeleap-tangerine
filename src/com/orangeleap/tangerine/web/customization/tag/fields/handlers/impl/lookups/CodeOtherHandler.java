package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.lookups;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import org.springframework.context.ApplicationContext;

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
	protected void getDisplayAttributes(String fieldPropertyName, String formFieldName, StringBuilder sb) {
	    super.getDisplayAttributes(fieldPropertyName, formFieldName, sb);
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
		if (fieldValue != null) {
			displayValue = super.getCodeDisplayValue(sectionDefinition, currentField, form, formFieldName, fieldValue);
		}
		else {
			String otherFormFieldName = resolveOtherFormFieldName(formFieldName);
			displayValue = form.getFieldValue(otherFormFieldName);
		}
		return displayValue;
	}
}
