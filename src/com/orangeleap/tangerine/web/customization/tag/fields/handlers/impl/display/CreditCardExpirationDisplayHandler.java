package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.display;

import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;

public class CreditCardExpirationDisplayHandler extends DateDisplayHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

	public CreditCardExpirationDisplayHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Override
    public String getDateFormat() {
        return "MM / yyyy";
    }
}