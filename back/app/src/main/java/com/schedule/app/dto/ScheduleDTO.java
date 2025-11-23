package com.schedule.app.dto;

import com.schedule.app.enums.ScheduleType;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;

@Builder
public record ScheduleDTO(
    int scheduleId,
    LocalDate date,
    LocalTime startTime,
    LocalTime endTime,
    String worktypeName,
    String worktypeColor,
    ScheduleType scheduleType) {}
