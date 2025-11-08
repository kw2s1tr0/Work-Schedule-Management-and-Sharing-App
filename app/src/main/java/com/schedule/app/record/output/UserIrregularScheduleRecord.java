package com.schedule.app.record.output;

import java.util.List;

import com.schedule.app.record.output.item.IrregularScheduleRecord;

public record UserIrregularScheduleRecord(
        List<IrregularScheduleRecord> irregularSchedules) {
}
