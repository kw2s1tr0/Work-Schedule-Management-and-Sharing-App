package com.schedule.app.form;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.constraints.NotNull;

public record IrregularScheduleSearchForm(

        @NotNull LocalDate from,
        @NotNull LocalDate to) {

    public IrregularScheduleSearchForm {
        if (from.isAfter(to)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The 'from' date must be before or equal to the 'to' date.");
        }
    }
}