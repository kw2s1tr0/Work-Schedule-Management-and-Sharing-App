package com.schedule.app.form;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record IrregularScheduleForm(
        Integer id,
        @NotNull LocalTime startTime,
        @NotNull LocalTime endTime,
        @NotNull LocalDate date,
        @Pattern(regexp = "([1-9]|10|11)") @NotNull String workTypeId) {
    public IrregularScheduleForm {
        if (startTime.isAfter(endTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The 'start' time must be before or equal to the 'end' time.");
        }
    }
}