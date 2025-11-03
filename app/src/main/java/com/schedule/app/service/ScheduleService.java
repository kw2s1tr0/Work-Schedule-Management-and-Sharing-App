package com.schedule.app.service;

import java.time.LocalDate;
import java.util.List;

import com.schedule.app.dto.UserDTO;
import com.schedule.app.form.ScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.UserRecord;
import com.schedule.app.record.output.item.DefaultScheduleRecord;
import com.schedule.app.record.output.item.IrregularScheduleRecord;
import com.schedule.app.record.output.item.RegularScheduleRecord;

public interface ScheduleService {
    public List<UserDTO> scheduleSearchService(ScheduleSearchForm form);
    public ScheduleSearchRecord toScheduleSearchRecord(ScheduleSearchForm form);
    public List<UserRecord> readRegularSchedule(ScheduleSearchRecord record);
    public List<UserRecord> readIrregularSchedule(ScheduleSearchRecord record);
    public List<UserRecord> readDefaultSchedule(ScheduleSearchRecord record);
    public List<DefaultScheduleRecord> readCommonDefaultScheduleRecord(ScheduleSearchRecord record);
    public List<RegularScheduleRecord> readCommonRegularUserRecords(ScheduleSearchRecord record);
    public List<IrregularScheduleRecord> readCommonIrregularUserRecords(ScheduleSearchRecord record);
    public List<UserDTO> toUserDTOList(
        List<UserRecord> defaultUserRecords,
        List<UserRecord> regularUserRecords,
        List<UserRecord> irregularUserRecords,
        List<DefaultScheduleRecord> defaultScheduleRecords,
        List<RegularScheduleRecord> regularScheduleRecords,
        List<IrregularScheduleRecord> irregularScheduleRecords,
        LocalDate from,
        LocalDate to);
}
