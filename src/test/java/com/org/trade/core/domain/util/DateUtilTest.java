package com.org.trade.core.domain.util;

import com.org.trade.core.domain.Weekend;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DateUtilTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void expect_exception_when_input_date_is_null() {
        expectedEx.expect(NullPointerException.class);
        expectedEx.expectMessage("Input date should not be null");
        DateUtil.determineNextAllowableDate(null, Weekend.SAT_SUN);
    }

    @Test
    public void expect_exception_when_weekend_is_null() {
        expectedEx.expect(NullPointerException.class);
        expectedEx.expectMessage("Weekend should not be null");
        DateUtil.determineNextAllowableDate(LocalDate.now(), null);
    }

    @Test
    public void expect_same_date_when_date_is_not_on_a_weekend() {
        LocalDate date = LocalDate.of(2017, 8, 15);
        assertThat(date, is(DateUtil.determineNextAllowableDate(date, Weekend.SAT_SUN)));
    }

    @Test
    public void expect_monday_when_weekend_is_Saturday_and_sunday() {
        LocalDate actualDateOnSunday = LocalDate.of(2017, 8, 13);
        LocalDate calculatedDateOnMonday = LocalDate.of(2017, 8, 14);
        assertThat(calculatedDateOnMonday, is(DateUtil.determineNextAllowableDate(actualDateOnSunday, Weekend.SAT_SUN)));

        LocalDate actualDateOnSaturday = LocalDate.of(2017, 8, 12);
        assertThat(calculatedDateOnMonday, is(DateUtil.determineNextAllowableDate(actualDateOnSaturday, Weekend.SAT_SUN)));

    }

    @Test
    public void expect_sunday_when_weekend_is_friday_and_saturday() {
        LocalDate actualDateOnFriday = LocalDate.of(2017, 8, 11);
        LocalDate calculatedDateOnSunday = LocalDate.of(2017, 8, 13);
        assertThat(calculatedDateOnSunday, is(DateUtil.determineNextAllowableDate(actualDateOnFriday, Weekend.FRI_SAT)));

        LocalDate actualDateOnSaturday = LocalDate.of(2017, 8, 12);
        assertThat(calculatedDateOnSunday, is(DateUtil.determineNextAllowableDate(actualDateOnSaturday, Weekend.FRI_SAT)));

    }
}
