package com.org.trade.core.domain;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum Weekend {
    FRI_SAT(new HashSet<DayOfWeek>(Arrays.asList(DayOfWeek.FRIDAY,DayOfWeek.SATURDAY)),
    SAT_SUN(new HashSet<DayOfWeek>(Arrays.asList(DayOfWeek.SATURDAY,DayOfWeek.SUNDAY));

    private final HashSet<DayOfWeek> weekendDays;

    Weekend(Set<DayOfWeek >)
}
