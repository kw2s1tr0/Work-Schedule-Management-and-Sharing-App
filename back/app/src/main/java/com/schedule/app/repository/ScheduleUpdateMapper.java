package com.schedule.app.repository;

import com.schedule.app.record.input.DefaultScheduleInputRecord;
import com.schedule.app.record.input.IrregularScheduleInputRecord;
import com.schedule.app.record.input.RegularScheduleInputRecord;

public interface ScheduleUpdateMapper {
  void updateDefaultSchedule(DefaultScheduleInputRecord record);

  void updateRegularSchedule(RegularScheduleInputRecord record);

  void updateIrregularSchedule(IrregularScheduleInputRecord record);
}
