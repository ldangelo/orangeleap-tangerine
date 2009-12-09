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
        if (Date.class.equals(clazz)) {
            extType = "date";
        }
        else if (Boolean.class.equals(clazz) || Boolean.TYPE.equals(clazz)) {
            extType = "boolean";
        }
        else if (Byte.class.equals(clazz) || Integer.class.equals(clazz) || Short.class.equals(clazz) || Long.class.equals(clazz) ||
                Byte.TYPE.equals(clazz) || Integer.TYPE.equals(clazz) || Short.TYPE.equals(clazz) || Long.TYPE.equals(clazz)) {
            extType = "int";
        }
        else if (Double.class.equals(clazz) || Float.class.equals(clazz) || BigDecimal.class.equals(clazz) ||
                Double.TYPE.equals(clazz) || Float.TYPE.equals(clazz)) {
            extType = "float";
        }
        else if (Character.class.equals(clazz) || String.class.equals(clazz) || Character.TYPE.equals(clazz)) {
            extType = "string";
        }
        else {
            extType = "auto";
        }
        return extType;
    }
}
