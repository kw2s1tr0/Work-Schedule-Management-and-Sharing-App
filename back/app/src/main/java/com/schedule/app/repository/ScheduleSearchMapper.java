package com.schedule.app.repository;

import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.DefaultScheduleOutputRecord;
import com.schedule.app.record.output.IrregularScheduleOutputRecord;
import com.schedule.app.record.output.RegularScheduleOutputRecord;
import java.util.List;

public interface ScheduleSearchMapper {
  List<RegularScheduleOutputRecord> readRegularScheduleRecord(ScheduleSearchRecord record);

  List<IrregularScheduleOutputRecord> readIrregularScheduleRecord(ScheduleSearchRecord record);

  List<DefaultScheduleOutputRecord> readDefaultScheduleRecord(ScheduleSearchRecord record);
}
