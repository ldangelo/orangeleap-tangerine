package com.mpower.web.customization;

import java.util.List;
import java.util.Locale;

import org.apache.commons.validator.GenericValidator;
import org.springframework.context.ApplicationContext;

import com.mpower.domain.User;
import com.mpower.domain.customization.SectionField;
import com.mpower.type.FieldType;
import com.mpower.type.MessageResourceType;

public class ExpirationFieldHandler extends GenericFieldHandler {

	public ExpirationFieldHandler(ApplicationContext appContext) {
		super(appContext);
	}

    @Override
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, User user, Object model) {
        FieldVO fieldVO = new FieldVO();

        fieldVO.setFieldName(getFieldPropertyName(currentField));
        fieldVO.setFieldType(FieldType.CC_EXPIRATION);

        String fieldLabelName = getFieldLabelName(currentField);
        fieldVO.setHelpText(messageService.lookupMessage(user.getSite(), MessageResourceType.FIELD_HELP, fieldLabelName, locale));
        fieldVO.setHelpAvailable(!GenericValidator.isBlankOrNull(fieldVO.getHelpText()));

        String labelText = messageService.lookupMessage(user.getSite(), MessageResourceType.FIELD_LABEL, fieldLabelName, locale);
        if (GenericValidator.isBlankOrNull(labelText)) {
            if (!currentField.isCompoundField()) {
                labelText = currentField.getFieldDefinition().getDefaultLabel();
            } else {
                labelText = currentField.getSecondaryFieldDefinition().getDefaultLabel();
            }
        }
        fieldVO.setLabelText(labelText);

        fieldVO.setRequired(fieldService.lookupFieldRequired(user.getSite(), currentField));

        return fieldVO;
    }
}
