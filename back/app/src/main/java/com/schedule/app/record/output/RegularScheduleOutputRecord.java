package com.schedule.app.record.output;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegularScheduleOutputRecord {
  private String userId;
  private Integer id;
  private LocalTime startTime;
  private LocalTime endTime;
  private LocalDate startDate;
  private LocalDate endDate;
  private String daysOfWeek;
  private String worktypeName;
  private String worktypeColor;
}
