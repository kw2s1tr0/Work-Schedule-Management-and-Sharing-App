package com.schedule.app.applicationservice;

import java.time.LocalDate;
import java.util.List;

import com.schedule.app.dto.UserDTO;
import com.schedule.app.entity.User;
import com.schedule.app.form.ScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.DefaultScheduleRecord;
import com.schedule.app.record.output.IrregularScheduleRecord;
import com.schedule.app.record.output.RegularScheduleRecord;
import com.schedule.app.record.output.UserRecord;

public interface ScheduleSearchService {
    public List<UserDTO> scheduleSearchService(ScheduleSearchForm form);

    public ScheduleSearchRecord toScheduleSearchRecord(ScheduleSearchForm form);

    public List<RegularScheduleRecord> readRegularSchedule(ScheduleSearchRecord record);

    public List<IrregularScheduleRecord> readIrregularSchedule(ScheduleSearchRecord record);

    public List<DefaultScheduleRecord> readDefaultSchedule(ScheduleSearchRecord record);

    public List<DefaultScheduleRecord> readCommonDefaultScheduleRecord(ScheduleSearchRecord record);

    public List<RegularScheduleRecord> readCommonRegularUserRecords(ScheduleSearchRecord record);

    public List<IrregularScheduleRecord> readCommonIrregularUserRecords(ScheduleSearchRecord record);

    public List<UserRecord> readUserRecords(ScheduleSearchRecord record);

    public List<User> toUserEntityList(List<DefaultScheduleRecord> defaultRecords,
            List<RegularScheduleRecord> regularRecords,
            List<IrregularScheduleRecord> irregularRecords,
            List<DefaultScheduleRecord> commonDefaultScheduleRecords,
            List<RegularScheduleRecord> commonRegularScheduleRecords,
            List<IrregularScheduleRecord> commonIrregularScheduleRecords,
            LocalDate from,
            LocalDate to,
            List<UserRecord> userRecords);

    public List<UserDTO> toUserDTOList(List<User> userList);
}
