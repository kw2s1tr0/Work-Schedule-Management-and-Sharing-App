package com.schedule.app.applicationservice;

import com.schedule.app.form.IrregularScheduleInsertForm;
import com.schedule.app.record.input.IrregularScheduleInputRecord;

public interface PostIrregularScheduleService {

  public void postIrregularScheduleService(IrregularScheduleInsertForm form, String userId);

  public IrregularScheduleInputRecord toIrregularScheduleRecord(
      IrregularScheduleInsertForm form, String userId);

  public void postIrregularSchedule(IrregularScheduleInputRecord record);
}
