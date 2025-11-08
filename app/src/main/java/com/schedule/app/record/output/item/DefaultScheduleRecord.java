package com.schedule.app.record.output.item;

import java.time.LocalDate;
import java.time.LocalTime;

public record DefaultScheduleRecord(
        int scheduleId,
        LocalTime startTime,
        LocalTime endTime,
        LocalDate startDate,
        LocalDate endDate,
        String worktypeName,
        String worktypeColor) {
}
