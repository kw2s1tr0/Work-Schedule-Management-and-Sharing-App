package com.schedule.app.applicationservice;

import com.schedule.app.form.DefaultScheduleForm;
import com.schedule.app.record.input.DefaultScheduleInputRecord;

public interface PostDefaultScheduleService {
    public void postDefaultScheduleService(DefaultScheduleForm form, String userId);
    public DefaultScheduleInputRecord toDefaultScheduleRecord(DefaultScheduleForm form, String userId);
    public void postDefaultSchedule(DefaultScheduleInputRecord record);
}