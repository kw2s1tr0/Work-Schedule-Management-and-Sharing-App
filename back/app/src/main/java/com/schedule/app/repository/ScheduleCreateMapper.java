package com.schedule.app.repository;

import com.schedule.app.record.input.DefaultScheduleInputRecord;
import com.schedule.app.record.input.IrregularScheduleInputRecord;
import com.schedule.app.record.input.RegularScheduleInputRecord;

public interface ScheduleCreateMapper {
    void createDefaultSchedule(DefaultScheduleInputRecord record);
    void createRegularSchedule(RegularScheduleInputRecord record);
    void createIrregularSchedule(IrregularScheduleInputRecord record);
}
