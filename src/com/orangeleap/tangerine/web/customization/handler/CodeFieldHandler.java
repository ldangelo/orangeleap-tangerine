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

import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.web.customization.FieldVO;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Locale;

public class CodeFieldHandler extends GenericFieldHandler {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());
    private final PicklistItemService picklistItemService;

    public CodeFieldHandler(ApplicationContext appContext) {
        super(appContext);
        picklistItemService = (PicklistItemService) appContext.getBean("picklistItemService");
    }

    @Override
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, Object model) {
        FieldVO fieldVO = super.handleField(sectionFields, currentField, locale, model);
        Object propertyValue = super.getPropertyValue(model, fieldVO);
        fieldVO.setDisplayValue(resolve(fieldVO.getFieldName(), (String) propertyValue));
        return fieldVO;
    }

    private String resolve(String picklistNameId, String defaultDisplayValue) {
        if (logger.isTraceEnabled()) {
            logger.trace("resolve: picklistNameId = " + picklistNameId + " defaultDisplayValue = " + defaultDisplayValue);
        }
        String val = defaultDisplayValue;
        PicklistItem code = picklistItemService.getPicklistItemByDefaultDisplayValue(picklistNameId, defaultDisplayValue);
        if (code != null) {
            val = code.getValueDescription();
        }
        return val;
    }
}
