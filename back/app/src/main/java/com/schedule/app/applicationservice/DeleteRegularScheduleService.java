package com.schedule.app.applicationservice;

public interface DeleteRegularScheduleService {
    void deleteRegularScheduleService(int scheduleId, String userId);
    void existRegularSchedule(int scheduleId, String userId);
    void deleteRegularSchedule(int scheduleId);
}
