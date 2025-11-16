package com.schedule.app.repository;

public interface ScheduleDeleteMapper {
    void deleteDefaultSchedule(int shceduleId);
    void deleteRegularSchedule(int scheduleId);
    void deleteIrregularSchedule(int scheduleId);
}
