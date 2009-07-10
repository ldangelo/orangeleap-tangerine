package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.form;

import org.springframework.context.ApplicationContext;

/**
 * User: alexlo
 * Date: Jul 6, 2009
 * Time: 12:37:01 PM
 */
public class DateTimeHandler extends TextHandler {

	public DateTimeHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Override
	protected String getSize() {
	    return "16";
	}
	
}
