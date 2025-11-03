package com.schedule.app.record.output;

import java.util.List;

import com.schedule.app.record.output.item.DefaultScheduleRecord;
import com.schedule.app.record.output.item.IrregularScheduleRecord;
import com.schedule.app.record.output.item.RegularScheduleRecord;

public record UserRecord (
    String userName,
    String organizationName,
    List<DefaultScheduleRecord> defaultSchedules,
    List<RegularScheduleRecord> regularSchedules,
    List<IrregularScheduleRecord> irregularSchedules
){}
