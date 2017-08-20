package com.org.trade.core.domain.util;

import com.org.trade.core.domain.Weekend;

import java.time.LocalDate;

import static com.google.common.base.Preconditions.checkNotNull;

/*
 *  Utility for date operations.
 */
public class DateUtil {

    /*
    *  The method accepts a date and week end. If date falls on weekend then the date is incremented until
    *  weekday is found.
    */
    public static LocalDate determineNextAllowableDate(final LocalDate localDate,final Weekend weekend) {
        checkNotNull(localDate, "Input date should not be null");
        checkNotNull(weekend, "Weekend should not be null");
        LocalDate tempLocalDate = localDate;
        while (isDayFallingOnWeekEnd(weekend, tempLocalDate)) {
            tempLocalDate = tempLocalDate.plusDays(1);
        }
        return tempLocalDate;
    }

    private static boolean isDayFallingOnWeekEnd(Weekend weekend, LocalDate tempLocalDate) {
        return weekend.getWeekendDays().contains(tempLocalDate.getDayOfWeek());
    }

}
