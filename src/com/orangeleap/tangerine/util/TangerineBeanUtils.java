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
package com.orangeleap.tangerine.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

public class TangerineBeanUtils {

    /**
     * If a propertyName refers to an inner bean that is not instantiated yet, instantiate it.
     * For example, if you want to bind 'constituent.firstName' to a 'Gift' object that doesn't have an instantiated
     * Constituent object, this method will create the constituent object first.
     *
     * Allows for infinite nested levels.
     * @param bw
     * @param propertyName
     */
    public static void checkInnerBeanCreated(BeanWrapper bw, String propertyName) {
        if (propertyName != null && propertyName.indexOf('.') > 0) {
            int dotIndex = propertyName.indexOf('.');
            String innerBeanName = propertyName.substring(0, dotIndex);
            if (bw.isReadableProperty(innerBeanName) && bw.getPropertyValue(innerBeanName) == null) {
                Class innerClazz = bw.getPropertyType(innerBeanName);
                try {
                    bw.setPropertyValue(innerBeanName, innerClazz.newInstance());
                }
                catch (Exception e) {
                }
                String nextPropertyName = propertyName.substring(dotIndex + 1);
                BeanWrapper innerBw = PropertyAccessorFactory.forBeanPropertyAccess(bw.getPropertyValue(innerBeanName));
                if (nextPropertyName.indexOf('.') > 0) {
                    checkInnerBeanCreated(innerBw, nextPropertyName);
                }
            }
        }
    }

}
