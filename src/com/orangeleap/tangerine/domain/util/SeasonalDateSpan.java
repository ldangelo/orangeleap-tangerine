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

package com.orangeleap.tangerine.domain.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Provides the ability to test if a date falls
 * between a start and end date, but ignoring the
 * year component.
 *
 * @version 1.0
 */
public class SeasonalDateSpan {

    private final Calendar startDate = Calendar.getInstance();
    private final Calendar endDate = Calendar.getInstance();

    /**
     * Construct a new SeasonalDateSpan. Comparisons are made
     * based on the year of the Date passed in the contains() method
     *
     * @param startDate the seasonal start date
     * @param endDate   the seasonal end date
     */
    public SeasonalDateSpan(Date startDate, Date endDate) {

        if (startDate == null) {
            throw new IllegalArgumentException("startDate cannot be null");
        } else if (endDate == null) {
            throw new IllegalArgumentException("endDate cannot be null");
        }

        this.startDate.setTime(startDate);
        this.endDate.setTime(endDate);
    }

    /**
     * Returns true if the given date falls between the start and end
     * date (inclusive) of this span. They year component is ignored.
     * If date is null, this method returns false
     *
     * @param date the date to check within the span
     * @return true if the date falls within the span
     */
    public boolean contains(Date date) {

        if (date == null) {
            return false;
        }

        Calendar activeDate = Calendar.getInstance();
        activeDate.setTime(date);

        int baseYear = activeDate.get(Calendar.YEAR);

        // calibrate the start and end dates to the base year
        startDate.set(Calendar.YEAR, baseYear);
        endDate.set(Calendar.YEAR, baseYear);

        if (startDate.compareTo(endDate) == 0) {
            // all the same date
            return (startDate.compareTo(activeDate) == 0);
        } else if (startDate.compareTo(endDate) < 0) {
            // start date is before end date
            return (startDate.compareTo(activeDate) <= 0 && endDate.compareTo(activeDate) >= 0);
        } else {
            // start date is after end date
            return (startDate.compareTo(activeDate) <= 0 || endDate.compareTo(activeDate) >= 0);
        }
    }
}
