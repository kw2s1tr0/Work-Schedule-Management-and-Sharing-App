package com.schedule.app.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;

@Builder
public record RegularScheduleDTO(
    int scheduleId,
    LocalTime startTime,
    LocalTime endTime,
    LocalDate startDate,
    LocalDate endDate,
    DayOfWeek daysOfWeek,
    String worktypeName,
    String worktypeColor) {}
