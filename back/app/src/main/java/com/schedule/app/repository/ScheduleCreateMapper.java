package com.schedule.app.repository;

import com.schedule.app.record.input.DefaultScheduleInputRecord;
import com.schedule.app.record.input.IrregularScheduleInputRecord;
import com.schedule.app.record.input.RegularScheduleInputRecord;

public interface ScheduleCreateMapper {
  Integer createDefaultSchedule(DefaultScheduleInputRecord record);

  Integer createRegularSchedule(RegularScheduleInputRecord record);

  Integer createIrregularSchedule(IrregularScheduleInputRecord record);
}
