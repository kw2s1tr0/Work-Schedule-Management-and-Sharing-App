package com.schedule.app.repository;

import java.util.List;

import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.DefaultScheduleOutputRecord;
import com.schedule.app.record.output.IrregularScheduleOutputRecord;
import com.schedule.app.record.output.RegularScheduleOutputRecord;

public interface CommonScheduleSearchMapper {
    List<DefaultScheduleOutputRecord> readDefaultScheduleRecord(ScheduleSearchRecord record);

    List<RegularScheduleOutputRecord> readRegularScheduleRecord(ScheduleSearchRecord record);

    List<IrregularScheduleOutputRecord> readIrregularScheduleRecord(ScheduleSearchRecord record);
}
