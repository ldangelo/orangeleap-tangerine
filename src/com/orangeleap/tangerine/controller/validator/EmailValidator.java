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

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.orangeleap.tangerine.domain.NewEmailAware;
import com.orangeleap.tangerine.domain.communication.AbstractCommunicatorEntity;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.util.StringConstants;

public class EmailValidator extends AbstractCommunicationValidator<Email> {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return Email.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.trace("in EmailValidator");
        validateEmail(target, errors);
    }
    
    public void validateEmail(Object target, Errors errors) {
    	Email email = null;
    	String inPath = errors.getNestedPath();
    	if (target instanceof Email) {
    		email = (Email) target;
    	} 
    	else if (target instanceof NewEmailAware) {
    		email = ((NewEmailAware) target).getEmail();
    		errors.setNestedPath("email");
    	}
        else if (target instanceof AbstractCommunicatorEntity) {
            email = ((AbstractCommunicatorEntity) target).getPrimaryEmail();
            errors.setNestedPath("primaryEmail");
        }

        if (StringUtils.hasText(email.getCustomFieldValue(StringConstants.EMAIL_TYPE)) == false) {
            errors.rejectValue(StringConstants.CUSTOM_FIELD_MAP_START + StringConstants.EMAIL_TYPE + StringConstants.CUSTOM_FIELD_MAP_END, "errorEmailTypeRequired");
        }
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress", "invalidEmailAddress", "Email Address is required");
    	validateDates(email, errors);
    	errors.setNestedPath(inPath);
    }
}
