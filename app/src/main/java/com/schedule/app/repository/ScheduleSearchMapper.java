package com.schedule.app.repository;

import java.util.List;

import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.UserRecord;

public interface ScheduleSearchMapper {
    List<UserRecord> readRegularScheduleRecord(ScheduleSearchRecord record);
    List<UserRecord> readIrregularScheduleRecord(ScheduleSearchRecord record);
    List<UserRecord> readDefaultScheduleRecord(ScheduleSearchRecord record);
}
