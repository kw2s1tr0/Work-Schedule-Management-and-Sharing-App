package com.schedule.app.record.input;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;

@Builder
public record IrregularScheduleRecord(
    Integer id,
    String userId,
    LocalTime startTime,
    LocalTime endTime,
    LocalDate date,
    String workTypeId
) {
}