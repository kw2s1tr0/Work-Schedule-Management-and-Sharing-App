package com.schedule.app.applicationservice;

public interface DeleteDefaultScheduleService {
    public void deleteDefaultScheduleService(int scheduleId, String userId);
    public void existDefaultSchedule(int scheduleId, String userId);
    public void deleteDefaultSchedule(int scheduleId);
}
