package com.mpower.web.customization.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.springframework.context.ApplicationContext;

import com.mpower.domain.customization.Picklist;
import com.mpower.domain.customization.PicklistItem;
import com.mpower.domain.customization.SectionField;
import com.mpower.type.EntityType;
import com.mpower.type.MessageResourceType;
import com.mpower.web.customization.FieldVO;

public class PicklistFieldHandler extends GenericFieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    public PicklistFieldHandler(ApplicationContext appContext) {
        super(appContext);
    }

    @Override
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, String siteName, Object model) {
        FieldVO fieldVO = super.handleField(sectionFields, currentField, locale, siteName, model);
        fieldVO.setCodes(new ArrayList<String>());
        fieldVO.setDisplayValues(new ArrayList<String>());
        fieldVO.setReferenceValues(new ArrayList<String>());
        EntityType entityType = currentField.getSecondaryFieldDefinition() != null ? currentField.getSecondaryFieldDefinition().getEntityType() : currentField.getFieldDefinition().getEntityType();
        Picklist picklist = fieldService.readPicklistBySiteAndFieldName(siteName, currentField.getPicklistName(), entityType);
        if (picklist != null) {
            for (Iterator<PicklistItem> iterator = picklist.getPicklistItems().iterator(); iterator.hasNext();) {
                PicklistItem item = iterator.next();
                fieldVO.getCodes().add(item.getItemName());
                String displayValue = messageService.lookupMessage(siteName, MessageResourceType.PICKLIST_VALUE, item.getItemName(), locale);
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
