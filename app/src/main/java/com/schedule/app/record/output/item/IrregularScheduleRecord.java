package com.schedule.app.record.output.item;

import java.time.LocalDate;
import java.time.LocalTime;

public record IrregularScheduleRecord(
        int scheduleId,
        LocalTime startTime,
        LocalTime endTime,
        LocalDate date,
        String worktypeName,
        String worktypeColor) {
}
