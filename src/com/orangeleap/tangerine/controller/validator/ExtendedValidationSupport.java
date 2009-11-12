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

import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.apache.commons.validator.GenericValidator;

import java.lang.reflect.Method;

public class ExtendedValidationSupport {
	
    protected final Log logger = OLLogger.getLog(getClass());

	protected final static GenericValidator validator = new GenericValidator();

	/* 
	 * Add ability to use for example in place of regex:  'extensions:isEmail'
	 * INSERT INTO FIELD_VALIDATION (SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, VALIDATION_REGEX) VALUES ('company1', 'constituent.contactInfo', 'constituent.emailMap[home]', 'email.emailAddress', 'extensions:isEmail');
	 * 
	 * @param value - string to validate
	 * @param expression - isCreditCard/isEmail/isUrl
	 */
	public boolean validate(String value, String expression) {
        boolean isValid = false;
        if ("isDate".equals(expression)) {
            isValid = GenericValidator.isDate(value, "EEE MMM dd HH:mm:ss zzz yyyy", true); // Thu Nov 06 00:00:00 CST 2009
        }
        else {
            Method[] methods = validator.getClass().getDeclaredMethods();
            for (Method method: methods) {
                if (method.getName().equals(expression)) {
                    try {
                       isValid = (Boolean)method.invoke(validator, value);
                    }
                    catch (Exception e) {
                       logger.error(e.getCause());
                       isValid = false;
                    }
                }
            }
        }
		return isValid;
	}
	
}
