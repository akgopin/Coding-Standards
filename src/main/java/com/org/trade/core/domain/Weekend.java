package com.org.trade.core.domain;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import  java.time.DayOfWeek;

import java.util.Arrays;
import java.util.HashSet;

public enum Weekend {

    FRI_SAT(new HashSet<DayOfWeek>(Arrays.asList(FRIDAY,SATURDAY))),
    SAT_SUN(new HashSet<DayOfWeek>(Arrays.asList(SATURDAY,SUNDAY)));

    private final HashSet<DayOfWeek> weekendDays;

    Weekend(HashSet<DayOfWeek> weekendDays){
        this.weekendDays = weekendDays;
    }

    public HashSet<DayOfWeek> getWeekendDays() {
        return weekendDays;
    }
}
