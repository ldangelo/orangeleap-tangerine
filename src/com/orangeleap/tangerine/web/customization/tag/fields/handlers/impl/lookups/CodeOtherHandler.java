package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.lookups;

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
	protected void getDisplayAttributes(String fieldPropertyName, StringBuilder sb) {
	    super.getDisplayAttributes(fieldPropertyName, sb);
		sb.append(" otherFieldId=\"").append(resolveOtherFieldPropertyName(fieldPropertyName)).append("\" ");
	}

	@Override
	protected String getLookupClickHandler() {
	    return "Lookup.loadCodePopup(this, true)";
	}

}
