package com.mpower.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarUtils {

    public static Calendar getToday(boolean includeTime) {
        Calendar now = Calendar.getInstance();
        if (!includeTime) {
            Calendar today = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            System.out.println("getToday() = " + today.getTime() + " millis=" + today.getTimeInMillis());
            return today;
        }
        System.out.println("getToday() = " + now.getTime() + " millis=" + now.getTimeInMillis());
        return now;
    }
}
