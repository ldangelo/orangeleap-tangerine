package com.mpower.domain.util;

import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Provides the ability to test if a date falls
 * between a start and end date, but ignoring the
 * year component.
 * @version 1.0
 */
public class SeasonalDateSpan {

    private final Calendar startDate = Calendar.getInstance();
    private final Calendar endDate = Calendar.getInstance();

    /**
     * Construct a new SeasonalDateSpan. The startDate must fall before
     * the endDate chronologically, even though comparisons are made
     * ignoring the year
     * @param startDate the seasonal start date
     * @param endDate the seasonal end date
     */
    public SeasonalDateSpan(Date startDate, Date endDate) {

        if(startDate == null) {
            throw new IllegalArgumentException("startDate cannot be null");
        } else if(endDate == null) {
            throw new IllegalArgumentException("endDate cannot be null");
        }

        this.startDate.setTime(startDate);
        this.endDate.setTime(endDate);

        if(!endDate.after(startDate)) {
            DateFormat df = new SimpleDateFormat("MM/dd/yy");
            throw new IllegalArgumentException(
                    String.format("startDate [%s] must come before endDate [%s]",
                            df.format(startDate), df.format(endDate)) );
        }
    }

    /**
     * Returns true if the given date falls between the start and end
     * date (inclusive) of this span. They year component is ignored.
     * If date is null, this method returns false
     * @param date the date to check within the span
     * @return true if the date falls within the span
     */
    public boolean contains(Date date) {

        if(date == null) {
            return false;
        }

        int startDay = startDate.get(Calendar.DAY_OF_YEAR);
        int startYear = startDate.get(Calendar.YEAR);
        int endDay = endDate.get(Calendar.DAY_OF_YEAR);
        int endYear = endDate.get(Calendar.YEAR);

        Calendar compareDate = Calendar.getInstance();
        compareDate.setTime(date);
        int compareDay = compareDate.get(Calendar.DAY_OF_YEAR);

        // end date rolls over to another year (i.e. span = Nov through Feb)
        if(startYear < endYear) {

            if( (compareDay >= startDay && compareDay <= (endDay+365)) ||
                ( (compareDay+365) >= startDay && (compareDay+365) <= (endDay+365))) {
                return true;
            }
        } else {

            if(compareDay >= startDay && compareDay <= endDay) {
                return true;
            }
        }

        return false;
    }
}
