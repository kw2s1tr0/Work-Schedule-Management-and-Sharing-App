package com.schedule.app.applicationservice;

import java.util.List;

import com.schedule.app.dto.DefaultScheduleDTO;
import com.schedule.app.form.SingleScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.DefaultScheduleOutputRecord;

public interface GetDefaultScheduleService {
    List<DefaultScheduleDTO> defaultScheduleSearchService(SingleScheduleSearchForm form);

    ScheduleSearchRecord toScheduleRecord(SingleScheduleSearchForm form);

    List<DefaultScheduleOutputRecord> readDefaultSchedule(ScheduleSearchRecord record);

    List<DefaultScheduleDTO> toScheduleDTOList(List<DefaultScheduleOutputRecord> records);
}
