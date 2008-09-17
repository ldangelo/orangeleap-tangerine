package com.mpower.web.customization;

import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.context.ApplicationContext;

import com.mpower.domain.customization.FieldRequired;
import com.mpower.domain.customization.SectionField;
import com.mpower.service.customization.FieldService;
import com.mpower.service.customization.MessageService;
import com.mpower.type.FieldType;
import com.mpower.type.MessageResourceType;

public class GenericFieldHandler implements FieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

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

    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, String siteName, Object model) {
        FieldVO fieldVO = new FieldVO();

        fieldVO.setFieldName(getFieldPropertyName(currentField));
        fieldVO.setFieldType(getFieldType(currentField));

        String fieldLabelName = getFieldLabelName(currentField);
        fieldVO.setHelpText(messageService.lookupMessage(siteName, MessageResourceType.FIELD_HELP, fieldLabelName, locale));
        fieldVO.setHelpAvailable(!GenericValidator.isBlankOrNull(fieldVO.getHelpText()));

        String labelText = messageService.lookupMessage(siteName, MessageResourceType.FIELD_LABEL, fieldLabelName, locale);
        if (GenericValidator.isBlankOrNull(labelText)) {
            if (!currentField.isCompoundField()) {
                labelText = currentField.getFieldDefinition().getDefaultLabel();
            } else {
                labelText = currentField.getSecondaryFieldDefinition().getDefaultLabel();
            }
        }
        fieldVO.setLabelText(labelText);

        FieldRequired fr = fieldService.lookupFieldRequired(siteName, currentField);
        fieldVO.setRequired(fr != null && fr.isRequired());

        if (!FieldType.SPACER.equals(fieldVO.getFieldType())) {
            String fieldProperty = fieldVO.getFieldName();
            BeanWrapper personBeanWrapper = new BeanWrapperImpl(model);
            fieldVO.setFieldValue(personBeanWrapper.getPropertyValue(fieldProperty));
        }

        return fieldVO;
    }
}
