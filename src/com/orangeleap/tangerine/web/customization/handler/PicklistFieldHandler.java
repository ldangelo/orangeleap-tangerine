package com.orangeleap.tangerine.web.customization.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.type.MessageResourceType;
import com.orangeleap.tangerine.web.customization.FieldVO;

public class PicklistFieldHandler extends GenericFieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    public PicklistFieldHandler(ApplicationContext appContext) {
        super(appContext);
    }

    @Override
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, Object model) {
        FieldVO fieldVO = super.handleField(sectionFields, currentField, locale, model);
        fieldVO.setCodes(new ArrayList<String>());
        fieldVO.setDisplayValues(new ArrayList<String>());
        fieldVO.setReferenceValues(new ArrayList<String>());
        EntityType entityType = currentField.getSecondaryFieldDefinition() != null ? currentField.getSecondaryFieldDefinition().getEntityType() : currentField.getFieldDefinition().getEntityType();
        Picklist picklist = fieldService.readPicklistByFieldNameEntityType(currentField.getPicklistName(), entityType);
        if (picklist != null) {
            for (Iterator<PicklistItem> iterator = picklist.getActivePicklistItems().iterator(); iterator.hasNext();) {
                PicklistItem item = iterator.next();
                fieldVO.getCodes().add(item.getItemName());
                String displayValue = messageService.lookupMessage(MessageResourceType.PICKLIST_VALUE, item.getItemName(), locale);
                if (GenericValidator.isBlankOrNull(displayValue)) {
                    displayValue = item.getDefaultDisplayValue();
                }
                fieldVO.getDisplayValues().add(displayValue);
                fieldVO.getReferenceValues().add(item.getReferenceValue());
                if (fieldVO.getFieldValue()!=null && fieldVO.getFieldValue().equals(item.getItemName())){
                    fieldVO.setDisplayValue(displayValue);
                }
            }
            for (Iterator<String> iterator = fieldVO.getReferenceValues().iterator(); iterator.hasNext();) {
                String refVal = iterator.next();
                if (!GenericValidator.isBlankOrNull(refVal)) {
                    fieldVO.setCascading(true);
                }
            }
        }
        return fieldVO;
    }
}
