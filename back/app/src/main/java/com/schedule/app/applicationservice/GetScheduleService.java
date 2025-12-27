package com.schedule.app.applicationservice;

import com.schedule.app.dto.UserDTO;
import com.schedule.app.entity.User;
import com.schedule.app.form.ScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.DefaultScheduleOutputRecord;
import com.schedule.app.record.output.IrregularScheduleOutputRecord;
import com.schedule.app.record.output.RegularScheduleOutputRecord;
import com.schedule.app.record.output.UserRecord;
import java.time.LocalDate;
import java.util.List;

public interface GetScheduleService {
  public List<UserDTO> scheduleSearchService(ScheduleSearchForm form);

  public ScheduleSearchRecord toScheduleSearchRecord(ScheduleSearchForm form);

  public List<RegularScheduleOutputRecord> readRegularSchedule(ScheduleSearchRecord record);

  public List<IrregularScheduleOutputRecord> readIrregularSchedule(ScheduleSearchRecord record);

  public List<DefaultScheduleOutputRecord> readDefaultSchedule(ScheduleSearchRecord record);

  public List<DefaultScheduleOutputRecord> readCommonDefaultScheduleRecord(
      ScheduleSearchRecord record);

  public List<RegularScheduleOutputRecord> readCommonRegularUserRecords(
      ScheduleSearchRecord record);

  public List<IrregularScheduleOutputRecord> readCommonIrregularUserRecords(
      ScheduleSearchRecord record);

  public List<UserRecord> readUserRecords(ScheduleSearchRecord record);

  public List<User> toUserEntityList(
      List<DefaultScheduleOutputRecord> defaultRecords,
      List<RegularScheduleOutputRecord> regularRecords,
      List<IrregularScheduleOutputRecord> irregularRecords,
      List<DefaultScheduleOutputRecord> commonDefaultScheduleRecords,
      List<RegularScheduleOutputRecord> commonRegularScheduleRecords,
      List<IrregularScheduleOutputRecord> commonIrregularScheduleRecords,
      LocalDate from,
      LocalDate to,
      List<UserRecord> userRecords);

  public List<UserDTO> toUserDTOList(List<User> userList);
}
