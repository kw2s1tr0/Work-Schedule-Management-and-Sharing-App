package com.schedule.app.applicationservice;

import com.schedule.app.entity.RegularSchedule;
import com.schedule.app.form.RegularScheduleForm;
import com.schedule.app.record.input.RegularScheduleInputRecord;

public interface PatchRegularScheduleService {

    public void patchRegularScheduleService(RegularScheduleForm form);
    public RegularSchedule toRegularScheduleEntity(RegularScheduleForm form);
    public RegularScheduleInputRecord toRegularScheduleRecord(RegularSchedule regularSchedule);
    public void patchRegularSchedule(RegularScheduleInputRecord record);
}
