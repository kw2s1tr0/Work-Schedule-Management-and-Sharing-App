package com.schedule.app.form;

import java.time.LocalDate;
import java.time.LocalTime;

import com.schedule.app.enums.DomainError;
import com.schedule.app.exception.DomainException;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record IrregularScheduleUpdateForm(
        @NotNull Integer id,
        @NotNull LocalTime startTime,
        @NotNull LocalTime endTime,
        @NotNull LocalDate date,
        @Pattern(regexp = "(0[1-9]|10|11)") @NotNull String workTypeId) {
    public IrregularScheduleUpdateForm {
        if (startTime != null && endTime != null && startTime.isAfter(endTime)) {
            throw new DomainException(DomainError.VALIDATION_ERROR,
                    "time: startTime must be before or equal to the 'end' time.");
        }
    }
}