package com.schedule.app.applicationservice;

public interface DeleteIrregularScheduleService {
  public void deleteIrregularScheduleService(int scheduleId, String userId);

  public void existIrregularSchedule(int scheduleId, String userId);

  public void deleteIrregularSchedule(int scheduleId);
}
