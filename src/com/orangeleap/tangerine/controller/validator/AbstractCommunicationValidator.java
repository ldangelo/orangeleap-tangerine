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

import java.util.Date;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.orangeleap.tangerine.domain.communication.AbstractCommunicationEntity;
import com.orangeleap.tangerine.type.ActivationType;

public abstract class AbstractCommunicationValidator<T extends AbstractCommunicationEntity> implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    protected void validateDates(T entity, Errors errors) {
        if (ActivationType.temporary.equals(entity.getActivationStatus())) {
            checkDateRange(entity.getTemporaryStartDate(), entity.getTemporaryEndDate(), "temporaryEndDate", "invalidTemporaryEndDateBeforeStartDate", "The Temporary End Date must be after the Temporary Start Date", errors);
        }
    }
    
    private void checkNullDate(Date date, String field, String msgKey, String msgDefault, Errors errors) {
        if (date == null) {
            errors.rejectValue(field, msgKey, msgDefault);
        }
    }
    
    private void checkDateRange(Date startDate, Date endDate, String field, String msgKey, String msgDefault, Errors errors) {
        if (startDate != null && endDate != null) {
            if (!endDate.after(startDate)) {
                errors.rejectValue(field, msgKey, msgDefault);
            }
        }
    }
}
