package com.schedule.app.repository;

import java.util.List;

import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.item.DefaultScheduleRecord;
import com.schedule.app.record.output.item.IrregularScheduleRecord;
import com.schedule.app.record.output.item.RegularScheduleRecord;

public interface CommonScheduleSearchMapper {
    List<DefaultScheduleRecord> readDefaultScheduleRecord(ScheduleSearchRecord record);
    List<RegularScheduleRecord> readRegularScheduleRecord(ScheduleSearchRecord record);
    List<IrregularScheduleRecord> readIrregularScheduleRecord(ScheduleSearchRecord record);
}
