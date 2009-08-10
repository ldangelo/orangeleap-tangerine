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

package com.orangeleap.tangerine.web.customization.tag.fields.handlers;

import java.util.Date;
import java.math.BigDecimal;

public final class ExtTypeHandler {
    public static final String EXT_DATE = "date";
    public static final String EXT_BOOLEAN = "boolean";
    public static final String EXT_INT = "int";
    public static final String EXT_FLOAT = "float";
    public static final String EXT_STRING = "string";
    public static final String EXT_AUTO = "auto";

    public static String findExtType(Class clazz) {
        String extType;
        if (clazz.equals(Date.class)) {
            extType = "date";
        }
        else if (clazz.equals(Boolean.class) || clazz.equals(Boolean.TYPE)) {
            extType = "boolean";
        }
        else if (clazz.equals(Byte.class) || clazz.equals(Integer.class) || clazz.equals(Short.class) || clazz.equals(Long.class) ||
                clazz.equals(Byte.TYPE) || clazz.equals(Integer.TYPE) || clazz.equals(Short.TYPE) || clazz.equals(Long.TYPE)) {
            extType = "int";
        }
        else if (clazz.equals(Double.class) || clazz.equals(Float.class) || clazz.equals(BigDecimal.class) ||
                clazz.equals(Double.TYPE) || clazz.equals(Float.TYPE)) {
            extType = "float";
        }
        else if (clazz.equals(Character.class) || clazz.equals(String.class) || clazz.equals(Character.TYPE)) {
            extType = "string";
        }
        else {
            extType = "auto";
        }
        return extType;
    }
}
