package com.schedule.app.repository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleFindMapper {
  int findDefaultSchedule(Integer id, String userId, LocalDate startDate, LocalDate endDate);

  List<String> findRegularSchedule(
      Integer id, String userId, LocalDate startDate, LocalDate endDate);

  int findIrregularSchedule(Integer id, String userId, LocalDate startDate, LocalDate endDate);
}
