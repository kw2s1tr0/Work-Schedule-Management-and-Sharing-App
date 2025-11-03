package com.schedule.app.service;

import java.time.LocalDate;
import java.util.List;

import com.schedule.app.dto.UserDTO;
import com.schedule.app.form.ScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.UserDefaultScheduleRecord;
import com.schedule.app.record.output.UserIrregularScheduleRecord;
import com.schedule.app.record.output.UserRecord;
import com.schedule.app.record.output.UserRegularScheduleRecord;
import com.schedule.app.record.output.item.DefaultScheduleRecord;
import com.schedule.app.record.output.item.IrregularScheduleRecord;
import com.schedule.app.record.output.item.RegularScheduleRecord;

public interface ScheduleService {
    public List<UserDTO> scheduleSearchService(ScheduleSearchForm form);
    public ScheduleSearchRecord toScheduleSearchRecord(ScheduleSearchForm form);
    public List<UserRegularScheduleRecord> readRegularSchedule(ScheduleSearchRecord record);
    public List<UserIrregularScheduleRecord> readIrregularSchedule(ScheduleSearchRecord record);
    public List<UserDefaultScheduleRecord> readDefaultSchedule(ScheduleSearchRecord record);
    public List<DefaultScheduleRecord> readCommonDefaultScheduleRecord(ScheduleSearchRecord record);
    public List<RegularScheduleRecord> readCommonRegularUserRecords(ScheduleSearchRecord record);
    public List<IrregularScheduleRecord> readCommonIrregularUserRecords(ScheduleSearchRecord record);
    public List<UserRecord> readUserRecords(ScheduleSearchRecord record);
    public List<UserDTO> toUserDTOList(
        List<UserRecord> defaultUserRecords,
        List<UserRecord> regularUserRecords,
        List<UserRecord> irregularUserRecords,
        List<DefaultScheduleRecord> defaultScheduleRecords,
        List<RegularScheduleRecord> regularScheduleRecords,
        List<IrregularScheduleRecord> irregularScheduleRecords,
        List<UserRecord> userRecords,
        LocalDate from,
        LocalDate to);
}
