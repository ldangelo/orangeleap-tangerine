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

import java.util.Locale;

import org.springframework.context.MessageSource;

public class TangerineMessageAccessor {
    
    private static MessageSource messageSource;

    public static String getMessage(String msg) {
        return messageSource.getMessage(msg, null, msg, Locale.getDefault());
    }

    public static String getMessage(String msg, String arg) {
        return getMessage(msg, new String[] { arg });
    }

    public static String getMessage(String msg, String... args) {
        return messageSource.getMessage(msg, args, msg, Locale.getDefault());
    }

    /**
     * This is the 'static factory method' called by Spring to 'create' this bean. null can not be returned (error occurs) 
     * so since this is a static library, returning the class object. No one will access this as a bean, so it's ok.
     * @param messageSource
     * @return TangerineMessageAccessor.class
     */
    @SuppressWarnings("unchecked")
    public static Class setMessageSource(MessageSource messageSource) {
        TangerineMessageAccessor.messageSource = messageSource;
        return TangerineMessageAccessor.class;
    }
}
