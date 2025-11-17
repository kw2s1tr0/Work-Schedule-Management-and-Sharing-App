package com.schedule.app.applicationservice;

import com.schedule.app.form.IrregularScheduleForm;
import com.schedule.app.record.input.IrregularScheduleInputRecord;

public interface PatchIrregularScheduleService {
    public void patchIrregularScheduleService(IrregularScheduleForm form, String userId);
    public IrregularScheduleInputRecord toIrregularScheduleRecord(IrregularScheduleForm form, String userId);
    public void patchIrregularSchedule(IrregularScheduleInputRecord record);
}
