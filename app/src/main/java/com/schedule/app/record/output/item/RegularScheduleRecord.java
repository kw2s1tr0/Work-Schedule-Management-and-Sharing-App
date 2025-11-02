package com.schedule.app.record.output.item;

import java.time.LocalDate;
import java.time.LocalTime;

import com.schedule.app.enums.DaysOfWeek;

public record RegularScheduleRecord (
    int scheduleId,
    LocalTime startTime,
    LocalTime endTime,
    LocalDate startDate,
    LocalDate endDate,
    DaysOfWeek daysOfWeek,
    int intervalWeeks,
    String worktypeName,
    String worktypeColor
){}
