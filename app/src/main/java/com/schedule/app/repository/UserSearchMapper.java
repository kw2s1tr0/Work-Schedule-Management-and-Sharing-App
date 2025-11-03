package com.schedule.app.repository;

import java.util.List;

import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.UserRecord;

public interface UserSearchMapper {
    List<UserRecord> readUserRecord(ScheduleSearchRecord record);
}