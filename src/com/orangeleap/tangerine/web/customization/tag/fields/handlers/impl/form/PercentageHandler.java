package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.form;

import org.springframework.context.ApplicationContext;

/**
 * User: alexlo
 * Date: Jul 6, 2009
 * Time: 12:32:25 PM
 */
public class PercentageHandler extends TextHandler {

	public PercentageHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Override
	protected String getCssClass() {
		return new StringBuilder(super.getCssClass()).append(" percentage ").toString();
	}
}