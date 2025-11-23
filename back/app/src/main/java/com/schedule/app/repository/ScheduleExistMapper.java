package com.schedule.app.repository;

public interface ScheduleExistMapper {
  int existDefaultSchedule(int scheduleId, String userId);

  int existRegularSchedule(int scheduleId, String userId);

  int existIrregularSchedule(int scheduleId, String userId);
}
