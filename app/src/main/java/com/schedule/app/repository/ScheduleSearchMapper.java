package com.schedule.app.repository;

import java.util.List;

import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.DefaultScheduleRecord;
import com.schedule.app.record.output.IrregularScheduleRecord;
import com.schedule.app.record.output.RegularScheduleRecord;

public interface ScheduleSearchMapper {
    List<RegularScheduleRecord> readRegularScheduleRecord(ScheduleSearchRecord record);

    List<IrregularScheduleRecord> readIrregularScheduleRecord(ScheduleSearchRecord record);

    List<DefaultScheduleRecord> readDefaultScheduleRecord(ScheduleSearchRecord record);
}
