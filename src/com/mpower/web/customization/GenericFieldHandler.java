package com.mpower.web.customization;

import java.util.List;
import java.util.Locale;

import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.context.ApplicationContext;

import com.mpower.domain.entity.User;
import com.mpower.domain.entity.customization.SectionField;
import com.mpower.domain.type.FieldType;
import com.mpower.domain.type.MessageResourceType;
import com.mpower.service.customization.FieldService;
import com.mpower.service.customization.MessageService;

public class GenericFieldHandler implements FieldHandler {

	protected FieldService fieldService;
	protected MessageService messageService;

	public GenericFieldHandler(ApplicationContext appContext) {
		messageService = (MessageService) appContext.getBean("messageService");
		fieldService = (FieldService) appContext.getBean("fieldService");
	}

	public String getFieldLabelName(SectionField sectionField) {
		return sectionField.getFieldLabelName();
	}

	public String getFieldRequiredName(SectionField sectionField) {
		return sectionField.getFieldRequiredName();
	}

	public String getFieldPropertyName(SectionField sectionField) {
		String fieldPropertyName = sectionField.getFieldPropertyName();

		if (sectionField.getFieldDefinition().isCustom()) {
			fieldPropertyName += ".value";
		}
		return fieldPropertyName;
	}

	public FieldType getFieldType(SectionField sectionField) {
		return sectionField.getFieldType();
	}

	public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, User user, Object model) {
		FieldVO fieldVO = new FieldVO();

		fieldVO.setFieldName(getFieldPropertyName(currentField));
		fieldVO.setFieldType(getFieldType(currentField));

		String fieldLabelName = getFieldLabelName(currentField);
		fieldVO.setHelpText(messageService.lookupMessage(user.getSite(), MessageResourceType.FIELD_HELP, fieldLabelName, locale));
		fieldVO.setHelpAvailable(! GenericValidator.isBlankOrNull(fieldVO.getHelpText()));

		String labelText = messageService.lookupMessage(user.getSite(), MessageResourceType.FIELD_LABEL, fieldLabelName, locale);
		if (GenericValidator.isBlankOrNull(labelText)) {
			if (! currentField.isCompoundField()) {
				labelText = currentField.getFieldDefinition().getDefaultLabel();
			} else {
				labelText = currentField.getSecondaryFieldDefinition().getDefaultLabel();
			}
		}
        fieldVO.setLabelText(labelText);

        fieldVO.setRequired(fieldService.lookupFieldRequired(user.getSite(), currentField));

        if (!FieldType.SPACER.equals(fieldVO.getFieldType())) {
            String fieldProperty = fieldVO.getFieldName();
            BeanWrapper personBeanWrapper = new BeanWrapperImpl(model);
            try {
                fieldVO.setFieldValue((String) personBeanWrapper.getPropertyValue(fieldProperty));
            } finally {
                model = null;
            }
        }

		return fieldVO;
	}
}
