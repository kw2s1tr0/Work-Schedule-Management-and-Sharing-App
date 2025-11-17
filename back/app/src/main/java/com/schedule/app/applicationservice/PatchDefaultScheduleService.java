package com.schedule.app.applicationservice;

import com.schedule.app.entity.DefaultSchedule;
import com.schedule.app.form.DefaultScheduleForm;
import com.schedule.app.record.input.DefaultScheduleInputRecord;

public interface PatchDefaultScheduleService {
    public void patchDefaultScheduleService(DefaultScheduleForm form, String userId);
    public DefaultSchedule toDefaultScheduleEintity(DefaultScheduleForm form, String userId);
    public DefaultScheduleInputRecord toDefaultScheduleRecord(DefaultSchedule DefaultSchedule);
    public void patchDefaultSchedule(DefaultScheduleInputRecord record);
}
