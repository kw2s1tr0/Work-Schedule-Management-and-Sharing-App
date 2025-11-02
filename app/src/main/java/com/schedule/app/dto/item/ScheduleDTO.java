package com.schedule.app.dto.item;

import java.time.LocalTime;

import lombok.Builder;

@Builder
public record ScheduleDTO (
    int scheduleId,
    LocalTime startTime,
    LocalTime endTime,
    String worktypeName,
    String worktypeColor
){}
