package com.schedule.app.applicationservice;

public interface DeleteDefaultScheduleService {
  public Integer deleteDefaultScheduleService(int scheduleId, String userId);

  public void existDefaultSchedule(int scheduleId, String userId);

  public Integer deleteDefaultSchedule(int scheduleId);
}
