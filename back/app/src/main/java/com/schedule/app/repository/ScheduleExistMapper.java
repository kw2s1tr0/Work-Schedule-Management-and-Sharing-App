package com.schedule.app.repository;

public interface ScheduleExistMapper {
    boolean existDefaultSchedule(int scheduleId,int userId);
    boolean existRegularSchedule(int scheduleId, int userId);
    boolean existIrregularSchedule(int scheduleId, int userId);
}
