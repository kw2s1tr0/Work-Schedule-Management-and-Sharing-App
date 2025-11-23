package com.schedule.app.applicationservice;

import com.schedule.app.form.DefaultScheduleUpdateForm;
import com.schedule.app.record.input.DefaultScheduleInputRecord;

public interface PutDefaultScheduleService {
  public void patchDefaultScheduleService(DefaultScheduleUpdateForm form, String userId);

  public DefaultScheduleInputRecord toDefaultScheduleRecord(
      DefaultScheduleUpdateForm form, String userId);

  public void patchDefaultSchedule(DefaultScheduleInputRecord record);
}
