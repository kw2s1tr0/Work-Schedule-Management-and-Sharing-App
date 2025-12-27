package com.schedule.app.applicationservice;

import com.schedule.app.form.DefaultScheduleInsertForm;
import com.schedule.app.record.input.DefaultScheduleInputRecord;

public interface PostDefaultScheduleService {
  public Integer postDefaultScheduleService(DefaultScheduleInsertForm form, String userId);

  public DefaultScheduleInputRecord toDefaultScheduleRecord(
      DefaultScheduleInsertForm form, String userId);

  public Integer postDefaultSchedule(DefaultScheduleInputRecord record);
}
