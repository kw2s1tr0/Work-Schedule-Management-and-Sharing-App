package com.schedule.app.applicationservice;

import com.schedule.app.entity.RegularSchedule;
import com.schedule.app.form.RegularScheduleInsertForm;
import com.schedule.app.record.input.RegularScheduleInputRecord;

public interface PostRegularScheduleService {
  public void postRegularScheduleService(RegularScheduleInsertForm form, String userId);

  public RegularSchedule toRegularScheduleEntity(RegularScheduleInsertForm form, String userId);

  public RegularScheduleInputRecord toRegularScheduleRecord(RegularSchedule regularSchedule);

  public void postRegularSchedule(RegularScheduleInputRecord record);
}
