package com.schedule.app.applicationservice;

import com.schedule.app.entity.RegularSchedule;
import com.schedule.app.form.RegularScheduleForm;
import com.schedule.app.record.input.RegularScheduleInputRecord;

public interface PostRegularScheduleService {
    public void postRegularScheduleService(RegularScheduleForm form, String userId);
    public RegularSchedule toRegularScheduleEntity(RegularScheduleForm form, String userId);
    public RegularScheduleInputRecord toRegularScheduleRecord(RegularSchedule regularSchedule);
    public void postRegularSchedule(RegularScheduleInputRecord record);
}
