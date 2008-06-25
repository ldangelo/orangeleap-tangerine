package com.mpower.web.customization;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.validator.GenericValidator;
import org.springframework.context.ApplicationContext;

import com.mpower.domain.User;
import com.mpower.domain.customization.Picklist;
import com.mpower.domain.customization.PicklistItem;
import com.mpower.domain.customization.SectionField;
import com.mpower.type.MessageResourceType;

public class PicklistFieldHandler extends GenericFieldHandler {

    public PicklistFieldHandler(ApplicationContext appContext) {
        super(appContext);
    }

    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, User user, Object model) {
        FieldVO fieldVO = super.handleField(sectionFields, currentField, locale, user, model);
        fieldVO.codes = new ArrayList<String>();
        fieldVO.displayValues = new ArrayList<String>();
        Picklist picklist = fieldService.readPicklistBySiteAndFieldName(user.getSite(), currentField.getPicklistName());
        if (picklist != null) {
            for (Iterator<PicklistItem> iterator = picklist.getPicklistItems().iterator(); iterator.hasNext();) {
                PicklistItem item = iterator.next();
                fieldVO.codes.add(item.getItemName());
                String displayValue = messageService.lookupMessage(user.getSite(), MessageResourceType.PICKLIST_VALUE, item.getItemName(), locale);
                if (GenericValidator.isBlankOrNull(displayValue)) {
                    displayValue = item.getDefaultDisplayValue();
                }
                fieldVO.displayValues.add(displayValue);
            }
        }
        return fieldVO;
    }
}
