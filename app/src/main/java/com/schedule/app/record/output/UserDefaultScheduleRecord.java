package com.schedule.app.record.output;

import java.util.List;

import com.schedule.app.record.output.item.DefaultScheduleRecord;

public record UserDefaultScheduleRecord(
        List<DefaultScheduleRecord> defaultSchedules) {
}
