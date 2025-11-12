package com.schedule.app.form;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RegularScheduleForm(
        Integer id,
        @NotNull LocalTime startTime,
        @NotNull LocalTime endTime,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate,
        @NotNull DayOfWeek dayOfWeek,
        @Min(1) @Max(2) Integer intervalWeeks,
        @NotNull String workTypeId) {
    public RegularScheduleForm {
        if (startDate.isAfter(endDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The 'from' date must be before or equal to the 'to' date.");
        }

        if (startTime.isAfter(endTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The 'start' time must be before or equal to the 'end' time.");
        }
    }
}