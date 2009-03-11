package com.orangeleap.tangerine.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarUtils {

    public static Calendar getToday(boolean includeTime) {
        Calendar now = Calendar.getInstance();
        if (!includeTime) {
            Calendar today = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            return today;
        }
        return now;
    }
}
