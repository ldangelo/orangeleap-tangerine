package com.mpower.web.customization;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.mpower.domain.customization.FieldDefinition;
import com.mpower.domain.customization.SectionField;

public class PhoneFieldHandler extends GenericFieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	
	public PhoneFieldHandler(ApplicationContext appContext) {
		super(appContext);
	}

	@Override
	public String getFieldPropertyName(SectionField sectionField) {
		FieldDefinition fieldDefinition = sectionField.getFieldDefinition();
		return fieldDefinition.getFieldName() + ".number";
	}


}
