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

package com.orangeleap.tangerine.type;

import javax.xml.bind.annotation.XmlType;

@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public enum FieldType {
    HIDDEN,
    DATE,
    DATE_DISPLAY,
    TEXT,
    PERCENTAGE,
    READ_ONLY_TEXT,
    PAYMENT_TYPE_READ_ONLY_TEXT,
    ASSOCIATION,
    ASSOCIATION_DISPLAY,
    LOOKUP,
    CODE,
    CODE_OTHER,
    MULTI_CODE_ADDITIONAL,
    CODE_OTHER_DISPLAY,
    PICKLIST,
    PICKLIST_DISPLAY,
    MULTI_PICKLIST,
    MULTI_PICKLIST_ADDITIONAL,
    MULTI_PICKLIST_DISPLAY,
    MULTI_PICKLIST_ADDITIONAL_DISPLAY,
    PREFERRED_PHONE_TYPES,
    DATE_TIME,
    ADDRESS,
    PHONE,
    LONG_TEXT,
    NUMBER,
    SPACER,
    CC_EXPIRATION,
    CC_EXPIRATION_DISPLAY,
    CHECKBOX,
    PAYMENT_SOURCE_PICKLIST,
    ADJUSTED_GIFT_PAYMENT_TYPE_PICKLIST,
    ADJUSTED_GIFT_PAYMENT_SOURCE_PICKLIST, 
    ADDRESS_PICKLIST,
    PHONE_PICKLIST,
    EMAIL_PICKLIST,
    EXISTING_ADDRESS_PICKLIST,
    EXISTING_PHONE_PICKLIST,
    EXISTING_EMAIL_PICKLIST,
    QUERY_LOOKUP,
    MULTI_QUERY_LOOKUP,
    QUERY_LOOKUP_OTHER,
    QUERY_LOOKUP_DISPLAY,
    SELECTION,
    SELECTION_DISPLAY
}