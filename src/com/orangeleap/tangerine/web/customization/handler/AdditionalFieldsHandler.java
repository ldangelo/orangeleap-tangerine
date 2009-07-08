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

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import com.orangeleap.tangerine.web.customization.FieldVO;

/**
 * Used only for multi-valued fields (multi-picklist, multi-querylist, etc) where the user can type in values in additional to the existing pre-defined ones
 */
public class AdditionalFieldsHandler {

    public static void handleAdditionalFields(FieldVO fieldVO, Object model) {
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(model);
        String additionalFieldName = fieldVO.getAdditionalFieldName();
        
        if (bw.isReadableProperty(additionalFieldName)) {
            Object propertyValue = bw.getPropertyValue(additionalFieldName);
            
            if (propertyValue != null) {
                fieldVO.setAdditionalDisplayValues(propertyValue.toString());
            }
        }
    }
}
