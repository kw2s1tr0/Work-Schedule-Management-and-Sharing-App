package com.schedule.app.record.input;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;

@Builder
public record ScheduleSearchRecord(
    String userId,
    LocalDate from,
    LocalDate to,
    List<String> names,
    String organizationCode
) {}