package com.schedule.app.record.input;

import lombok.Builder;

@Builder
public record RegularScheduleInputRecord(
    Integer id,
    String userId,
    String startTime,
    String endTime,
    String startDate,
    String endDate,
    String dayOfWeek,
    Integer intervalWeeks,
    String workTypeId
) {
}
