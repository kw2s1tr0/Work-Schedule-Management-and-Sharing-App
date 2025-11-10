package com.schedule.app.dto;

import java.time.LocalTime;

import lombok.Builder;

@Builder
public record IrregularScheduleDTO(
        int scheduleId,
        String date,
        LocalTime startTime,
        LocalTime endTime,
        String worktypeName,
        String worktypeColor
) {}
