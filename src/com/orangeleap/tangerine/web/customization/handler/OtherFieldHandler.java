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

import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.FieldVO;

/**
 * Used only for single-valued fields (picklist, querylist, etc) where the user can type in and use a free-form value instead of an existing pre-defined one
 */
public class OtherFieldHandler {

    public static void handleOtherField(FieldVO fieldVO, Object model) {
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(model);
        
        String fieldName = fieldVO.getFieldName();
        if (bw.isReadableProperty(fieldName)) {
            Object propertyValue = bw.getPropertyValue(fieldName);
            
            /* If the property value and ID are not defined, see if the 'other' field is populated and use that value instead */
            if (fieldVO.getId() == null && (propertyValue == null || StringConstants.EMPTY.equals(propertyValue))) {
                if (bw.isReadableProperty(fieldVO.getOtherFieldName())) {
                    Object otherFieldValue = bw.getPropertyValue(fieldVO.getOtherFieldName());
                    if (otherFieldValue != null) {
                        fieldVO.setDisplayValue(otherFieldValue);
                    }
                }
            }
        }
    }
}
