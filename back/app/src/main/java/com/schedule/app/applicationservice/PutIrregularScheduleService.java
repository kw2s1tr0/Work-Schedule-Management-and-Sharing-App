package com.schedule.app.applicationservice;

import com.schedule.app.form.IrregularScheduleUpdateForm;
import com.schedule.app.record.input.IrregularScheduleInputRecord;

public interface PutIrregularScheduleService {
  public void patchIrregularScheduleService(IrregularScheduleUpdateForm form, String userId);

  public IrregularScheduleInputRecord toIrregularScheduleRecord(
      IrregularScheduleUpdateForm form, String userId);

  public void patchIrregularSchedule(IrregularScheduleInputRecord record);
}
