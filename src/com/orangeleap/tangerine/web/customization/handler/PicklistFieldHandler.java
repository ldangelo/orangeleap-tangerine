/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.web.customization.handler;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
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
    protected final Log logger = OLLogger.getLog(getClass());

    public PicklistFieldHandler(ApplicationContext appContext) {
        super(appContext);
    }

    @Override
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, Object model) {
        FieldVO fieldVO = super.handleField(sectionFields, currentField, locale, model);
        EntityType entityType = currentField.getSecondaryFieldDefinition() != null ? currentField.getSecondaryFieldDefinition().getEntityType() : currentField.getFieldDefinition().getEntityType();
        Picklist picklist = fieldService.readPicklistByFieldNameEntityType(currentField.getPicklistName(), entityType);
        if (picklist != null) {
            for (Iterator<PicklistItem> iterator = picklist.getActivePicklistItems().iterator(); iterator.hasNext();) {
                PicklistItem item = iterator.next();
                fieldVO.addCode(item.getItemName());
                String displayValue = messageService.lookupMessage(MessageResourceType.PICKLIST_VALUE, item.getItemName(), locale);
                if (GenericValidator.isBlankOrNull(displayValue)) {
                    displayValue = item.getDefaultDisplayValue();
                }
                fieldVO.addDisplayValue(displayValue);
                fieldVO.addReferenceValue(item.getReferenceValue());
            }
            if (fieldVO.getReferenceValues() != null) for (Iterator<String> iterator = fieldVO.getReferenceValues().iterator(); iterator.hasNext();) {
                String refVal = iterator.next();
                if (!GenericValidator.isBlankOrNull(refVal)) {
                    fieldVO.setCascading(true);
                }
            }
        }
        return fieldVO;
    }
}
