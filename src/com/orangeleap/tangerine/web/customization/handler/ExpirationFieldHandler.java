package com.orangeleap.tangerine.web.customization.handler;

import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.MessageResourceType;
import com.orangeleap.tangerine.web.customization.FieldVO;

public class ExpirationFieldHandler extends GenericFieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    public ExpirationFieldHandler(ApplicationContext appContext) {
        super(appContext);
    }

    @Override
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, Object model) {
        FieldVO fieldVO = new FieldVO();

        fieldVO.setFieldName(getFieldPropertyName(currentField));
        fieldVO.setFieldType(FieldType.CC_EXPIRATION);

        String fieldLabelName = getFieldLabelName(currentField);
        fieldVO.setHelpText(messageService.lookupMessage(MessageResourceType.FIELD_HELP, fieldLabelName, locale));
        fieldVO.setHelpAvailable(!GenericValidator.isBlankOrNull(fieldVO.getHelpText()));

        String labelText = messageService.lookupMessage(MessageResourceType.FIELD_LABEL, fieldLabelName, locale);
        if (GenericValidator.isBlankOrNull(labelText)) {
            if (!currentField.isCompoundField()) {
                labelText = currentField.getFieldDefinition().getDefaultLabel();
            } else {
                labelText = currentField.getSecondaryFieldDefinition().getDefaultLabel();
            }
        }
        fieldVO.setLabelText(labelText);
        setEntityAttributes(fieldVO, currentField);

        FieldRequired fr = fieldService.lookupFieldRequired(currentField);
        fieldVO.setRequired(fr != null && fr.isRequired());

        return fieldVO;
    }
}
