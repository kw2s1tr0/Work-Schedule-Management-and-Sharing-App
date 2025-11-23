package com.schedule.app.form;

import com.schedule.app.enums.DomainError;
import com.schedule.app.exception.DomainException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;

@Builder
public record DefaultScheduleInsertForm(
    @NotNull LocalTime startTime,
    @NotNull LocalTime endTime,
    @NotNull LocalDate startDate,
    @NotNull LocalDate endDate,
    @Pattern(regexp = "(0[1-9]|10|11)") @NotNull String workTypeId) {
  public DefaultScheduleInsertForm {
    if (startDate != null && endDate != null && startTime != null && endTime != null) {
      if (startDate.isAfter(endDate)) {
        throw new DomainException(
            DomainError.VALIDATION_ERROR,
            "date: startDate must be before or equal to the 'to' date.");
      }

      if (startTime.isAfter(endTime)) {
        throw new DomainException(
            DomainError.VALIDATION_ERROR,
            "time: startTime must be before or equal to the 'end' time.");
      }
    }
  }
}
