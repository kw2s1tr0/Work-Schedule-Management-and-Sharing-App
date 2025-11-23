package com.schedule.app.form;

import com.schedule.app.enums.DomainError;
import com.schedule.app.enums.ViewMode;
import com.schedule.app.exception.DomainException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ScheduleSearchForm(
    @Pattern(regexp = "^[0-9０-９]{0,5}$") String userId,
    @Pattern(regexp = "^\\d{4}-W(0[1-9]|[1-4][0-9]|5[0-3])$") String week,
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])$") String month,
    @NotNull ViewMode viewMode,
    @Size(max = 50) String name,
    @Pattern(regexp = "^\\d{0,3}$") String organizationCode) {
  public ScheduleSearchForm {
    if (week != null && month != null) {
      throw new DomainException(
          DomainError.VALIDATION_ERROR, "period: Only one of week or month can be provided");
    }
    if (week == null && month == null) {
      throw new DomainException(
          DomainError.VALIDATION_ERROR, "period: Either week or month must be provided");
    }
    if (viewMode == ViewMode.WEEK && week == null) {
      throw new DomainException(
          DomainError.VALIDATION_ERROR, "viewMode: Week must be provided when viewMode is WEEK");
    }
    if (viewMode == ViewMode.MONTH && month == null) {
      throw new DomainException(
          DomainError.VALIDATION_ERROR, "viewMode: Month must be provided when viewMode is MONTH");
    }
  }
}
