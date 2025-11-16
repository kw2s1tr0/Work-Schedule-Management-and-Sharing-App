package com.schedule.app.applicationservice;

import com.schedule.app.form.IrregularScheduleForm;
import com.schedule.app.record.input.IrregularScheduleInputRecord;

public interface PatchIrregularScheduleService {
    public void patchIrregularScheduleService(IrregularScheduleForm form);
    public IrregularScheduleInputRecord toIrregularScheduleRecord(IrregularScheduleForm form);
    public void patchIrregularSchedule(IrregularScheduleInputRecord record);
}
