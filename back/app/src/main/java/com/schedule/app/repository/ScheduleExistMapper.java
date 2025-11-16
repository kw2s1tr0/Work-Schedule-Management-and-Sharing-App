package com.schedule.app.repository;

public interface ScheduleExistMapper {
    boolean existDefaultSchedule(int scheduleId,String userId);
    boolean existRegularSchedule(int scheduleId, String userId);
    boolean existIrregularSchedule(int scheduleId, String userId);
}
