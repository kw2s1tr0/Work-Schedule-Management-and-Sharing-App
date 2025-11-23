package com.schedule.app.applicationservice;

import com.schedule.app.dto.DefaultScheduleDTO;
import com.schedule.app.form.SingleScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.DefaultScheduleOutputRecord;
import java.util.List;

public interface GetDefaultScheduleService {
  List<DefaultScheduleDTO> defaultScheduleSearchService(
      SingleScheduleSearchForm form, String userId);

  ScheduleSearchRecord toScheduleRecord(SingleScheduleSearchForm form, String userId);

  List<DefaultScheduleOutputRecord> readDefaultSchedule(ScheduleSearchRecord record);

  List<DefaultScheduleDTO> toScheduleDTOList(List<DefaultScheduleOutputRecord> records);
}
