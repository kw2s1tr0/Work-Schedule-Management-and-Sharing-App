package com.schedule.app.record.output;

import java.util.List;

import com.schedule.app.record.output.item.RegularScheduleRecord;

public record UserRegularScheduleRecord(
        List<RegularScheduleRecord> regularSchedules) {
}
