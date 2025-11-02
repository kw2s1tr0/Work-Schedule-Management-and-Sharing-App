package com.schedule.app.repository;

import java.util.List;

import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.DefaultUserRecord;
import com.schedule.app.record.output.IrregularUserRecord;
import com.schedule.app.record.output.RegularUserRecord;

public interface ScheduleSearchMapper {
    List<DefaultUserRecord> readRegularScheduleRecord(ScheduleSearchRecord record);
    List<RegularUserRecord> readIrregularScheduleRecord(ScheduleSearchRecord record);
    List<IrregularUserRecord> readDefaultScheduleRecord(ScheduleSearchRecord record);
}
