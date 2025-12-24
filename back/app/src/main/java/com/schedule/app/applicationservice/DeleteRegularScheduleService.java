package com.schedule.app.applicationservice;

public interface DeleteRegularScheduleService {
  Integer deleteRegularScheduleService(int scheduleId, String userId);

  void existRegularSchedule(int scheduleId, String userId);

  Integer deleteRegularSchedule(int scheduleId);
}
