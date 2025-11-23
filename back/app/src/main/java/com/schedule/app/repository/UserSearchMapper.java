package com.schedule.app.repository;

import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.UserRecord;
import java.util.List;

public interface UserSearchMapper {
  List<UserRecord> readUserRecord(ScheduleSearchRecord record);
}
