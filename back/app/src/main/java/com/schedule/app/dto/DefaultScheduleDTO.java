package com.schedule.app.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;

@Builder
public record DefaultScheduleDTO(
        Integer scheduleId,
        LocalTime startTime,
        LocalTime endTime,
        LocalDate startDate,
        LocalDate endDate,
        String worktypeName,
        String worktypeColor
) {}
