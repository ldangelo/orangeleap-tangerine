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

package com.orangeleap.tangerine.service.validator;

import com.orangeleap.tangerine.domain.PhoneAware;
import com.orangeleap.tangerine.domain.communication.AbstractCommunicatorEntity;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.validation.Errors;

public class PhoneValidator extends AbstractCommunicationValidator<Phone> {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return Phone.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.trace("in PhoneValidator");
        validatePhone(target, errors);
    }

    public void validatePhone(Object target, Errors errors) {
        Phone phone = null;
        String inPath = errors.getNestedPath();
        if (target instanceof Phone) {
            phone = (Phone) target;
        }
        else if (target instanceof PhoneAware) {
            phone = ((PhoneAware) target).getPhone();
            errors.setNestedPath("phone");
        }
        else if (target instanceof AbstractCommunicatorEntity) {
            phone = ((AbstractCommunicatorEntity) target).getPrimaryPhone();
            errors.setNestedPath("primaryPhone");
        }

        validateDates(phone, errors);
        errors.setNestedPath(inPath);
    }
}
