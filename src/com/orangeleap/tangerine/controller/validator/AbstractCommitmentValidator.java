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

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.orangeleap.tangerine.domain.paymentInfo.Commitment;
import com.orangeleap.tangerine.util.StringConstants;

public abstract class AbstractCommitmentValidator<T extends Commitment> implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    public void validateStartEndDate(T entity, Errors errors) {
        if (entity.getEndDate() != null) {
            if (entity.getEndDate().before(entity.getStartDate())) {
                errors.rejectValue("endDate", "invalidEndDate", "Start date must be before end date");
            }
        }
    }
    
    public void validateReminders(T entity, Errors errors) {
        String initialReminder = entity.getCustomFieldValue(StringConstants.INITIAL_REMINDER);
        String maxReminders = entity.getCustomFieldValue(StringConstants.MAXIMUM_REMINDERS);
        String reminderInterval = entity.getCustomFieldValue(StringConstants.REMINDER_INTERVAL);
        if (NumberUtils.isNumber(initialReminder) && Integer.parseInt(initialReminder) < 0) {
            errors.rejectValue("customFieldMap[initialReminder].value", "errorNumber", new String[] { "Initial Reminder" }, "The Initial Reminder must be 0 or greater");
        }
        if (NumberUtils.isNumber(maxReminders) && Integer.parseInt(maxReminders) < 0) {
            if (NumberUtils.isNumber(initialReminder) && Integer.parseInt(initialReminder) > 0) {
                errors.rejectValue("customFieldMap[maximumReminders].value", "errorMaxReminders", "The number of maximum reminders must be 1 or greater");
            }
            else {
                errors.rejectValue("customFieldMap[maximumReminders].value", "errorNumber", new String[] { "Maximum Reminders" }, "Maximum Reminders must be 0 or greater");
            }
        }
        if (NumberUtils.isNumber(reminderInterval) && Integer.parseInt(reminderInterval) < 0) {
            errors.rejectValue("customFieldMap[reminderInterval].value", "errorNumber", new String[] { "Reminder Interval" }, "The Reminder Interval must be 0 or greater");
        }
        if (NumberUtils.isNumber(initialReminder) && NumberUtils.isNumber(maxReminders)) {
            if (Integer.parseInt(initialReminder) > 0 && Integer.parseInt(maxReminders) == 0) {
                errors.rejectValue("customFieldMap[maximumReminders].value", "errorMaxReminders", "The number of maximum reminders must be 1 or greater");
            }
        }
    }
}
