package com.schedule.app.record.input;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;

@Builder
public record DefaultScheduleRecord(
    Integer id,
    String userId,
    LocalTime starTime,
    LocalTime endTime,
    LocalDate starDate,
    LocalDate endDate,
    String workTypeId
) {
}