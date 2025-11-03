package com.schedule.app.repository;

import java.util.List;

import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.UserDefaultScheduleRecord;
import com.schedule.app.record.output.UserIrregularScheduleRecord;
import com.schedule.app.record.output.UserRegularScheduleRecord;

public interface ScheduleSearchMapper {
    List<UserRegularScheduleRecord> readRegularScheduleRecord(ScheduleSearchRecord record);
    List<UserIrregularScheduleRecord> readIrregularScheduleRecord(ScheduleSearchRecord record);
    List<UserDefaultScheduleRecord> readDefaultScheduleRecord(ScheduleSearchRecord record);
}
