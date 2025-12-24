package com.schedule.app.applicationservice;

public interface DeleteIrregularScheduleService {
  public Integer deleteIrregularScheduleService(int scheduleId, String userId);

  public void existIrregularSchedule(int scheduleId, String userId);

  public Integer deleteIrregularSchedule(int scheduleId);
}
