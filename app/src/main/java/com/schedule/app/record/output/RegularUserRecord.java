package com.schedule.app.record.output;

import java.util.List;

import com.schedule.app.record.output.item.RegularScheduleRecord;

public record RegularUserRecord (
    int userId,
    String userName,
    String organizationName,
    List<RegularScheduleRecord> regularSchedules
){}
