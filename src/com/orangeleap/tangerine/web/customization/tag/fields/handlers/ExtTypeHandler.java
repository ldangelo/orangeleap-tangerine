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

import com.orangeleap.tangerine.type.FieldType;
import java.math.BigDecimal;
import java.util.Date;

public final class ExtTypeHandler {
    public static final String EXT_DATE = "date";
    public static final String EXT_BOOLEAN = "boolean";
    public static final String EXT_INT = "int";
    public static final String EXT_FLOAT = "float";
    public static final String EXT_STRING = "string";
    public static final String EXT_AUTO = "auto";

    public static String findExtDataType(Class clazz) {
        String extType;
        if (Date.class.equals(clazz)) {
            extType = EXT_DATE;
        }
        else if (Boolean.class.equals(clazz) || Boolean.TYPE.equals(clazz)) {
            extType = EXT_BOOLEAN;
        }
        else if (Byte.class.equals(clazz) || Integer.class.equals(clazz) || Short.class.equals(clazz) || Long.class.equals(clazz) ||
                Byte.TYPE.equals(clazz) || Integer.TYPE.equals(clazz) || Short.TYPE.equals(clazz) || Long.TYPE.equals(clazz)) {
            extType = EXT_INT;
        }
        else if (Double.class.equals(clazz) || Float.class.equals(clazz) || BigDecimal.class.equals(clazz) ||
                Double.TYPE.equals(clazz) || Float.TYPE.equals(clazz)) {
            extType = EXT_FLOAT;
        }
        else if (Character.class.equals(clazz) || String.class.equals(clazz) || Character.TYPE.equals(clazz)) {
            extType = EXT_STRING;
        }
        else {
            extType = EXT_AUTO;
        }
        return extType;
    }

	public static final String EXT_CHECKBOX = "checkbox";
	public static final String EXT_COMBO = "combo";
	public static final String EXT_DATE_FIELD = "datefield";
	public static final String EXT_DISPLAY_FIELD = "displayfield";
	public static final String EXT_FIELD = "field";
	public static final String EXT_HIDDEN = "hidden";
	public static final String EXT_NUMBER_FIELD = "numberfield";
	public static final String EXT_RADIO = "radio";
	public static final String EXT_TEXT_AREA = "textarea";
	public static final String EXT_TEXT_FIELD = "textfield";
	public static final String EXT_TIME_FIELD = "timefield";

	public static String findExtFieldType(FieldType fieldType) {
		String extType;
		if (FieldType.CHECKBOX.equals(fieldType)) {
			extType = EXT_CHECKBOX;
		}
		else if (FieldType.DATE.equals(fieldType)) {
			extType = EXT_DATE_FIELD;
		}
		else if (FieldType.LONG_TEXT.equals(fieldType)) {
			extType = EXT_TEXT_AREA;
		}
		else if (FieldType.TEXT.equals(fieldType)) {
			extType = EXT_TEXT_FIELD;
		}
		else if (FieldType.READ_ONLY_TEXT.equals(fieldType)) {
			extType = EXT_DISPLAY_FIELD;
		}
		else if (FieldType.HIDDEN.equals(fieldType)) {
			extType = EXT_HIDDEN;
		}
		else if (FieldType.NUMBER.equals(fieldType)) {
			extType = EXT_NUMBER_FIELD;
		}
		else if (FieldType.PICKLIST.equals(fieldType)) {
			extType = EXT_COMBO;
		}
		else {
			extType = EXT_FIELD;
		}
		return extType;
	}
}
