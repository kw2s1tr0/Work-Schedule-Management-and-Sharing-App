package com.schedule.app.applicationservice;

import com.schedule.app.form.DefaultScheduleForm;
import com.schedule.app.record.input.DefaultScheduleInputRecord;

public interface PutDefaultScheduleService {
    public void patchDefaultScheduleService(DefaultScheduleForm form, String userId);
    public DefaultScheduleInputRecord toDefaultScheduleRecord(DefaultScheduleForm form, String userId);
    public void patchDefaultSchedule(DefaultScheduleInputRecord record);
}
