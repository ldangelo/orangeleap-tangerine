package com.mpower.web.customization;

import org.springframework.context.ApplicationContext;

import com.mpower.domain.entity.customization.FieldDefinition;
import com.mpower.domain.entity.customization.SectionField;

public class PhoneFieldHandler extends GenericFieldHandler {

	public PhoneFieldHandler(ApplicationContext appContext) {
		super(appContext);
	}

	@Override
	public String getFieldPropertyName(SectionField sectionField) {
		FieldDefinition fieldDefinition = sectionField.getFieldDefinition();
		return fieldDefinition.getFieldName() + ".number";
	}


}
