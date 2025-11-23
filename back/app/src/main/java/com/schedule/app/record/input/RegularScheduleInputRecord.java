package com.schedule.app.record.input;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;

@Builder
public record RegularScheduleInputRecord(
    Integer id,
    String userId,
    LocalTime startTime,
    LocalTime endTime,
    LocalDate startDate,
    LocalDate endDate,
    String dayOfWeek,
    String workTypeId) {}
