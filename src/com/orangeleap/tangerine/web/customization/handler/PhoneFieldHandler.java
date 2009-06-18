package com.orangeleap.tangerine.web.customization.handler;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;

public class PhoneFieldHandler extends GenericFieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

	
	public PhoneFieldHandler(ApplicationContext appContext) {
		super(appContext);
	}

	@Override
	public String getFieldPropertyName(SectionField sectionField) {
		FieldDefinition fieldDefinition = sectionField.getFieldDefinition();
		return fieldDefinition.getFieldName();
	}


}
