package com.schedule.app.applicationservice;

import com.schedule.app.form.IrregularScheduleForm;
import com.schedule.app.record.input.IrregularScheduleInputRecord;

public interface PostIrregularScheduleService {
    
    public void postIrregularScheduleService(IrregularScheduleForm form);
    public IrregularScheduleInputRecord toIrregularScheduleRecord(IrregularScheduleForm form);
    public void postIrregularSchedule(IrregularScheduleInputRecord record);
}