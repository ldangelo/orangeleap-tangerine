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

import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.validator.GenericValidator;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.MessageResourceType;
import com.orangeleap.tangerine.web.customization.FieldVO;

public class PreferredPhoneFieldHandler extends GenericFieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());


    public PreferredPhoneFieldHandler(ApplicationContext appContext) {
        super(appContext);
    }

    /**
     * Reads each field on the page to get a list of phones types that are used to build the drop-down.
     */
    @Override
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, Object model) {
        FieldVO fieldVO = super.handleField(sectionFields, currentField, locale, model);
        for (SectionField currentSectionField : sectionFields) {
            if (FieldType.PHONE == currentSectionField.getFieldDefinition().getFieldType()) {
                FieldDefinition currentFieldDefinition = currentSectionField.getFieldDefinition();
                String displayValue = messageService.lookupMessage(MessageResourceType.FIELD_LABEL, currentSectionField.getFieldLabelName(), locale);
                if (GenericValidator.isBlankOrNull(displayValue)) {
                    displayValue = currentSectionField.getFieldDefinition().getDefaultLabel();
                }
                fieldVO.addDisplayValue(displayValue);

                fieldVO.addCode(currentFieldDefinition.getFieldName());
            }
        }
        return fieldVO;
    }
}
