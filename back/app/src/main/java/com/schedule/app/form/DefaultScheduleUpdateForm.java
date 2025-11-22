package com.schedule.app.form;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record DefaultScheduleUpdateForm(
        @NotNull Integer id,
        @NotNull LocalTime startTime,
        @NotNull LocalTime endTime,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate,
        @Pattern(regexp = "(0[1-9]|10|11)") @NotNull String workTypeId) {
    public DefaultScheduleUpdateForm {
        if (startDate == null || endDate == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Both 'from' and 'to' dates must be provided.");
        }

        if (startTime == null || endTime == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Both 'start' and 'end' times must be provided.");
        }

        if (startDate.isAfter(endDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The 'from' date must be before or equal to the 'to' date.");
        }

        if (startTime.isAfter(endTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The 'start' time must be before or equal to the 'end' time.");
        }
    }
}
