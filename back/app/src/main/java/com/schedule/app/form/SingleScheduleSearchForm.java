package com.schedule.app.form;

import java.time.LocalDate;

import com.schedule.app.enums.DomainError;
import com.schedule.app.exception.DomainException;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SingleScheduleSearchForm(

        @NotNull LocalDate from,
        @NotNull LocalDate to) {

    public SingleScheduleSearchForm {
        if (from != null && to != null && from.isAfter(to)) {
            throw new DomainException(DomainError.VALIDATION_ERROR,
                    "date: The 'from' date must be before or equal to the 'to' date.");
        }
    }
}