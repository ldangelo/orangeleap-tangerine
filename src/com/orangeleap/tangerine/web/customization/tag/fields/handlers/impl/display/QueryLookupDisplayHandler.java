package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.display;

import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.lookups.MultiQueryLookupHandler;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;

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
	protected String getContainerCssClass() {
		return "readOnly";
	}
}