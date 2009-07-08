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

package com.orangeleap.tangerine.controller.validator;

import java.text.MessageFormat;
import java.util.Locale;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import com.orangeleap.tangerine.service.customization.MessageService;
import com.orangeleap.tangerine.type.MessageResourceType;

/**
 * Messages can be obtained either from the database or from a message resource bundle.
 * Checks the message bundle first before looking at the DB for messages.
 */
public class TangerineMessageSource extends AbstractMessageSource implements ResourceLoaderAware {

    private MessageService messageService;
    private AbstractMessageSource bundleMessageSource;

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void setBundleMessageSource(AbstractMessageSource bundleMessageSource) {
        this.bundleMessageSource = bundleMessageSource;
    }
    
    private static Locale checkLocale(Locale locale) {
        if (locale != null && Locale.ENGLISH.getLanguage().equals(locale.getLanguage()) && "".equals(locale.getCountry())) {
            return Locale.US;  // assume US if no country is specified and 'en' is used 
        }
        return locale;
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String message = null;
        
        locale = checkLocale(locale);
        try {
            message = bundleMessageSource.getMessage(code, null, locale); // check the message bundle first for the key
        }
        catch (NoSuchMessageException ne) { }

        if (!StringUtils.hasText(message)) {
            message = messageService.lookupMessage(MessageResourceType.FIELD_VALIDATION, code, locale);
            if (message == null && (code.startsWith("fieldRequiredFailure.") || code.startsWith("fieldValidationFailure."))) {
                logger.info("message code, " + code + ", not found - use out-of-the-box error message");
                message = messageService.lookupMessage(MessageResourceType.FIELD_VALIDATION, code.substring(0, code.indexOf('.')), locale);
            }
        }
        if (message != null) {
            return createMessageFormat(message, locale);
        }
        return null;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
    }
}
