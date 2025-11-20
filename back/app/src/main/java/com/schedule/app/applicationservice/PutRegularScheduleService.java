package com.schedule.app.applicationservice;

import com.schedule.app.entity.RegularSchedule;
import com.schedule.app.form.RegularScheduleForm;
import com.schedule.app.record.input.RegularScheduleInputRecord;

public interface PutRegularScheduleService {

    public void patchRegularScheduleService(RegularScheduleForm form, String userId);
    public RegularSchedule toRegularScheduleEntity(RegularScheduleForm form, String userId);
    public RegularScheduleInputRecord toRegularScheduleRecord(RegularSchedule regularSchedule);
    public void patchRegularSchedule(RegularScheduleInputRecord record);
}
