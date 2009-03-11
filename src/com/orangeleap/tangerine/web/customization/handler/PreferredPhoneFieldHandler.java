package com.orangeleap.tangerine.web.customization.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.MessageResourceType;
import com.orangeleap.tangerine.web.customization.FieldVO;

public class PreferredPhoneFieldHandler extends GenericFieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());


    public PreferredPhoneFieldHandler(ApplicationContext appContext) {
        super(appContext);
    }

    /**
     * Reads each field on the page to get a list of phones types that are used to build the drop-down.
     */
    @Override
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, Object model) {
        FieldVO fieldVO = super.handleField(sectionFields, currentField, locale, model);
        fieldVO.setCodes(new ArrayList<String>());
        fieldVO.setDisplayValues(new ArrayList<String>());
        for (SectionField currentSectionField : sectionFields) {
            if (FieldType.PHONE == currentSectionField.getFieldDefinition().getFieldType()) {
                FieldDefinition currentFieldDefinition = currentSectionField.getFieldDefinition();
                String displayValue = messageService.lookupMessage(MessageResourceType.FIELD_LABEL, currentSectionField.getFieldLabelName(), locale);
                if (GenericValidator.isBlankOrNull(displayValue)) {
                    displayValue = currentSectionField.getFieldDefinition().getDefaultLabel();
                }
                fieldVO.getDisplayValues().add(displayValue);

                fieldVO.getCodes().add(currentFieldDefinition.getFieldName());
            }
        }
        return fieldVO;
    }
}
