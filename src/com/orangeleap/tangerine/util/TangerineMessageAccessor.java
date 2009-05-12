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

    public static String getMessage(String msg, String[] args) {
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
