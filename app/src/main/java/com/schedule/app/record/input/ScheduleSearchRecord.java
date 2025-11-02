package com.schedule.app.record.input;

import java.time.LocalDate;

public record ScheduleSearchRecord(
    Integer userId,
    LocalDate from,
    LocalDate to
) {}